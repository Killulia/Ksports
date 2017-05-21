package com.test.ksports.util;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by kingwag on 2017/5/21.
 */

public class FullScreenUtil {
    public static void fullScreen(Activity activity){
        //去掉标题栏
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
        activity.getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN,WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }
}
