package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class CpuDiskMonitor extends Monitor implements Runnable {
    private int cpuTreshold;
    private int diskTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private int hours;
    private Boolean stopped = false;
    private Boolean triggered = false;

    public CpuDiskMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold, Integer diskTreshold) {

        this.cpuTreshold = cpuTreshold;
        this.diskTreshold = diskTreshold;
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
            double[] diskStats = new double[n];
            double[] cpuStats = new double[n];
            Sigar sigar = new Sigar();
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
                try {
                    cpuStats[n - 1] = sigar.getCpuPerc().getUser();
                    System.out.println("cpu stats at " + date.toString() + " is: " + cpuStats[n - 1]);
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
            double diskAverage, diskSum = 0;
            double cpuAverage, cpuSum = 0;
            for (double d : diskStats) {
                diskSum += d;
            }
            diskAverage = diskSum / diskStats.length;
            for (double d : cpuStats) {
                cpuSum += d;
            }
            cpuAverage = cpuSum / cpuStats.length;
            if ((diskAverage > diskTreshold) || (cpuAverage > cpuTreshold / 100)) {
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
