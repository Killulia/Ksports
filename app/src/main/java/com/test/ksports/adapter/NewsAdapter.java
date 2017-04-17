package com.test.ksports.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by kingwag on 2017/2/7.
 */

public class NewsAdapter extends BaseMultiItemQuickAdapter<NewsBean.DataBean.ArticlesBean> {
    private Context context;
    private String imgUrl;
    private String msgValue;
    private List<NewsBean.DataBean.ArticlesBean> datas;
    public NewsAdapter(Context context,List<NewsBean.DataBean.ArticlesBean> data) {
        super(data);
        this.context = context;
        this.datas = data;
        addItemType(NewsBean.DataBean.ArticlesBean.TEXT, R.layout.item_news2);
        addItemType(NewsBean.DataBean.ArticlesBean.IMG, R.layout.item_news);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsBean.DataBean.ArticlesBean articlesBean) {
        switch (baseViewHolder.getItemViewType()) {
            case NewsBean.DataBean.ArticlesBean.TEXT:
                baseViewHolder.setText(R.id.news_tittle2, articlesBean.getTitle())
                        .setText(R.id.news_auther2, articlesBean.getAuther_name());
                break;
            case NewsBean.DataBean.ArticlesBean.IMG:
                baseViewHolder.setText(R.id.news_tittle, articlesBean.getTitle())
                        .setText(R.id.news_auther, articlesBean.getAuther_name());
                if (!TextUtils.isEmpty(articlesBean.getThumbnail_pic())) {
                    Picasso.with(context).load(articlesBean.getThumbnail_pic()).into((ImageView) baseViewHolder.getView(R.id.news_img));

                }
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        NewsBean.DataBean.ArticlesBean itemBean = datas.get(position);
        if (!TextUtils.isEmpty(itemBean.getThumbnail_pic())){
            return NewsBean.DataBean.ArticlesBean.IMG;
        }else {
            return NewsBean.DataBean.ArticlesBean.TEXT;
        }

    }
}
