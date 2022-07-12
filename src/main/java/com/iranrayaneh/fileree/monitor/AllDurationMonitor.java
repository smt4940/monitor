package com.iranrayaneh.fileree.monitor;

public class AllDurationMonitor extends Monitor implements Runnable {

    private int cpuTreshold;
    private int ramTreshold;
    private int diskTreshold;

    public AllDurationMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold, Integer
            ramTreshold, Integer diskTreshold) {
        this.cpuTreshold = cpuTreshold;
        this.ramTreshold = ramTreshold;
        this.diskTreshold = diskTreshold;

    }

    @Override
    public void run() {

    }
}
