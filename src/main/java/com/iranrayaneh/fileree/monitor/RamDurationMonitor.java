package com.iranrayaneh.fileree.monitor;

public class RamDurationMonitor extends Monitor implements Runnable {
    private int ramTreshold;
    public RamDurationMonitor(Limiter limiter, FileTransferer transfer, Integer ramTreshold) {

        this.ramTreshold = ramTreshold;
    }

    @Override
    public void run() {

    }
}
