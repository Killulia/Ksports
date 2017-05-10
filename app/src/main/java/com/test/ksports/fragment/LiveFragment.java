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
import com.test.ksports.adapter.LiveAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/4/18.
 */

public class LiveFragment extends Fragment {
    private List<Fragment> fragments;
    private ViewPager pager;
    private TabLayout tabLayout;
    private LiveAdapter adapter;
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
        adapter = new LiveAdapter(getChildFragmentManager(), fragments, strs);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private void initData() {
        fragments = new ArrayList<>();
        BasketFragment basketFragment = new BasketFragment();
        SocerFragment socerFragment = new SocerFragment();
        fragments.add(basketFragment);
        fragments.add(socerFragment);

        strs = new ArrayList<>();
        strs.add("篮球");
        strs.add("足球");

    }
}