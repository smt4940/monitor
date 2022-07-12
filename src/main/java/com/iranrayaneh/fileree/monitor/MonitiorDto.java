package com.iranrayaneh.fileree.monitor;

public class MonitiorDto {

    private final String cpuTreshold;
    private final String ramTreshold;
    private final String diskTreshold;
    private final String steps;
    private final String stepDuration;
    private final String lines;
    private final String duration;

    public String getCpuTreshold() {
        return cpuTreshold;
    }

    public String getRamTreshold() {
        return ramTreshold;
    }

    public String getDiskTreshold() {
        return diskTreshold;
    }

    public String getSteps() {
        return steps;
    }

    public String getStepDuration() {
        return stepDuration;
    }

    public String getLines() {
        return lines;
    }

    public String getDuration() {
        return duration;
    }

    private MonitiorDto(Builder builder) {
        cpuTreshold = builder.cpuTreshold;
        ramTreshold = builder.ramTreshold;
        diskTreshold = builder.diskTreshold;
        steps = builder.steps;
        stepDuration = builder.stepDuration;
        lines = builder.lines;
        duration = builder.duration;
    }

    public static class Builder {

        private String cpuTreshold;
        private String ramTreshold;
        private String diskTreshold;
        private String steps;
        private String stepDuration;
        private String lines;
        private String duration;

        public Builder() {

        }

        public Builder cpuTreshold(String val) {
            cpuTreshold = val;
            return this;
        }

        public Builder ramTreshold(String val) {
            ramTreshold = val;
            return this;
        }

        public Builder diskTreshold(String val) {
            diskTreshold = val;
            return this;
        }

        public Builder steps(String val) {
            steps = val;
            return this;
        }

        public Builder stepDuration(String val) {
            stepDuration = val;
            return this;
        }

        public Builder lines(String val) {
            lines = val;
            return this;
        }

        public Builder duration(String val) {
            duration = val;
            return this;
        }

        public MonitiorDto build() {
            return new MonitiorDto(this);
        }

    }
}
