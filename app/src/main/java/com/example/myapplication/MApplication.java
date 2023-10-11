package com.example.myapplication;

import android.app.Application;
import android.util.Log;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("mtest","MApplication onCreate "+this.getApplicationContext());
    }
}
