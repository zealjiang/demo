package com.example.myapplication;

//import android.util.Log;

import android.util.Log;

public class TestInnerClass {
    final class BackStackRecord{
        private int mAge;
    }

    public void forTest(){
        boolean didSomething;
        for(didSomething = false; 3 < 5; didSomething = true) {
            Log.d("mtest","for --didSomething: "+didSomething);

            if(didSomething)return;

        }
    }

}
