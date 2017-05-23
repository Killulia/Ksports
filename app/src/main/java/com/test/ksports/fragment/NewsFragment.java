package com.test.ksports.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.ksports.R;
import com.test.ksports.adapter.TabAdapter;
import com.test.ksports.constant.UrlConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/4/18.
 */

public class NewsFragment extends Fragment {
    private List<Fragment> fragments;
    private ViewPager pager;
    private TabLayout tabLayout;
    private TabAdapter adapter;
    private List<String> strs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.live_main, container, false);
        initData();
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        pager = (ViewPager)view. findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        adapter = new TabAdapter(getChildFragmentManager(), fragments, strs);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private void initData() {
        fragments = new ArrayList<>();
        NewsOneFragment fragment1 = new NewsOneFragment(UrlConstants.NEWS_URL1_1,1);
        NewsOneFragment fragment2 = new NewsOneFragment(UrlConstants.NEWS_URL2_1,2);
        NewsOneFragment fragment3 = new NewsOneFragment(UrlConstants.NEWS_URL3_1,3);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        strs = new ArrayList<>();
        strs.add("热门");
        strs.add("懒熊体育");
        strs.add("懂个球");

    }
}
