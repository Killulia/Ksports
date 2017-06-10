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
import com.test.ksports.adapter.SocerAdapter;
import com.test.ksports.bean.SocerBean;
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

public class LiveSecondFragment extends Fragment {
    private View view;
    private RecyclerView balRecycle;
    private SocerAdapter socerAdapter;
    private RecyclerView.LayoutManager manager;
    private List<SocerBean.ResultBean.ViewsBean.Saicheng1Bean> datas;
    private PtrFrameLayout ptrFrameLayout_socer;
    //创建一个线程池
    private Executor downloadExecutor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        loadData(MyConstants.BALL_URL2);
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
        socerAdapter = new SocerAdapter(getContext(),datas);
        balRecycle.setAdapter(socerAdapter);
        balRecycle.setLayoutManager(manager);
//        new JsonTask(MyConstants.BALL_URL2, new JsonTask.OnDownloadLisntner() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                SocerBean socerBean = gson.fromJson(result, SocerBean.class);
//                datas.addAll(socerBean.getResult().getViews().getSaicheng1());
//                socerAdapter.notifyDataSetChanged();
//            }
//        }).executeOnExecutor(downloadExecutor);
        //item点击事件，点击进入详情页面
        balRecycle.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                SocerBean.ResultBean.ViewsBean.Saicheng1Bean saicheng1Bean = datas.get(position);
                String itemUrl = saicheng1Bean.getC51Link();
                Intent intent = new Intent(getActivity(), DetailActivityWebView.class);
                intent.putExtra("itemUrl", itemUrl);
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

        ptrFrameLayout_socer = (PtrFrameLayout) rootView.findViewById(R.id.frag2);
        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头视图
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getContext());
        //设置头视图
        ptrFrameLayout_socer.setHeaderView(defaultHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_socer.addPtrUIHandler(defaultHeader);

        // 添加刷新动作监听
        ptrFrameLayout_socer.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(MyConstants.BALL_URL2);
                // 刷新完成，让刷新Loading消失
                ptrFrameLayout_socer.refreshComplete();
            }
        });
    }

    /**
     * 网络加载数据
     * @param url
     */
    private void loadData(String url){
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
                    final SocerBean socerBean = gson.fromJson(jsonString, SocerBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            datas.clear();
                            datas.addAll(socerBean.getResult().getViews().getSaicheng1());
                            socerAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });


    }

}
