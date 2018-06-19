package com.test.ksports.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
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

import es.dmoral.toasty.Toasty;

/**
 * 新闻详情页面
 */
public class DetailActivity extends BaseActivity implements ShineButton.OnCheckedChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener{
    private ImageView imgDetail;//详情图片
    private Toolbar toolbar;//导航栏
    private TextView tvContent;//页面内容
    private List<String> pList = new ArrayList<>();//爬虫的文字集合
    private ShineButton mSave, mPraise;//收藏与点赞按钮
    private DBManager manager;//数据库管理
    private Context mContext;//上下文
    private NewsBean.DataBean.ArticlesBean bean;//新闻实体类对象
    private int position;//新闻的位置
    private ImageButton sahreButton;//分享按钮
    private ImageButton textBar;//字体调节按钮
    private SeekBar seekBar;//进度条
    private View rlView, barView;//底部栏
    private StringBuilder builder;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String> phList = new ArrayList<>();
            phList = (List<String>) msg.obj;
            //对每一个元素进行格式化，并最终拼接成一个格式化好的字符串设置给TextView
            for (int i = 0; i < phList.size(); i++) {
                builder.append("  ").append(Html.fromHtml("<p>" + phList.get(i) + "</p>"));
            }
            tvContent.setText(builder.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化承载文章内容的StringBuilder
        builder = new StringBuilder("");
        //设置状态栏颜色
        StatusbarUtil.setStatusBarColor(this, getResources().getColor(R.color.red));
        setContentView(R.layout.activity_detail2);
        mContext = this;
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        bean = (NewsBean.DataBean.ArticlesBean) intent.getSerializableExtra("item");
        initData();
        initTollbar(bean.getAuther_name());
        initView(bean.getThumbnail_pic(), bean.getWeburl());
        initShineButton();
        initSeekBar();
        initContent(bean.getAuther_name(), bean.getWeburl());

    }

    /**
     * 初始化进度条，默认字体大小的进度
     */
    private void initSeekBar() {
        seekBar.setProgress(60);
    }

    /**
     * 控制按钮选中状态
     */
    private void initShineButton() {
        boolean isSaveCheck = SwitchPreferences.getState(mContext, bean.getWeburl() + "save");
        boolean isPraiseCheck = SwitchPreferences.getState(mContext, bean.getWeburl() + "praise");
        mSave.setChecked(isSaveCheck);
        mPraise.setChecked(isPraiseCheck);
    }

    /**
     * 初始化数据库
     */
    private void initData() {
        manager = new DBManager(mContext);
    }

    /**
     * 文本解析
     * @param author
     * @param url
     */
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
                            getHtmlMessage(doc, "div.artical-main-content");
                            break;
                        case "腾讯体育":
                            getHtmlMessage(doc, "div.tpl_main");
                            break;
                        case "懒熊体育":
                            getHtmlMessage(doc, "div[class=top or  imagecontent]");
                            break;
                        case "懂个球":
                            getHtmlMessage(doc, "div[class=rich_media_content]");
                            break;
                        case "张佳玮的博客":
                            getHtmlMessage(doc, "div[class=brief]");
                            break;
                        case "全球健身指南":
                            getHtmlMessage(doc, "div[class=rich_media_content]");
                            break;
                        case "私家鞋柜官方号":
                            getHtmlMessage(doc, "div[class=rich_media_content]");
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    /**
     *  获取每个<p>标签并添加到字符串集合
     */
    private void getHtmlMessage(Document doc, String div) {
        Elements content = doc.select(div).select("P");
        for (Element element : content) {
            if (!TextUtils.isEmpty(element.text())) {
                if ("-END-".equals(element.text())) {
                    break;
                }
                String newContent = element.text();
                pList.add(newContent);
            }

        }
        Message msg = new Message();
        msg.obj = pList;
        handler.sendMessage(msg);
    }

    /**
     * 初始化工具栏
     * @param tittleString
     */
    private void initTollbar(String tittleString) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(tittleString);
        setSupportActionBar(toolbar);
        //初始化 ActionBar
        ActionBar actionBar = getSupportActionBar();
        //添加返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化视图
     * @param imgUrl
     * @param url
     */
    private void initView(String imgUrl, final String url) {
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        rlView = findViewById(R.id.rl_bar);
        barView = findViewById(R.id.bar_include);
        textBar = (ImageButton) findViewById(R.id.detail_text);
        sahreButton = (ImageButton) findViewById(R.id.detail_share);
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        tvContent = (TextView) findViewById(R.id.tv_detail);
        Picasso.with(this).load(imgUrl).fit().into(imgDetail);
        mSave = (ShineButton) findViewById(R.id.detail_save);
        mPraise = (ShineButton) findViewById(R.id.detail_praise);
        mSave.setOnCheckStateChangeListener(this);
        mPraise.setOnCheckStateChangeListener(this);
        sahreButton.setOnClickListener(this);
        textBar.setOnClickListener(this);
        tvContent.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
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
        switch (view.getId()) {
            case R.id.detail_save:
                if (checked) {
                    boolean result = manager.insert(bean, 1);
                    if (!result) {
                        Toasty.warning(mContext, "已经收藏过", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.success(mContext, "收藏成功", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    manager.delete(bean.getPk(), 1);
                    Toasty.normal(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("backPosition", position);
                    setResult(200, intent);
                }
                SwitchPreferences.putState(mContext, bean.getWeburl() + "save", checked);
                break;

            case R.id.detail_praise:
                if (checked) {
                    Toasty.success(mContext, "点赞成功", Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.normal(mContext, "取消点赞", Toast.LENGTH_SHORT).show();
                }
                SwitchPreferences.putState(mContext, bean.getWeburl() + "praise", checked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, bean.getWeburl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享"));
                break;
            case R.id.detail_text:
                rlView.setVisibility(View.GONE);
                barView.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_detail:
                if (barView.getVisibility() == View.VISIBLE) {
                    barView.setVisibility(View.GONE);
                    rlView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress == 0) {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
        } else if (progress > 0 && progress <= 30) {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
        } else if (progress > 30 && progress <= 60) {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
        } else if (progress > 60 && progress < 90) {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f);
        } else {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
