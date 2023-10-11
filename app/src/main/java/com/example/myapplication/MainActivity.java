package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RelativeLayout mSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        assert 5 == 5;
        Log.d("mtest","assert 5 == 5;");

        //assert 5 == 6;
        ///Log.d("mtest","assert 5 == 6;");

        Log.d("mtest","aaaa");
        Log.println(Log.DEBUG,"mtest","aaa");
        BaseActivity.setDisplayFullScreen(getWindow());
        //receiveBroadcast();

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


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mSplashLogo = findViewById(R.id.splash_logo);
        addLogoImage();

        TextView textView = findViewById(R.id.tv_withdraw_commit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LibTaskHandler.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("mtest","LibTaskHandler.runOnUiThread :"+Thread.currentThread());
                            }
                        });
                    }
                }).start();


                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });

        textView.setText(String.format(getResources().getString(R.string.ans_progress),"9240"));


        //startActivity(new Intent(MainActivity.this,MainActivity2.class));

/*        mSplashLogo.post(new Runnable() {
            @Override
            public void run() {
                ScreenCutDisplayUtils.hasNotchInScreen(MainActivity.this);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //BaseActivity.fullScreen(MainActivity.this);
            }
        });*/


/*        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });*/

        changeAppIcon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseActivity.setDisplayFullScreen(getWindow());
    }

    public static float getScreenDensity(Context ctx){
        //从系统服务中获取窗口管理器
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        //从默认显示器中获取显示参数保存到dm对象中
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static int getStatusHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int statusBaHeight = 0;
        try {
            @SuppressLint("PrivateApi")
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBaHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBaHeight;
    }

    public void addLogoImage() {
        if (mSplashLogo == null) {
            return;
        }
        mSplashLogo.removeAllViews();
        int statusHeight = getStatusHeight(this);
        Log.d("mtest","statusHeight: "+statusHeight+" density: "+getScreenDensity(this));
        ImageView mIvSplashLogo = new ImageView(this);
        mIvSplashLogo.setImageResource(R.mipmap.privacy_logo);
        mIvSplashLogo.setScaleType(ImageView.ScaleType.CENTER);
        mIvSplashLogo.setBackgroundColor(Color.parseColor("#99ff00ff"));
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginTop = getResources().getDimensionPixelSize(R.dimen.splash_start_page_privacy_top);// - statusHeight;//全屏显示，所以不减云statusHeight
        Log.d("mtest","marginTop_: "+marginTop);
        rlParams.topMargin = marginTop;
        rlParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mSplashLogo.addView(mIvSplashLogo, rlParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private IntentFilter intentFilter;
    private void receiveBroadcast(){

        //添加多个事件action
        intentFilter = new IntentFilter("com.jix.broadcast.PULL_ALIVE");
        //intentFilter.addAction(YtbConstants.intentAction.ACTION_ADULT_SES_SWITCH_NOTIFY);
        PullLiveBroadcastReceiver mReceiver = new PullLiveBroadcastReceiver();
        registerReceiver(mReceiver, intentFilter);

    }

    public static Handler mHandler = new Handler();
    private PackageManager mPackageManager;
    private void changeAppIcon(){
//获取到包管理类实例
        mPackageManager = getPackageManager();
        ComponentName IndexActivityIcon = new ComponentName(getBaseContext(), "com.example.myapplication.MainActivityIcon");
        ComponentName IndexActivityNoIcon = new ComponentName(getBaseContext(), "com.example.myapplication.MainActivityNoIcon");
        enableComponent(IndexActivityIcon);
        disableComponent(IndexActivityNoIcon);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MainActivity.this.isFinishing() || MainActivity.this.isDestroyed()){
                    return;
                }
                enableComponent(IndexActivityNoIcon);
                disableComponent(IndexActivityIcon);
            }
        },10000);
    }

    /**
     * 启动组件
     * @param componentName 组件名
     */
    private void enableComponent(ComponentName componentName) {
        //此方法用以启用和禁用组件，会覆盖Androidmanifest文件下定义的属性
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     * @param componentName 组件名
     */
    private void disableComponent(ComponentName componentName) {
        mPackageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}