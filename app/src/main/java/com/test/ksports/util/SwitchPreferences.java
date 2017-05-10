package com.test.ksports.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kingwag on 2017/3/13.
 */

public class SwitchPreferences {
    public static SharedPreferences getPrederences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putState(Context context,boolean state){
        SharedPreferences preferences = getPrederences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("state", state);
        editor.commit();

    }

    public static boolean getState(Context context){
        SharedPreferences preferences = getPrederences(context);
        boolean state = preferences.getBoolean("state", false);
        return state;
    }
}
