package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class CpuRamMonitor extends Monitor implements Runnable {

    private int cpuTreshold;
    private int ramTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private int hours;

    public Boolean getStopped() {
        return stopped;
    }

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    private Boolean stopped = false;
    private Boolean triggered = false;

    public CpuRamMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold, Integer ramTreshold) {

        this.cpuTreshold = cpuTreshold;
        this.ramTreshold = ramTreshold;
        this.fileTransferer = (GeneralFileTransferer) fileTransferer;
        this.steps = limiter.getSteps();
        this.milisecondsToWait = 60000 * limiter.getStepDuration();
        this.fileTransferer.gotoLastLine();
        this.hours = limiter.getDuration();
    }

    @Override
    public void run() {
        int h = 0;
        while (!stopped && h < hours) {
            int n = steps;
            double[] ramStats = new double[n];
            double[] cpuStats = new double[n];
            Sigar sigar = new Sigar();
            while (!stopped && n > 0) {
                try {
                    Date date = new Date();
                    ramStats[n - 1] = sigar.getMem().getUsedPercent();
                    cpuStats[n - 1] = sigar.getCpuPerc().getUser();
                    System.out.println("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
                    System.out.println("ram stats at " + date.toString() + " is: " + ramStats[n - 1]);
                    fileTransferer.readLinesToBuffer();
                    Thread.sleep(milisecondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SigarException e) {
                    e.printStackTrace();
                }
                n--;
            }
            double ramAverage, ramSum = 0;
            double cpuAverage, cpuSum = 0;
            for (double d : ramStats) {
                ramSum += d;
            }
            ramAverage = ramSum / ramStats.length;
            for (double d : cpuStats) {
                cpuSum += d;
            }
            cpuAverage = cpuSum / cpuStats.length;
            if ((ramAverage > ramTreshold / 100) || (cpuAverage > cpuTreshold / 100)) {
                fileTransferer.saveOnFile();
                triggered = true;
            }
        }
        try {
            h++;
            Thread.sleep(180000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
