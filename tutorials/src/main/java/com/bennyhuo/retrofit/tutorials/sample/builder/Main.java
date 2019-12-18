package com.bennyhuo.retrofit.tutorials.sample.builder;

public class Main {
    public static void main(String... args) {
        Event fixedTimeEvent = new FixedTimeEventBuilder()
                .delay(100)
                .second(10)
                .minute(10)
                .build();

        Event periodEvent = new PeriodEventBuilder()
                .delay(100)
                .duration(1000)
                .build();
    }
}
