package com.test.ksports.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;

import java.util.List;

/**
 * Created by kingwag on 2017/2/7.
 * 收藏适配器
 */

public class FavAdapter extends BaseMultiItemQuickAdapter<NewsBean.DataBean.ArticlesBean> {
    private Context context;//上下文
    private List<NewsBean.DataBean.ArticlesBean> datas;//数据源
    public FavAdapter(Context context, List<NewsBean.DataBean.ArticlesBean> data) {
        super(data);
        this.context = context;
        this.datas = data;
        addItemType(NewsBean.DataBean.ArticlesBean.FAV_IMG, R.layout.fav_item_news);
        addItemType(NewsBean.DataBean.ArticlesBean.FAV_TEXT, R.layout.fav_item_news2);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsBean.DataBean.ArticlesBean articlesBean) {
        switch (baseViewHolder.getItemViewType()) {
            case NewsBean.DataBean.ArticlesBean.FAV_TEXT:
                baseViewHolder.setText(R.id.tv_fav_tittle2, articlesBean.getTitle());
                break;
            case NewsBean.DataBean.ArticlesBean.FAV_IMG:
                baseViewHolder.setText(R.id.tv_fav_tittle, articlesBean.getTitle());
                if (!TextUtils.isEmpty(articlesBean.getThumbnail_pic())) {
                    Picasso.with(context).load(articlesBean.getThumbnail_pic()).into((ImageView) baseViewHolder.getView(R.id.iv_fav));
                }
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        NewsBean.DataBean.ArticlesBean itemBean = datas.get(position);
        if (!TextUtils.isEmpty(itemBean.getThumbnail_pic())){
            return NewsBean.DataBean.ArticlesBean.FAV_IMG;
        }else {
            return NewsBean.DataBean.ArticlesBean.FAV_TEXT;
        }

    }
}
