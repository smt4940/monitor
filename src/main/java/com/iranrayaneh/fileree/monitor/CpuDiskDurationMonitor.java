package com.iranrayaneh.fileree.monitor;

public class CpuDiskDurationMonitor extends Monitor implements Runnable {

    private int cpuTreshold;
    private int diskTreshold;

    public CpuDiskDurationMonitor(Limiter limiter ,FileTransferer fileTransferer, Integer cpuTreshold, Integer diskTreshold) {

        this.cpuTreshold = cpuTreshold;
        this.diskTreshold = diskTreshold;
    }

    @Override
    public void run() {

    }
}
