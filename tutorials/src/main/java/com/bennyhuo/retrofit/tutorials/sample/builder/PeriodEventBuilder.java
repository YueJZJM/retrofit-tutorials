package com.bennyhuo.retrofit.tutorials.sample.builder;

public class PeriodEventBuilder extends Builder<PeriodEventBuilder> {

    private int duration;

    public int getDuration() {
        return duration;
    }

    public PeriodEventBuilder duration(int second) {
        this.duration = second;
        return this;
    }

    @Override
    public Event build() {
        return new PeriodEvent();
    }
}
