package com.test.ksports.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.test.ksports.R;
import com.test.ksports.adapter.BallAdapter;
import com.test.ksports.bean.AgendaBean;
import com.test.ksports.constant.UrlConstants;
import com.test.ksports.util.JsonTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kingwag on 2016/11/30.
 * 赛事页面
 */

public class BallFragment extends Fragment {
    private View view;
    private RecyclerView balRecycle;
    private BallAdapter ballAdapter;
    private RecyclerView.LayoutManager manager;
    private List<AgendaBean.ResultBean.ListBean.TrBean> datas;

    //创建一个线程池
    private Executor downloadExecutor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.frag_ball, container, false);
            initView(view);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        return view;
    }

    private void initData() {
        datas = new ArrayList<>();
    }

    private void initView(View rootView) {
        balRecycle = (RecyclerView) rootView.findViewById(R.id.ball_recy);
        downloadExecutor = Executors.newFixedThreadPool(5);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        new JsonTask(UrlConstants.BALL_URL, new JsonTask.OnDownloadLisntner() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                AgendaBean agendaBean = gson.fromJson(result, AgendaBean.class);
                datas.addAll(agendaBean.getResult().getList().get(1).getTr());
                ballAdapter = new BallAdapter(getActivity(), datas);
                balRecycle.setAdapter(ballAdapter);
                balRecycle.setLayoutManager(manager);
                ballAdapter.notifyDataSetChanged();
            }
        }).executeOnExecutor(downloadExecutor);

    }

}
