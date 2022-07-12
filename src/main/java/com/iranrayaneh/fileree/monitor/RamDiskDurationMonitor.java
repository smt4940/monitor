package com.iranrayaneh.fileree.monitor;

public class RamDiskDurationMonitor extends Monitor implements Runnable {

    private int ramTreshold;
    private int diskTreshold;
    private GeneralFileTransferer fileTransferer;
    private long milisecondsToWait;
    private int steps;
    private Boolean stopped = false;
    private Boolean triggered = false;

    public RamDiskDurationMonitor(Limiter limiter, FileTransferer fileTransferer, Integer ramTreshold, Integer diskTreshold) {

        this.diskTreshold = diskTreshold;
        this.ramTreshold = ramTreshold;
        this.fileTransferer = (GeneralFileTransferer) fileTransferer;
        this.steps = limiter.getSteps();
        this.milisecondsToWait = 60000 * limiter.getStepDuration();
        this.fileTransferer.gotoLastLine();

    }

    @Override
    public void run() {

    }
}
