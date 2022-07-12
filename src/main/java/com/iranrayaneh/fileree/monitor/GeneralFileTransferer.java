package com.iranrayaneh.fileree.monitor;

import java.io.*;

class GeneralFileTransferer implements FileTransferer, Runnable {

    private int lines;
    private File srcFile;
    private File destFile;
    private StringBuffer fileBuffer;
    private ObjectOutputStream output;
    private BufferedReader input;
    private boolean hasCaptured = false;
    private StatusLabel statusLabel = StatusLabel.getInstance();

    public GeneralFileTransferer(File srcFile, File destFile, Integer lines) {
        this.lines = lines;
        this.srcFile = srcFile;
        this.destFile = destFile;
    }

    public void saveOnFile() {
        try {
            output = new ObjectOutputStream(new FileOutputStream(destFile.getAbsolutePath(),true));
            output.writeUTF(fileBuffer.substring(0));
            output.close();
            hasCaptured = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error opening File");
        }
    }

    public void gotoLastLine() {
        try {
            checkForNewerFile();
            input = new BufferedReader(new FileReader(srcFile));
            String line, last = "";
            while ((line = input.readLine()) != last) {
                last = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        statusLabel.appendLabel("پیدا کردن خط نهایی فایل لاگ");
    }

    private void checkForNewerFile() {

    }

    public void readLinesToBuffer() {
        try {
            fileBuffer = new StringBuffer();
            String line;
            for (int i = 0; i < lines; i++) {
                line = input.readLine();
                fileBuffer.append(line+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startRecording() {
        readLinesToBuffer();
        saveOnFile();
    }

    @Override
    public void startBuffering() {
        try {
            input = new BufferedReader(new FileReader(srcFile));
            fileBuffer.append(input.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
