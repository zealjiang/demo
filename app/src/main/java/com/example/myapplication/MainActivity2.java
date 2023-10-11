package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity2 extends Activity {//AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //hideStatusbar();
        //setSystemUIListener();
/*        //设置状态栏背景色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0版本号
            //     * For this to take effect,
            //     * the window must be drawing the system bar backgrounds with
            //     * {@link android.view.WindowManager.LayoutParams#FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS} and
            //     * {@link android.view.WindowManager.LayoutParams#FLAG_TRANSLUCENT_STATUS} must not be set.
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#ffff00"));//核心方法
        }
        //设置状态图标和文字颜色，系统只支持亮色和默认色，亮色即图标和文字是黑色；默认色即图标和文字是白色
        boolean isLightStatusIcon = true;
        if(isLightStatusIcon){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Build.VERSION_CODES.M 对应的版本是6.0
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色图标和字体
            }
        }else {
            //默认如果不做任何设置状态栏的图标和文字是白色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//默认白色图标和字体
        }*/
        //setContentView(R.layout.activity_main2);

/*        //设置状态栏背景色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0版本号
            //     * For this to take effect,
            //     * the window must be drawing the system bar backgrounds with
            //     * {@link android.view.WindowManager.LayoutParams#FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS} and
            //     * {@link android.view.WindowManager.LayoutParams#FLAG_TRANSLUCENT_STATUS} must not be set.
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS This code is deprecated in API 30
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            getWindow().setStatusBarColor(Color.parseColor("#ffff00"));//核心方法
        }
        //设置状态图标和文字颜色，系统只支持亮色和默认色，亮色即图标和文字是黑色；默认色即图标和文字是白色
        boolean isLightStatusIcon = true;
        if(isLightStatusIcon){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Build.VERSION_CODES.M 对应的版本是6.0
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController windowInsetsController = getWindow().getDecorView().getWindowInsetsController();
                    windowInsetsController.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                }else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色图标和字体
                }
            }
        }else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsController windowInsetsController = getWindow().getDecorView().getWindowInsetsController();
                windowInsetsController.setSystemBarsAppearance(0,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
            }else{
                //默认如果不做任何设置状态栏的图标和文字是白色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//默认白色图标和字体
            }
        }*/
        BaseActivity.setDisplayFullScreen(getWindow());
        setContentView(R.layout.activity_main2);

/*        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatusbar();
            }
        });*/
    }


    private void hideStatusbar(){
        hideSystemUI();
        //隐藏刘海屏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            //在竖屏模式和横屏模式下，内容都会呈现到刘海区域中。
            lp.layoutInDisplayCutoutMode =  WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    private void setSystemUIListener(){
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // TODO: The system bars are visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                } else {
                    // TODO: The system bars are NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                }
            }
        });
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        //如果为SYSTEM_UI_FLAG_IMMERSIVE_STICKY时，滑动操作会使系统界面暂时以半透明状态显示，但不会清除任何标记，并且不会触发系统界面可见性更改监听器
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}