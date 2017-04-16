package com.test.ksports.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;

import java.util.List;

/**
 * Created by kingwag on 2017/2/7.
 */

public class NewsAdapter extends BaseQuickAdapter <NewsBean.DataBean.ArticlesBean>{
    private Context context;
    private int layoutID;
    public NewsAdapter(Context context, List<NewsBean.DataBean.ArticlesBean> datas,int layoutID) {
        super(layoutID, datas);
        this.context = context;
        this.layoutID = layoutID;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsBean.DataBean.ArticlesBean articlesBean) {
        if (layoutID==R.layout.item_news){
            baseViewHolder.setText(R.id.news_tittle,articlesBean.getTitle())
                    .setText(R.id.news_auther,articlesBean.getAuther_name());
            if (!TextUtils.isEmpty(articlesBean.getThumbnail_pic())) {
                Picasso.with(context).load(articlesBean.getThumbnail_pic()).into((ImageView) baseViewHolder.getView(R.id.news_img));
            }
        }else{
            baseViewHolder.setText(R.id.tv_fav_tittle,articlesBean.getTitle());
            if (!TextUtils.isEmpty(articlesBean.getThumbnail_pic())) {
                Picasso.with(context).load(articlesBean.getThumbnail_pic()).into((ImageView) baseViewHolder.getView(R.id.iv_fav));
            }
        }

    }


}
