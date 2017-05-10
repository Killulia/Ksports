package com.test.ksports.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.test.ksports.R;
import com.test.ksports.bean.NewsBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by kingwag on 2017/4/26.
 */

public class HistoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<NewsBean.DataBean.ArticlesBean> beans;
    private Context mContext;
    public HistoryAdapter(Context context,List<NewsBean.DataBean.ArticlesBean> beans) {
        this.beans = beans;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        NewsBean.DataBean.ArticlesBean itemBean = beans.get(position);
        if (!TextUtils.isEmpty(itemBean.getThumbnail_pic())){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position)== 0){
            ViewHolderImg viewHolderImg = null;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_news, parent, false);
                viewHolderImg = new ViewHolderImg(convertView);
                convertView.setTag(viewHolderImg);
            }else {
                viewHolderImg = (ViewHolderImg) convertView.getTag();
            }
            viewHolderImg.tittle.setText(beans.get(position).getTitle());
            viewHolderImg.author.setText(beans.get(position).getAuther_name());
            Picasso.with(mContext).load(beans.get(position).getThumbnail_pic()).into(viewHolderImg.img);
        }else {
            ViewHolderText viewHolderText = null;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_news2, parent, false);
                viewHolderText = new ViewHolderText(convertView);
                convertView.setTag(viewHolderText);
            }else {
                viewHolderText = (ViewHolderText) convertView.getTag();
            }
            viewHolderText.tittle.setText(beans.get(position).getTitle());
            viewHolderText.author.setText(beans.get(position).getAuther_name());
        }
        return convertView;
    }

    static class ViewHolderImg{
        private TextView tittle;
        private TextView author;
        private ImageView img;

        public ViewHolderImg(View rootView) {
            tittle = (TextView) rootView.findViewById(R.id.news_tittle);
            author = (TextView) rootView.findViewById(R.id.news_auther);
            img = (ImageView) rootView.findViewById(R.id.news_img);
        }
    }

    static class ViewHolderText{
        private TextView tittle;
        private TextView author;


        public ViewHolderText(View rootView) {
            tittle = (TextView) rootView.findViewById(R.id.news_tittle2);
            author = (TextView) rootView.findViewById(R.id.news_auther2);
        }
    }
}
