package com.test.ksports.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.db.DBManager;
import com.test.ksports.util.StatusbarUtil;
import com.test.ksports.util.SwitchPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻详情页面
 */
public class DetailActivity extends AppCompatActivity implements ShineButton.OnCheckedChangeListener{
    private ImageView imgDetail;
    private View detailView;
    private Toolbar toolbar;
    private TextView tvContent;
    private String finalContent = "";
    private List<String> pList = new ArrayList<>();
    private ShineButton mSave;
    private DBManager manager;
    private Context mContext;
    private NewsBean.DataBean.ArticlesBean bean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String> phList = new ArrayList<>();
            phList = (List<String>) msg.obj;
            //对每一个元素进行格式化，并最终拼接成一个格式化好的字符串设置给TextView
            for (int i = 0; i < phList.size(); i++) {
                //Html.fromHtml("<p>" + phList.get(i) + "</p>");
                finalContent = finalContent + "  "+ Html.fromHtml("<p>" + phList.get(i) + "</p>");
            }
            tvContent.setText(finalContent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtil.setStatusBarColor(this,getResources().getColor(R.color.red));
        setContentView(R.layout.activity_detail2);
        mContext = this;
        Intent intent = getIntent();
        String url = intent.getStringExtra("itemUrl");
        String img = intent.getStringExtra("itemImg");
        String author = intent.getStringExtra("itemAuthor");
        bean = (NewsBean.DataBean.ArticlesBean) intent.getSerializableExtra("item");
        initData();
        initTollbar(author);
        //initWebView(url);
        initView(img);
        initShineButton();
        initContent(author, url);

    }

    private void initShineButton() {
        boolean isCheck = SwitchPreferences.getState(mContext,bean.getWeburl());
        mSave.setChecked(isCheck);
    }

    private void initData() {
        manager = new DBManager(mContext);
    }

    private void initContent(final String author, final String url) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    Elements content;
                    switch (author) {
                        case "虎扑篮球":
                            content = doc.select("div.artical-main-content").select("P");
                            //获取每个<>标签并添加到字符串集合
                            for (Element element : content) {
                                String newContent = element.text();
                                Log.d("kingwag", "内容是:" + newContent);
                                pList.add(newContent);

                            }
                           Message msg = new Message();
                            msg.obj = pList;
                            handler.sendMessage(msg);
                            break;
                        case "腾讯体育":
                            content = doc.select("div.tpl_main").select("P");
                            //获取每个<>标签并添加到字符串集合
                            for (Element element : content) {
                                if (element.text()!=null){
                                    String newContent = element.text();
                                    Log.d("kingwag", "内容是:" + newContent);
                                    pList.add(newContent);
                                }


                            }
                            Message msg2 = new Message();
                            msg2.obj = pList;
                            handler.sendMessage(msg2);

                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    private void initTollbar(String tittleString) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(tittleString);
        setSupportActionBar(toolbar);
        //初始化 ActionBar
        ActionBar actionBar = getSupportActionBar();
        //添加返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView(String imgUrl) {
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        tvContent = (TextView) findViewById(R.id.tv_detail);
        Picasso.with(this).load(imgUrl).fit().into(imgDetail);
        mSave = (ShineButton) findViewById(R.id.detail_save);
        mSave.setOnCheckStateChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //返回按钮
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(View view, boolean checked) {
        switch (view.getId()){
            case R.id.detail_save:
                if (checked){
                    boolean result = manager.insert(bean, 1);
                    if (!result) {
                        Toast.makeText(mContext, "已经收藏过", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    manager.delete(bean.getPk(),1);
                    Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                    Log.d("DetailActivity", "id is"+bean.getPk());
                }
                SwitchPreferences.putState(mContext,bean.getWeburl(),checked);
                break;
        }
    }
}
