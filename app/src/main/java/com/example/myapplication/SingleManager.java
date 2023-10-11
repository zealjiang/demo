package com.example.myapplication;

import android.content.Context;
import android.util.Log;

public class SingleManager {

    private static class SingleManagerHolder{
        private static SingleManager instance = new SingleManager();
    }

    public static SingleManager getInstance(Context context){
        return SingleManagerHolder.instance;
    }

    public void init(){
        Log.d("mtest","SingleManager init 初始化完成");
    }
}
