package com.iranrayaneh.fileree.monitor;

class InputValidator {

    public static boolean validate(MonitiorDto monitiorDto) {
        if (monitiorDto.getCpuTreshold().equals("") && monitiorDto.getRamTreshold().equals("") && monitiorDto.getDiskTreshold().equals("") ||
                monitiorDto.getStepDuration().equals("") || monitiorDto.getSteps().equals("") ||
                monitiorDto.getLines().equals("") || monitiorDto.getDuration().equals("")) return false;
        try {
            int cpu = monitiorDto.getCpuTreshold().equals("") ? 0 : Integer.valueOf(monitiorDto.getCpuTreshold());
            int ram = monitiorDto.getRamTreshold().equals("") ? 0 : Integer.valueOf(monitiorDto.getRamTreshold());
            int disk = monitiorDto.getDiskTreshold().equals("") ? 0 : Integer.valueOf(monitiorDto.getDiskTreshold());
            int step = monitiorDto.getSteps().equals("") ? 0 : Integer.valueOf(monitiorDto.getSteps());
            int stepdure = monitiorDto.getStepDuration().equals("") ? 0 : Integer.valueOf(monitiorDto.getStepDuration());
            int duration = monitiorDto.getDuration().equals("") ? 0 : Integer.valueOf(monitiorDto.getDuration());
            int cutLine = monitiorDto.getLines().equals("") ? 0 : Integer.valueOf(monitiorDto.getLines());
            return true;
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }
        return false;
    }
}
