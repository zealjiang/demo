package com.example.javalib;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                int v = atomicInteger.incrementAndGet();
                System.out.println("thread: "+Thread.currentThread().getName()+"   v: "+v);
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args) {
        AtomicIntegerTest test = new AtomicIntegerTest();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(test.runnable,"t"+i);
            thread.start();
        }
    }
}
