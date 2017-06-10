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
import com.test.ksports.activity.DetailActivityWebView;
import com.test.ksports.adapter.BasketAdapter;
import com.test.ksports.bean.AgendaBean;
import com.test.ksports.constant.MyConstants;
import com.test.ksports.util.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by kingwag on 2016/11/30.
 * 赛事页面
 */

public class LiveOneFragment extends Fragment {
    private View view;
    private RecyclerView balRecycle;//列表
    private BasketAdapter ballAdapter;//列表适配器
    private RecyclerView.LayoutManager manager;//列表管理
    private List<AgendaBean.ResultBean.ListBean.TrBean> datas;//数据集合
    private PtrFrameLayout ptrFrameLayout_basket;//刷新布局
    //创建一个线程池
    private Executor downloadExecutor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        loadData(MyConstants.BALL_URL1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_ball, container, false);
            initView(view);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas = new ArrayList<>();
    }

    /**
     * 初始化视图
     *
     * @param rootView
     */
    private void initView(View rootView) {
        ptrFrameLayout_basket = (PtrFrameLayout) rootView.findViewById(R.id.frag);
        balRecycle = (RecyclerView) rootView.findViewById(R.id.ball_recy);
        downloadExecutor = Executors.newFixedThreadPool(5);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ballAdapter = new BasketAdapter(getActivity(), datas);
        balRecycle.setAdapter(ballAdapter);
        balRecycle.setLayoutManager(manager);
        //item点击事件，点击进入详情页面
        balRecycle.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                AgendaBean.ResultBean.ListBean.TrBean trBean = datas.get(position);
                String itemUrl = trBean.getLink2url();
                String itemImg = trBean.getPlayer1logobig();
                Intent intent = new Intent(getActivity(), DetailActivityWebView.class);
                intent.putExtra("itemUrl", itemUrl);
                intent.putExtra("itemImg", itemImg);
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

        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头视图
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getContext());
        //设置头视图
        ptrFrameLayout_basket.setHeaderView(defaultHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_basket.addPtrUIHandler(defaultHeader);

        // 添加刷新动作监听
        ptrFrameLayout_basket.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(MyConstants.BALL_URL1);
                // 刷新完成，让刷新Loading消失
                ptrFrameLayout_basket.refreshComplete();
            }
        });
    }

    /**
     * 网络加载数据
     *
     * @param url
     */
    private void loadData(String url) {
        OkHttpUtils.doAsyncGETRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String jsonString = body.string();
                    //json解析
                    Gson gson = new Gson();
                    final AgendaBean agendaBean = gson.fromJson(jsonString, AgendaBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            datas.clear();
                            datas.addAll(agendaBean.getResult().getList().get(1).getTr());
                            ballAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
    }

}
