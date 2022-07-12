package com.iranrayaneh.fileree.monitor;

public class Limiter {
    private int steps;
    private int stepDuration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int duration;

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(int stepDuration) {
        this.stepDuration = stepDuration;
    }

    public Limiter(int steps, int stepDuration, int duration) {
        this.steps = steps;
        this.stepDuration = stepDuration;
        this.duration = duration;
    }
}
