package com.example.javalib;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MyClass {

    private volatile int value;

    static {
        System.out.println("MyClass static");
        try {
            Field field = AtomicReference.class.getDeclaredField("value");
            System.out.println("MyClass static field: "+field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static class MyClassHolder{
        private MyClassHolder(){System.out.println("MyClassHolder 构造方法 ");}
        static {
            System.out.println("MyClassHolder static 静态方法  thread id: "+Thread.currentThread().getId());
        }
        private static MyClass instance = new MyClass(8);
    }

    private MyClass(int value){
        this.value = value;
        System.out.println("MyClass 构造方法"+this +"  thread id: "+Thread.currentThread().getId()+" value: "+value);
    }

    public static MyClass getInstance(){
        try {
            Thread.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("prepare get instance thread id: "+Thread.currentThread().getId());
        return MyClassHolder.instance;
    }

    public void doW(){
        System.out.println("method doW  "+this+"    thread id: "+Thread.currentThread().getId());

        //sun.misc.Unsafe
    }
}