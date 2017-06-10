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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwag on 2017/4/18.
 * 看球页面
 */

public class LiveFragment extends Fragment {
    private List<Fragment> fragments;//Fragment集合
    private ViewPager pager;//容器
    private TabLayout tabLayout;//顶部导航
    private TabAdapter adapter;//导航适配器
    private List<String> strs;//导航标题集合
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.live_main, container, false);
        initData();
        initView(rootView);
        return rootView;
    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        pager = (ViewPager)view. findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        adapter = new TabAdapter(getChildFragmentManager(), fragments, strs);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragments = new ArrayList<>();
        LiveOneFragment basketFragment = new LiveOneFragment();
        LiveSecondFragment socerFragment = new LiveSecondFragment();
        fragments.add(basketFragment);
        fragments.add(socerFragment);
        strs = new ArrayList<>();
        strs.add("篮球");
        strs.add("足球");

    }
}
