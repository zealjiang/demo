package com.example.javalib;

public class DefaultInterfaceTest {
    public static void main(String[] args) {
        ChildN childN = new ChildN();
        childN.abstractM();
        childN.real();
    }
}

interface InterfaceTest {

    void abstractM();
    default void real(){
        System.out.println("InterfaceTest real");
    }
}

class ChildN extends ParentN implements InterfaceTest{
    @Override
    public void abstractM() {
        System.out.println("ChildN abstractM");
    }
}

class ParentN{
    void abstractM(){
        System.out.println("ParentN abstractM");
    }
    public void real(){
        System.out.println("ParentN real");
    }
}
