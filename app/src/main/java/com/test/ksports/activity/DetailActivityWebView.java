package com.test.ksports.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.util.StatusbarUtil;

/**
 * 新闻详情页面
 */
public class DetailActivityWebView extends AppCompatActivity {
    private ImageView imgDetail;//详情图片
    private Toolbar toolbar;//导航栏
    private WebView webView;//网页加载组件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusbarUtil.setStatusBarColor(this,getResources().getColor(R.color.red));
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String url = intent.getStringExtra("itemUrl");
        String img = intent.getStringExtra("itemImg");
        initTollbar("数据");
        initWebView(url);
        initView(img);

    }


    /**
     * 初始化工具栏
     * @param tittleString
     */
    private void initTollbar(String tittleString) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(tittleString);
    }

    /**
     * 初始化视图
     * @param imgUrl
     */
    private void initView(String imgUrl) {
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        if (imgUrl != null) {
            Picasso.with(this).load(imgUrl).fit().into(imgDetail);
        }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //回收WebView资源
        webView.stopLoading();
        ((ViewGroup)webView.getParent()).removeView(webView);
        webView.removeAllViews();
        webView.clearCache(true);
        webView.clearHistory();
        webView.destroy();

    }
}
