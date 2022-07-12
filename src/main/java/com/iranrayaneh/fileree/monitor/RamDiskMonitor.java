package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class RamDiskMonitor extends Monitor implements Runnable {
    private int ramTreshold;
    private int diskTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private int hours;
    private Boolean stopped = false;
    private Boolean triggered = false;

    public RamDiskMonitor(Limiter limiter, FileTransferer fileTransferer, Integer ramTreshold, Integer diskTreshold) {

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
            int n = steps;
            double[] diskStats = new double[n];
            double[] ramStats = new double[n];
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
                    diskStats[n - 1] = sigar.getDiskUsage("C:").getServiceTime();
                    ramStats[n - 1] = sigar.getMem().getUsedPercent();
                    System.out.println("ram stats at " + date.toString() + " is: " + ramStats[n - 1]);
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
            double ramAverage, ramSum = 0;
            for (double d : diskStats) {
                diskSum += d;
            }
            diskAverage = diskSum / diskStats.length;
            for (double d : ramStats) {
                ramSum += d;
            }
            ramAverage = ramSum / ramStats.length;
            if ((diskAverage > diskTreshold) || (ramAverage > ramTreshold / 100)) {
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
