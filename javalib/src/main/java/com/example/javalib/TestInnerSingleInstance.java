package com.example.javalib;

import java.text.DateFormat;
import java.util.Date;

public class TestInnerSingleInstance {

    public static void main(String[] args) {
        Singleton.getInstance();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(DateFormat.getInstance().format(new Date())+"  getMyClassInstance thread id: "+Thread.currentThread().getId());
                    MyClass.getInstance().doW();
                }
            }).start();
        }
    }
}
