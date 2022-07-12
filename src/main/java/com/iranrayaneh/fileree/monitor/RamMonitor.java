package com.iranrayaneh.fileree.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class RamMonitor extends Monitor implements Runnable {

    private int ramTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;

    public Boolean getStopped() {
        return stopped;
    }

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }

    private Boolean stopped = false;
    private Boolean triggered = false;
    private int hours;

    public RamMonitor(Limiter limiter, FileTransferer transferer, Integer ramTreshold) {
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
        while(!stopped && h<hours) {
            int n = steps;
            double[] ramStats = new double[n];
            Sigar sigar = new Sigar();
            while (!stopped && n > 0) {
                try {
                    Date date = new Date();
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
            }
            double average, sum = 0;
            for (double d : ramStats) {
                sum += d;
            }
            average = sum / ramStats.length;
            if (average > ramTreshold / 100) {
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
