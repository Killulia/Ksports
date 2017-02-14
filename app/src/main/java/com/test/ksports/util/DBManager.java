package com.test.ksports.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.test.ksports.bean.NewsBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/2/12.
 * 数据库管理类
 */

public class DBManager {

    public static final String TABLE_NAME = "news";

    private SQLiteOpenHelper helper;

    public DBManager(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加数据
     * @param articlesBean
     */
    public  boolean insert(NewsBean.DataBean.ArticlesBean articlesBean){
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //插入数据
        ContentValues cv = new ContentValues();
        cv.put("tittle",articlesBean.getTitle());
        cv.put("auther",articlesBean.getAuther_name());
        cv.put("img",articlesBean.getThumbnail_pic());
        cv.put("url", articlesBean.getWeburl());
        long result = db.insert(TABLE_NAME,null,cv);
        //关闭数据库
        db.close();
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 删除数据
     * @param id
     */
    public boolean delete(int id){
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //删除数据
        long result = db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
        //关闭数据库
        db.close();
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 查询数据
     * @return
     */
    public List<NewsBean.DataBean.ArticlesBean> getArticles(){
        List<NewsBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();
        //打开数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //查询数据
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        int idIndex = cursor.getColumnIndex("_id");
        int tittleIndex = cursor.getColumnIndex("tittle");
        int autherIndex = cursor.getColumnIndex("auther");
        int imgIndex = cursor.getColumnIndex("img");
        int urlIndex = cursor.getColumnIndex("url");
        while (cursor.moveToNext()){
            int id = cursor.getInt(idIndex);
            String tittle = cursor.getString(tittleIndex);
            String auther = cursor.getString(autherIndex);
            String img = cursor.getString(imgIndex);
            String url = cursor.getString(urlIndex);
            NewsBean.DataBean.ArticlesBean bean = new NewsBean.DataBean.ArticlesBean();
            bean.setId(id);
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
