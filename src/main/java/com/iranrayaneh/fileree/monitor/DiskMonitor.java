package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class DiskMonitor extends Monitor implements Runnable {
    private int diskTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private Boolean stopped = false;
    private Boolean triggered = false;
    private int hours;

    public DiskMonitor(Limiter limiter, FileTransferer fileTransferer, Integer diskTrwshold) {
        this.diskTreshold = diskTrwshold;
        this.fileTransferer = (GeneralFileTransferer) fileTransferer;
        this.steps = limiter.getSteps();
        this.milisecondsToWait = 60000 * limiter.getStepDuration();
        this.fileTransferer.gotoLastLine();
        this.hours = limiter.getDuration();
    }

    public Boolean isStopped() {
        return stopped;
    }

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    public void run() {
        int h = 0;
        while (!stopped && h < hours) {
            Sigar sigar = new Sigar();
            int n = steps;
            double[] diskStats = new double[n];
            while (!stopped && n > 0) {
                double sum = 0;
                Date date = new Date();
                for (int i = 0; i < 100; i++) {
                    try {
                        sum += sigar.getDiskUsage(System.getProperty("user.dir").split(":")[0] + ":").getQueue();
                        System.out.println("disk stats at " + date.toString() + " is: " + sum + ". ");
                        Thread.sleep(1000);
                    } catch (SigarException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                n--;
                diskStats[n] = sum;
                fileTransferer.readLinesToBuffer();
                try {
                    Thread.sleep(milisecondsToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double average, diskSum = 0;
            for (double d : diskStats) {
                diskSum += d;
            }
            average = diskSum / diskStats.length * 20;
            if (average > diskTreshold) {
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
