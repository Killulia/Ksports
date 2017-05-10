package com.test.ksports.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kingwag on 2017/2/12.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "sports.db";

    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //通过约束tittle,和URL避免数据重复
        db.execSQL("create table news(_id INTEGER PRIMARY KEY AUTOINCREMENT, tittle TEXT UNIQUE,auther TEXT,img TEXT,url TEXT UNIQUE);");
        db.execSQL("create table history(_id INTEGER PRIMARY KEY AUTOINCREMENT, tittle TEXT UNIQUE,auther TEXT,img TEXT,url TEXT UNIQUE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
