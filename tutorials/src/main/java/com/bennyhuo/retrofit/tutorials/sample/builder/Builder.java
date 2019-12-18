package com.bennyhuo.retrofit.tutorials.sample.builder;

public abstract class Builder<T extends Builder> {

    private long delay;

    public long getDelay() {
        return delay;
    }

    public T delay(long delay) {
        this.delay = delay;
        return (T) this;
    }

    public abstract Event build();
}
