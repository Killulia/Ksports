package com.test.ksports.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.util.ImageUtils;

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
    private WebView webView;
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
                finalContent = finalContent + "  "+Html.fromHtml("<p>" + phList.get(i) + "</p>");
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
                            if (content.size() == 0) {
                                content = doc.select("div.artical-main-content").select("div");
                            }
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
                            break;
                        case "体育疯":
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

    /**
     * 设置WebView
     *
     * @param itemUrl
     */
    private void initWebView(String itemUrl) {

        // pb = (ProgressBar) findViewById(R.id.pb);
        //初始化webview
        webView = (WebView) findViewById(R.id.web_text);
        //加载网页
        webView.loadUrl(itemUrl);
        //取消滚动条
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        //初始化缩放比尺寸
        webView.setInitialScale(100);
        //设置支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //设置WebView可触摸放大缩小
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //设置双击放大，缩小回原尺寸
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //pb.setVisibility(View.GONE);
            }
        });
    }

}
