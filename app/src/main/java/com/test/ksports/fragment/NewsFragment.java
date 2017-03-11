package com.test.ksports.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.gson.Gson;
import com.test.ksports.R;
import com.test.ksports.activity.DetailActivity;
import com.test.ksports.adapter.NewsAdapter;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.constant.UrlConstants;
import com.test.ksports.db.DBManager;
import com.test.ksports.util.JsonTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by kingwag on 2017/2/7.
 * 新闻页面
 */

public class NewsFragment extends Fragment {
    private PtrFrameLayout ptrFrameLayout_main;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager=null;
    private List<NewsBean.DataBean.ArticlesBean> datas;
    private int curPage = 1;
    private Executor downloadExecutor;
    private DBManager dbManager;
    private int lastVisibleItem = 0;
    private JsonTask.OnDownloadLisntner downloadLisntner = new JsonTask.OnDownloadLisntner() {
        @Override
        public void onSuccess(String result) {

            if (!TextUtils.isEmpty(result)) {
                //解析Json
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(result, NewsBean.class);
                if (curPage==1){
                    datas.clear();
                }
                datas.addAll(newsBean.getData().getArticles());
                newsAdapter.notifyDataSetChanged();
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news, container, false);
        initDatabase();
        initView(view);
        initData();
     // initRefresh();
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData() {
        //开启异步任务，网络下载数据
        downloadExecutor = Executors.newFixedThreadPool(5);
        new JsonTask(UrlConstants.NEWS_URL1, downloadLisntner).executeOnExecutor(downloadExecutor);

    }

    /**
     * 初始化视图
     *
     * @param view
     */
    private void initView(View view) {
        datas = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), datas);
        newsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recy);
        ptrFrameLayout_main = (PtrFrameLayout) view.findViewById(R.id.frag);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //item点击事件，点击进入详情页面
        newsRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View itemview, int position) {
                NewsBean.DataBean.ArticlesBean articlesBean = datas.get(position);
                String itemUrl = articlesBean.getWeburl();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("itemUrl", itemUrl);
                startActivity(intent);
            }
        });
        newsRecyclerView.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void SimpleOnItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定收藏这条新闻？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewsBean.DataBean.ArticlesBean articlesBean = datas.get(position);
                                boolean result = dbManager.insert(articlesBean);
                                if (result){
                                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), "已经收藏过该条新闻", Toast.LENGTH_SHORT).show();
                                }
                                newsAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        //利用RecyclerView的滚动监听实现上拉加载下一页
        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem==newsAdapter.getItemCount()-1){
                    curPage++;
                    if (curPage==2){
                        new JsonTask(UrlConstants.NEWS_URL2, downloadLisntner).executeOnExecutor(downloadExecutor);
                    }else {
                        new JsonTask(UrlConstants.NEWS_URL3, downloadLisntner).executeOnExecutor(downloadExecutor);
                    }
                    // 刷新完成，让刷新Loading消失
                    ptrFrameLayout_main.refreshComplete();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头视图
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getContext());
        //设置头视图
        ptrFrameLayout_main.setHeaderView(defaultHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_main.addPtrUIHandler(defaultHeader);

        // 添加刷新动作监听
        ptrFrameLayout_main.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                curPage = 1;
                new JsonTask(UrlConstants.NEWS_URL1, downloadLisntner).executeOnExecutor(downloadExecutor);
            }
        });

    }

    /**
     * 初始化数据库
     */
    private void initDatabase(){
        dbManager = new DBManager(getContext());
    }

    /**
     * 刷新方法
     */
//    private void initRefresh() {
//        //设置刷新模式
//        newsRecyclerView.setPullRefreshEnabled(true);
//        newsRecyclerView.setLoadingMoreEnabled(true);
//        //设置刷新，加载样式
//        newsRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
//        newsRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
//        //刷新监听
//        newsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                //下拉刷新
//                new RefreshTask(UrlConstants.NEWS_URL1, 0).executeOnExecutor(downloadExecutor);
//            }
//
//            @Override
//            public void onLoadMore() {
//                //上拉加载更多
//                if (urlCount == 2) {
//                    new RefreshTask(UrlConstants.NEWS_URL2, 1).executeOnExecutor(downloadExecutor);
//                    newsAdapter.notifyDataSetChanged();
//                    urlCount++;
//                } /*else if (urlCount == 3) {
//                    new RefreshTask(UrlConstants.NEWS_URL3, 1).executeOnExecutor(downloadExecutor);
//                    newsAdapter.notifyDataSetChanged();
//                    urlCount++;
//                }*/
//            }
//        });
//    }


//    class RefreshTask extends AsyncTask<Void, Void, String> {
//        private String url;
//        private int refreshType; //0=下拉刷，1=上拉加载
//
//        public RefreshTask(String url, int refreshType) {
//            this.url = url;
//            this.refreshType = refreshType;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try {
//                String result = HttpUtil.getHttpData(url);
//
//                return result;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            if (!TextUtils.isEmpty(s)) {
//                Gson gson = new Gson();
//                NewsBean newsBean = gson.fromJson(s, NewsBean.class);
//                if (refreshType == 0) {
//                    //datas = newsBean.getData().getArticles();
//                    newsAdapter.notifyDataSetChanged();
//                    //停止刷新
//                    newsRecyclerView.refreshComplete();
//                } else {
//
//                    datas.addAll(newsBean.getData().getArticles());
//                    newsAdapter.notifyDataSetChanged();
//                    //停止刷新
//                    newsRecyclerView.loadMoreComplete();
//                }
//
//            }
//
//        }
//
//
//    }
}
