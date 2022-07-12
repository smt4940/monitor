package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class CpuMonitor extends Monitor implements Runnable {

    private int cpuTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private int hours;
    private Boolean stopped = false;
    private Boolean triggered = false;
    private StatusLabel statusLabel = StatusLabel.getInstance();

    public CpuMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold) {
        this.cpuTreshold = cpuTreshold;
        this.fileTransferer = (GeneralFileTransferer) fileTransferer;
        this.steps = limiter.getSteps();
        this.milisecondsToWait = 60000 * limiter.getStepDuration();
        this.fileTransferer.gotoLastLine();
        this.hours = limiter.getDuration();
    }

    @Override
    public void run() {
        int h=0;
        while (!stopped && h<hours) {
            int n = steps;
            double[] cpuStats = new double[n];
            Sigar sigar = new Sigar();
            while (!stopped && n > 0) {
                try {
                    Date date = new Date();
                    cpuStats[n - 1] = sigar.getCpuPerc().getUser();
                    System.out.println("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
                    statusLabel.appendLabel("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
                    fileTransferer.readLinesToBuffer();
                    Thread.sleep(milisecondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SigarException e) {
                    e.printStackTrace();
                }
                n--;
            }
            double average, sum = 0;
            for (double d : cpuStats) {
                sum += d;
            }
            average = sum / cpuStats.length;
            if (average > cpuTreshold / 100) {
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
