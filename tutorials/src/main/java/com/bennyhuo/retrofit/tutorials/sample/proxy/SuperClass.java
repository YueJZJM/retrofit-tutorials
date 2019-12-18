package com.bennyhuo.retrofit.tutorials.sample.proxy;

public class SuperClass {

    private String value = "Hello";

    public SuperClass() {
    }

    public SuperClass(String value) {
        this.value = value;
    }

    public void hello(){
        System.out.println(value);
    }
}
