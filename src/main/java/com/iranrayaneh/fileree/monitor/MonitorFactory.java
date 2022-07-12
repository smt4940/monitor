package com.iranrayaneh.fileree.monitor;

import java.io.File;

public class MonitorFactory {
    private static Limiter limiter;
    private static FileTransferer fileTransferer;

    public static Runnable init(MonitiorDto monitiorDto, File srcFile, File destFile) {
        limiter = new Limiter(Integer.valueOf(monitiorDto.getSteps()), Integer.valueOf(monitiorDto.getStepDuration()), Integer.valueOf(monitiorDto.getDuration()));
        if (monitiorDto.getCpuTreshold().equals("") && monitiorDto.getRamTreshold().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new DiskMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getDiskTreshold()));
        } else if (monitiorDto.getRamTreshold().equals("") && monitiorDto.getDiskTreshold().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new CpuMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getCpuTreshold()));
        } else if (monitiorDto.getCpuTreshold().equals("") && monitiorDto.getDiskTreshold().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new RamMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getRamTreshold()));
        } else if (monitiorDto.getCpuTreshold().equals("") && monitiorDto.getDuration().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new RamDiskMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getRamTreshold()), Integer.valueOf(monitiorDto.getDiskTreshold()));
        } else if (monitiorDto.getRamTreshold().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new CpuDiskMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getCpuTreshold()), Integer.valueOf(monitiorDto.getDiskTreshold()));
        } else if (monitiorDto.getDiskTreshold().equals("")) {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new CpuRamMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getCpuTreshold()), Integer.valueOf(monitiorDto.getRamTreshold()));
        } else {
            fileTransferer = new GeneralFileTransferer(srcFile, destFile, Integer.valueOf(monitiorDto.getLines()));
            return new AllMonitor(limiter, fileTransferer, Integer.valueOf(monitiorDto.getCpuTreshold()), Integer.valueOf(monitiorDto.getRamTreshold()), Integer.valueOf(monitiorDto.getDiskTreshold()));
        }
    }
}
