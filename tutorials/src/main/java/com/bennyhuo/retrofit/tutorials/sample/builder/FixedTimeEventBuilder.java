package com.bennyhuo.retrofit.tutorials.sample.builder;

public class FixedTimeEventBuilder extends Builder<FixedTimeEventBuilder> {

    private int second;
    private int minute;

    public int getSecond() {
        return second;
    }

    public FixedTimeEventBuilder second(int second) {
        this.second = second;
        return this;
    }

    public int getMinute() {
        return minute;
    }

    public FixedTimeEventBuilder minute(int minute) {
        this.minute = minute;
        return this;
    }

    @Override
    public Event build() {
        return new FixedTimeEvent();
    }
}
