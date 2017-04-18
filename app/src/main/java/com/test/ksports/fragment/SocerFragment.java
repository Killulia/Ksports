package com.test.ksports.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.google.gson.Gson;
import com.test.ksports.R;
import com.test.ksports.activity.DetailActivity;
import com.test.ksports.adapter.SocerAdapter;
import com.test.ksports.bean.AgendaBean;
import com.test.ksports.bean.SocerBean;
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

public class SocerFragment extends Fragment {
    private View view;
    private RecyclerView balRecycle;
    private SocerAdapter socerAdapter;
    private RecyclerView.LayoutManager manager;
    private List<SocerBean.ResultBean.ViewsBean.Saicheng1Bean> datas;

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
            view = inflater.inflate(R.layout.frag_ball2, container, false);
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
        balRecycle = (RecyclerView) rootView.findViewById(R.id.ball_recy2);
        downloadExecutor = Executors.newFixedThreadPool(5);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        new JsonTask(UrlConstants.BALL_URL2, new JsonTask.OnDownloadLisntner() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                SocerBean socerBean = gson.fromJson(result, SocerBean.class);
                datas.addAll(socerBean.getResult().getViews().getSaicheng1());
                socerAdapter = new SocerAdapter(getContext(),datas);
                balRecycle.setAdapter(socerAdapter);
                balRecycle.setLayoutManager(manager);
                socerAdapter.notifyDataSetChanged();
            }
        }).executeOnExecutor(downloadExecutor);
        //item点击事件，点击进入详情页面
        balRecycle.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                SocerBean.ResultBean.ViewsBean.Saicheng1Bean saicheng1Bean = datas.get(position);
//                String itemUrl = saicheng1Bean.getLink2url();
//                String itemImg = saicheng1Bean.getPlayer1logobig();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                intent.putExtra("itemUrl", itemUrl);
//                intent.putExtra("itemImg", itemImg);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });
    }

}
