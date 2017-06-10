package com.test.ksports.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.test.ksports.R;
import com.test.ksports.util.FullScreenUtil;
import com.test.ksports.util.SharedPreferencesUtil;

/**
 * 闪屏页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        FullScreenUtil.fullScreen(this);
        setContentView(R.layout.activity_splash);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    //跳转判断
                    jump();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    /**
     * 页面跳转
     */
    private void jump() {
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(SplashActivity.this, SharedPreferencesUtil.FIRST_OPEN, true);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // 如果不是第一次启动app，则正常显示启动屏
        startMainActivity();
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
