package com.example.javalib;

import java.util.Random;

public class VolatileTest {
    //private volatile int num;
    private int num;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int v = 5+num;

            System.out.println("thread: "+Thread.currentThread().getName()+"   v: "+num);
            num = 3;
            System.out.println("thread: "+Thread.currentThread().getName()+"   3 ");
            if(true)return;
            for (int i = 0; i < 5; i++) {
                //synchronized (VolatileTest.class){
                    num = 1;
                System.out.println("thread: "+Thread.currentThread().getName()+"   v: "+num);
                //}


/*                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        }
    };

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(test.runnable,"t"+i);
            thread.start();
        }
    }
}
