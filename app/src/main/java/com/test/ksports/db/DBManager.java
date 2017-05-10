package com.test.ksports.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.ksports.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/2/12.
 * 数据库管理类
 */

public class DBManager {

    public static final String TABLE_NAME_FAV = "news";

    public static final String TABLE_NAME_HISTORY = "history";

    private SQLiteOpenHelper helper;

    public DBManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加数据
     * @param articlesBean
     */
    public  boolean insert(NewsBean.DataBean.ArticlesBean articlesBean,int type){
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //插入数据
        ContentValues cv = new ContentValues();
        cv.put("pk",articlesBean.getPk());
        cv.put("tittle",articlesBean.getTitle());
        cv.put("auther",articlesBean.getAuther_name());
        cv.put("img",articlesBean.getThumbnail_pic());
        cv.put("url", articlesBean.getWeburl());
        long result;
        if (type == 1){
            result  = db.insert(TABLE_NAME_FAV,null,cv);
        }else {
            result  = db.insert(TABLE_NAME_HISTORY,null,cv);
        }
        //关闭数据库
        db.close();
        Log.d("king", "result is "+result);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 删除数据
     * @param pk
     */
    public void delete(String pk){
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //删除数据
        long result = db.delete(TABLE_NAME_FAV, "pk = ?", new String[]{pk});
        //关闭数据库
        db.close();


    }

    /**
     * 查询数据
     * @return
     */
    public List<NewsBean.DataBean.ArticlesBean> getArticles(int type){
        List<NewsBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //查询数据
        String tableName;
        if (type == 1){
            tableName = TABLE_NAME_FAV;
        }else {
            tableName = TABLE_NAME_HISTORY;
        }
        Cursor cursor = db.query(
                tableName,
                null,
                null,
                null,
                null,
                null,
                null
        );
        int pkIndex = cursor.getColumnIndex("pk");
        int tittleIndex = cursor.getColumnIndex("tittle");
        int autherIndex = cursor.getColumnIndex("auther");
        int imgIndex = cursor.getColumnIndex("img");
        int urlIndex = cursor.getColumnIndex("url");
        while (cursor.moveToNext()){
            String pk = cursor.getString(pkIndex);
            String tittle = cursor.getString(tittleIndex);
            String auther = cursor.getString(autherIndex);
            String img = cursor.getString(imgIndex);
            String url = cursor.getString(urlIndex);
            NewsBean.DataBean.ArticlesBean bean = new NewsBean.DataBean.ArticlesBean();
            bean.setPk(pk);
            bean.setTitle(tittle);
            bean.setAuther_name(auther);
            bean.setThumbnail_pic(img);
            bean.setWeburl(url);
            articlesBeanList.add(bean);
        }
        //关闭数据库
        db.close();
        return articlesBeanList;
    }

}
