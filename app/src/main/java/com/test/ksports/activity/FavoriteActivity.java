package com.test.ksports.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.test.ksports.R;
import com.test.ksports.adapter.NewsAdapter;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.db.DBManager;
import com.test.ksports.util.EmptyRecyclerView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private EmptyRecyclerView favRecyclerView;
    private NewsAdapter favAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NewsBean.DataBean.ArticlesBean> datas;
    private DBManager dbManager;
    private Context mContext;
    private RelativeLayout emptyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mContext = this;
        initDatabase();
        initData();
        initView();


    }


    /**
     * 初始化数据
     */
    private void initData() {

        datas= dbManager.getArticles();

    }


    /**
     * 初始化数据库
     */
    private void initDatabase(){
        dbManager = new DBManager(this);
    }

    /**
     * 初始化视图
     *
     *
     */
    private void initView( ) {
        //数据为空时显示的布局
        View emptyView =(RelativeLayout) findViewById(R.id.empty_include);
        favAdapter = new NewsAdapter(this, datas);
        favRecyclerView = (EmptyRecyclerView) findViewById(R.id.fav_recy);
        favRecyclerView.setAdapter(favAdapter);
        layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        favRecyclerView.setLayoutManager(layoutManager);
        //设置空布局
        favRecyclerView.setEmptyView(emptyView);
        //item点击事件，点击进入详情页面
        favRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View itemview, int position) {
                NewsBean.DataBean.ArticlesBean articlesBean = datas.get(position);
                String itemUrl = articlesBean.getWeburl();
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra("itemUrl", itemUrl);
                startActivity(intent);
            }
        });
        favRecyclerView.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void SimpleOnItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确定删除这条新闻？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewsBean.DataBean.ArticlesBean articlesBean = datas.get(position);
                                dbManager.delete(articlesBean.getId());
                                favAdapter.remove(position);
                                favAdapter.notifyDataSetChanged();


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

    }
}
