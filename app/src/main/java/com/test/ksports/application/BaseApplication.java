package com.test.ksports.application;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/5/24.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static List<ImageView> allImgs;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        allImgs = new ArrayList<>();
    }

    //有图模式
    public void enableImgs(){
        Toast.makeText(this, "有图调用", Toast.LENGTH_SHORT).show();
        for (ImageView img :allImgs) {
            img.setVisibility(View.VISIBLE);
        }
    }

    //无图模式
    public void disableImgs(){
        Toast.makeText(this, "无图调用", Toast.LENGTH_SHORT).show();
        for (ImageView img :allImgs) {
            img.setVisibility(View.INVISIBLE);
        }
    }

    public static BaseApplication getInstance(){
        return instance;
    }

    public static List<ImageView> getAllImgs() {
        return allImgs;
    }

    public void getSize(){
        Toast.makeText(this, "allImgs.size():" + allImgs.size(), Toast.LENGTH_SHORT).show();
    }
}
