package com.iranrayaneh.fileree.monitor;

public class CpuDurationMonitor extends Monitor implements Runnable {
    private int cpuTreshold;


    public CpuDurationMonitor(Limiter limiter, FileTransferer fileTransferer, int cpuTreshold) {

        this.cpuTreshold = cpuTreshold;
    }

    @Override
    public void run() {

    }
}
