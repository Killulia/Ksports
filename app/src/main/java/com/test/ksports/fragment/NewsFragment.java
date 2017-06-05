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
import com.test.ksports.apiservice.ApiService;
import com.test.ksports.constant.MyConstants;

import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
/**
 * Created by kingwag on 2017/4/18.
 */

public class NewsFragment extends Fragment {
    private List<Fragment> fragments;
    private ViewPager pager;
    private TabLayout tabLayout;
    private TabAdapter adapter;
    private List<String> strs;
    private Retrofit retrofit;
    private ApiService apiService;
    private Call<ResponseBody> call;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.live_main, container, false);
        initRetrofit();
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

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()//创建Retrofit.Builder
                .baseUrl(MyConstants.BASE_URL)//绑定BaseUrl
                .build();//创建Retrofit
        apiService = retrofit.create(ApiService.class);//创建接口对象
    }

    private void initData() {
        fragments = new ArrayList<>();
        NewsOneFragment fragment1 = new NewsOneFragment(1);
        NewsOneFragment fragment2 = new NewsOneFragment(2);
        NewsOneFragment fragment3 = new NewsOneFragment(3);
        NewsOneFragment fragment4 = new NewsOneFragment(4);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        strs = new ArrayList<>();
        strs.add("热门");
        strs.add("杂谈");
        strs.add("装备");
        strs.add("健身");

    }
}
