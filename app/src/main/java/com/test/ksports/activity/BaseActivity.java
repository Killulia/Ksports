package com.test.ksports.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.test.ksports.utilinterface.NoTittleable;

public class BaseActivity extends AppCompatActivity implements NoTittleable{


    @Override
    public void fullScreen() {
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN,WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }
}
