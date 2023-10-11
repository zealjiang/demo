package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    public static boolean isCutOutScreen = false;//是否是刘海屏手机
    public static void fullScreen(Activity activity) {
        if(activity == null)return;
        Window window = activity.getWindow();
        if(window == null)return;
        fullScreen(window);
    }

    public static void setDisplayFullScreen(Window window){
        if(window == null)return;
        try{
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
/*        WindowInsetsController controller = window.getInsetsController();
        controller.hide(WindowInsets.Type.statusBars());*/
        }catch (Exception e){}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        //| View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                ;
                decorView.setSystemUiVisibility(option);

                //隐藏刘海屏
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    WindowManager.LayoutParams lp = window.getAttributes();
                    //在竖屏模式和横屏模式下，内容都会呈现到刘海区域中。
                    lp.layoutInDisplayCutoutMode =  WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    window.setAttributes(lp);
                }
            }else {
                try {
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                }catch (Exception e){}
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    public static void fullScreen(Window window) {
        if(window == null)return;
/*        if(!isCutOutScreen){
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            return;
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                }catch (Exception e){}
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        ;//| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                decorView.setSystemUiVisibility(option);

                //隐藏刘海屏
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    WindowManager.LayoutParams lp = window.getAttributes();
                    //在竖屏模式和横屏模式下，内容都会呈现到刘海区域中。
                    lp.layoutInDisplayCutoutMode =  WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    window.setAttributes(lp);
                }

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏为透明，否则在部分手机上会呈现系统默认的浅灰色
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以考虑设置为透明色
                //window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                try {
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                }catch (Exception e){}
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
}