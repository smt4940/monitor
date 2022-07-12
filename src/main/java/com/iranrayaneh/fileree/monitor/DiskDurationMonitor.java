package com.iranrayaneh.fileree.monitor;

public class DiskDurationMonitor extends Monitor implements Runnable{

    private int diskTreshold;
    public DiskDurationMonitor(Limiter limiter, FileTransferer transferer, Integer diskTreshold) {

        this.diskTreshold = diskTreshold;
    }

    @Override
    public void run() {

    }
}
