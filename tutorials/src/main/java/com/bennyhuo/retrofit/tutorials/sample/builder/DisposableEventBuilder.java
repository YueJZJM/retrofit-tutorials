package com.bennyhuo.retrofit.tutorials.sample.builder;

public class DisposableEventBuilder extends Builder {

    @Override
    public Event build() {
        return new DisposableEvent();
    }
}
