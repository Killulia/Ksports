package com.test.ksports.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kingwag on 2017/3/13.
 */

public class LoginPreferences {
    public static SharedPreferences getPrederences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
