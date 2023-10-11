package com.example.javalib;

public class BinaryTest {
    public static void main(String[] args) {
        int  head = 0;
        Object[] elements = new Object[16];
        head = (head - 1) & (elements.length - 1);
        System.out.println("head: "+head);
        System.out.println("-1 : "+Integer.toBinaryString(-1));
        System.out.println("15 : "+Integer.toBinaryString(15));
        head = 16 & (elements.length - 1);
        System.out.println("head : "+Integer.toBinaryString(head));

        System.out.println("-2 : "+Integer.toBinaryString(-2));

    }
}
