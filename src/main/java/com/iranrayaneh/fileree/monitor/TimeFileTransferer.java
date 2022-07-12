package com.iranrayaneh.fileree.monitor;

import java.io.File;

public class TimeFileTransferer implements FileTransferer,Runnable {
    private File srcFile;
    private File destFile;
    private int minutes;

    public TimeFileTransferer(File srcFile, File destFile, int minutes) {
        this.srcFile = srcFile;
        this.destFile = destFile;
        this.minutes = minutes;
    }

    @Override
    public void run() {

    }

    @Override
    public void startRecording() {

    }

    @Override
    public void startBuffering() {

    }
}
