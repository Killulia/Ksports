package com.test.ksports.apiservice;

import com.test.ksports.bean.AgendaBean;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.bean.SocerBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kingwag on 2017/6/5.
 * Api接口
 */

public interface ApiService {

    //比赛页_篮球
    @GET("nba?key=58e1b5e3c751af4f3c12c54676b6f309")
    Call<AgendaBean> getBasketballAgenda();

    //比赛页_足球
    @GET("league?key=7b46ae851f90a2125f111c638e27ab4c&league=%E8%A5%BF%E7%94%B2")
    Call<SocerBean> getSocerAgenda();

    //新闻页_热门1
    @GET("blog.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=724")
    Call<NewsBean> getNews1_1Call();

    //新闻页_热门2
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12844")
    Call<NewsBean> getNews1_2Call();

    //新闻页_杂谈1
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.7&app_id=12985")
    Call<NewsBean> getNews2_1Call();

    //新闻页_杂谈2
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12347")
    Call<NewsBean> getNews2_2Call();

    //新闻页_杂谈3
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=1261")
    Call<NewsBean> getNews2_3Call();

    //新闻页_装备
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=13161")
    Call<NewsBean> getNews3_1Call();

    //新闻页_健身
    @GET("news.php?_appid=AndroidPhone&_bsize=1080_1920&_version=7.3&app_id=12867")
    Call<NewsBean> getNews4_1Call();
}
