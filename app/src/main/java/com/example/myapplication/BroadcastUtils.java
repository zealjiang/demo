package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

public class BroadcastUtils {

    private void sendBroadcastX(Context context){
        Intent intent = new Intent();
        intent.setAction("com.jix.broadcast.PULL_ALIVE");
        intent.putExtra("data","Notice me senpai!");
        intent.setPackage("com.example.myapplication");
        context.sendBroadcast(intent,"jix.permission.PULL_ALIVE");

        //带权限发送
/*        sendBroadcast(new Intent("com.example.NOTIFY"),
                Manifest.permission.SEND_SMS);*/
        //接收有权限
        //<uses-permission android:name="android.permission.SEND_SMS"/>
    }
}
