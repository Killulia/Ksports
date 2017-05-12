package com.test.ksports.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;
import java.util.List;

/**
 * Created by kingwag on 2017/5/11.
 */

public class CustomViewPageAdapter extends PagerAdapter {
    private List<NewsBean.DataBean.ArticlesBean> beans;
    private LayoutInflater inflater;
    private Context context;
    public CustomViewPageAdapter(Context context,List<NewsBean.DataBean.ArticlesBean> beans) {
        this.context = context;
        this.beans = beans;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return beans.size() > 0 ? beans.size() == 1 ? 1 : Integer.MAX_VALUE : 0;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = View.inflate(context,R.layout.news_page1,null);
        TextView textView = (TextView) itemView.findViewById(R.id.tv_page_tittle);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_page);
        textView.setText(beans.get(position%beans.size()).getTitle());
        Picasso.with(context).load(beans.get(position%beans.size()).getThumbnail_mpic()).into(imageView);
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }
}
