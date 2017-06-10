package com.test.ksports.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.test.ksports.R;
import com.test.ksports.adapter.HistoryAdapter;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.db.DBManager;
import com.test.ksports.util.ScrollListView;
import com.test.ksports.util.StatusbarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 历史记录页面
 */
public class HistoryActivity extends AppCompatActivity {
    private DBManager dbManager;//数据库管理
    private Context mContext;//上下文
    private ListView mListView;//列表
    private HistoryAdapter mAdapter;//列表适配器
    private List<NewsBean.DataBean.ArticlesBean> mList;//新闻集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtil.setStatusBarColor(this, getResources().getColor(R.color.red));
        setContentView(R.layout.activity_history);
        mContext = this;
        dbManager = new DBManager(mContext);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mList = dbManager.getArticles(2);
        Collections.reverse(mList);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_history);
        mAdapter = new HistoryAdapter(mContext, mList);
        mListView.setAdapter(mAdapter);
        View emptyView = findViewById(R.id.empty_include);
        mListView.setEmptyView(emptyView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.DataBean.ArticlesBean articlesBean = mList.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("item", articlesBean);
                startActivity(intent);
            }
        });

    }

}
