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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private DBManager dbManager;
    private Context mContext;
    private ListView mListView;
    private HistoryAdapter mAdapter;
    private List<NewsBean.DataBean.ArticlesBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mContext = this;
        dbManager = new DBManager(mContext);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList = dbManager.getArticles(2);
        Collections.reverse(mList);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_history);
        mAdapter = new HistoryAdapter(mContext, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(   new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean.DataBean.ArticlesBean articlesBean = mList.get(position);
                String itemUrl = articlesBean.getWeburl();
                String itemImg = articlesBean.getThumbnail_pic();
                String itemAuthor = articlesBean.getAuther_name();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("itemUrl", itemUrl);
                intent.putExtra("itemImg", itemImg);
                intent.putExtra("itemAuthor", itemAuthor);
                intent.putExtra("item", articlesBean);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
