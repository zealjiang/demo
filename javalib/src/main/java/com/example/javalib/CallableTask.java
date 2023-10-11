package com.example.javalib;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Boolean> {

    private volatile boolean doSuc;
    private String name;

    public CallableTask(String name){
        this.name = name;
    }
    @Override
    public Boolean call() throws Exception {
        System.out.println("新任务开始执行 "+name+"  "+Thread.currentThread().getId());
        ExcutorTest.getCachedService().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("新任务开始执行 "+name);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("耗时任务执行完成"+name);
                doSuc = true;

/*                synchronized (CallableTask.class) {
                    CallableTask.class.notify();
                }*/
            }
        });

/*        synchronized (CallableTask.class) {
            try {
                CallableTask.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        return doSuc;
    }
}
