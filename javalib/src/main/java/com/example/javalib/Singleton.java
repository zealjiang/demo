package com.example.javalib;

public enum Singleton {
    INSTANCE;
    private Singleton() {}
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
