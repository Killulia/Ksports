package com.test.ksports.apiservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kingwag on 2017/6/5.
 */

public interface ApiService {
    //新闻页_热门1
    @GET("blog.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=724")
    Call<ResponseBody> getNews1_1Call();

    //新闻页_热门2
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12844")
    Call<ResponseBody> getNews1_2Call();

    //新闻页_杂谈1
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.7&app_id=12985")
    Call<ResponseBody> getNews2_1Call();

    //新闻页_杂谈2
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12347")
    Call<ResponseBody> getNews2_2Call();

    //新闻页_杂谈3
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=1261")
    Call<ResponseBody> getNews2_3Call();

    //新闻页_装备
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=13161")
    Call<ResponseBody> getNews3_1Call();

    //新闻页_健身
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12867")
    Call<ResponseBody> getNews4_1Call();
}
