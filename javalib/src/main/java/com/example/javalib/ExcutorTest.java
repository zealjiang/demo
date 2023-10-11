package com.example.javalib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExcutorTest {

    private static ExecutorService sCachedService = Executors.newCachedThreadPool();

    public static ExecutorService getCachedService() {
        return sCachedService;
    }

    public static void main(String[] args) {
        try{
            CallableTask task = new CallableTask("task 1");
            Future<Boolean> future = getCachedService().submit(task);
            System.out.println("任务执行完了吗？");
            //getCachedService().submit(new CallableTask("task 2"));
            System.out.println("future :"+future.get());
            //System.out.println("future :");
            //getCachedService().submit(new CallableTask("task 3"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
