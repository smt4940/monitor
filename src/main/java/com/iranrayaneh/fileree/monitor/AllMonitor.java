package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class AllMonitor extends Monitor implements Runnable{

    private int cpuTreshold;
    private int ramTreshold;
    private int diskTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private int hours;
    private Boolean stopped = false;
    private Boolean triggered = false;
    private StatusLabel statusLabel = StatusLabel.getInstance();

    public AllMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold, Integer ramTreshold, Integer diskTreshold) {

        this.cpuTreshold = cpuTreshold;
        this.diskTreshold = diskTreshold;
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
            Sigar sigar = new Sigar();
            int n = steps;
            double[] diskStats = new double[n];
            double[] ramStats = new double[n];
            double[] cpuStats = new double[n];
            while (!stopped && n > 0) {
                double sum = 0;
                Date date = new Date();
                for (int i = 0; i < 100; i++) {
                    try {
                        sum += sigar.getDiskUsage(System.getProperty("user.dir").split(":")[0] + ":").getQueue();
                        System.out.println("disk stats at " + date.toString() + " is: " + sum + ". ");
                        statusLabel.appendLabel("disk stats at " + date.toString() + " is: " + sum + ". ");
                        Thread.sleep(1000);
                    } catch (SigarException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    ramStats[n - 1] = sigar.getMem().getUsedPercent();
                    cpuStats[n - 1] = sigar.getCpuPerc().getUser();
                    System.out.println("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
                    statusLabel.appendLabel("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
                    System.out.println("ram stats at " + date.toString() + " is: " + ramStats[n - 1]);
                    statusLabel.appendLabel("ram stats at " + date.toString() + " is: " + ramStats[n - 1]);
                    fileTransferer.readLinesToBuffer();
                    Thread.sleep(milisecondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SigarException e) {
                    e.printStackTrace();
                }
                n--;
                diskStats[n] = sum;
            }
            double ramAverage, ramSum = 0;
            double cpuAverage = 0, cpuSum = 0;
            double diskAverage, diskSum = 0;
            for (double d : diskStats) {
                diskSum += d;
            }
            for (double d : ramStats) {
                ramSum += d;
            }
            ramAverage = ramSum / ramStats.length;
            for (double d : cpuStats) {
                cpuSum += d;
            }
            diskAverage = diskSum / diskStats.length * 20;
            if ((diskAverage > diskTreshold)||(ramAverage > ramTreshold / 100) || (cpuAverage > cpuTreshold / 100)){
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
