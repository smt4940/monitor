package com.iranrayaneh.fileree.monitor;

public class CpuRamDurationMonitor extends Monitor implements Runnable {

    private int cpuTreshold;
    private int ramTreshold;

    public CpuRamDurationMonitor(Limiter limiter, FileTransferer fileTransferer, Integer cpuTreshold, Integer ramTreshold) {

        this.cpuTreshold = cpuTreshold;
        this.ramTreshold = ramTreshold;
    }

    @Override
    public void run() {

    }
}
