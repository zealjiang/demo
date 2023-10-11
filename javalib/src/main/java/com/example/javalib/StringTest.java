package com.example.javalib;

import java.util.concurrent.atomic.AtomicReference;

public class StringTest {
    public static void main(String[] args) {
        String a = new String("abc");
        String a1 = "a" + "b" + "c";
        String b1 = "abc";
        String c1 = "abc";
        String d = new String("abc");
        String f = new String("abc");

        System.out.println("a: "+a.hashCode());
        System.out.println("a1: "+a1.hashCode());
        System.out.println("b1: "+b1.hashCode());
        System.out.println("c1: "+c1.hashCode());
        System.out.println("d: "+d.hashCode());
        System.out.println("f: "+f.hashCode());

        System.out.println("a == a1: "+(a == a1));
        System.out.println("a == b1: "+(a == b1));

        System.out.println("a1 == b1: "+(a1 == b1));
        System.out.println("a1.equals(b1): "+(a1.equals(b1)));

        System.out.println("c1 == d: "+(c1 == d));
        System.out.println("c1.equals(d): "+(c1.equals(d)));

        System.out.println("f == d: "+(f == d));

        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100, 127));
            System.out.println(atomicReference.compareAndSet(127, 100));
            System.out.println(atomicReference.get());
        },"T1").start();

    }
}
