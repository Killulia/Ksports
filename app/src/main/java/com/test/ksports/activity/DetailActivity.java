package com.test.ksports.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.ksports.R;

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
public class DetailActivity extends AppCompatActivity {
    private ImageView imgDetail;
    private View detailView;
    private Toolbar toolbar;
    private TextView tvContent;
    private String finalContent = "";
    private List<String> pList = new ArrayList<>();
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
        setContentView(R.layout.activity_detail2);
        Intent intent = getIntent();
        String url = intent.getStringExtra("itemUrl");
        String img = intent.getStringExtra("itemImg");
        String author = intent.getStringExtra("itemAuthor");
        initTollbar(author);
        //initWebView(url);
        initView(img);
        initContent(author, url);

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
    }

    private void initView(String imgUrl) {
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        tvContent = (TextView) findViewById(R.id.tv_detail);
        Picasso.with(this).load(imgUrl).fit().into(imgDetail);

    }



}
