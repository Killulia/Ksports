package com.test.ksports.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.google.gson.Gson;
import com.test.ksports.R;
import com.test.ksports.activity.DetailActivity;
import com.test.ksports.activity.MainActivity;
import com.test.ksports.adapter.NewsAdapter;
import com.test.ksports.apiservice.ApiService;
import com.test.ksports.bean.NewsBean;
import com.test.ksports.constant.MyConstants;
import com.test.ksports.db.DBManager;
import com.test.ksports.util.AnimationUtil;
import com.test.ksports.util.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kingwag on 2017/2/7.
 * 新闻页面
 */

public class NewsOneFragment extends Fragment {
    private View view;
    private PtrFrameLayout ptrFrameLayout_main;//刷新布局
    private RecyclerView newsRecyclerView;//列表
    private NewsAdapter newsAdapter;//列表适配器
    private LinearLayoutManager layoutManager = null;//列表管理
    private List<NewsBean.DataBean.ArticlesBean> datas;//数据集合
    private int curPage = 1;//页面
    private Executor downloadExecutor;
    private DBManager dbManager;//数据库管理
    private int lastVisibleItem = 0;//最后一个可见项
    private boolean isUp;//上下滑动状态
    private int tabType;//标题类型
    public static String receiverCache = "";
    private Retrofit retrofit;//
    private ApiService apiService;//api接口
    private Call<ResponseBody> call;
    private MainActivity mainActivity;
    private TabWidget tabWidget;//底部导航
    public static final String KEY = "ARGUMENTS";


    public static NewsOneFragment newInstance(int type) {
        NewsOneFragment fragment = new NewsOneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tabType = getArguments().getInt(KEY);
        }
        initDatabase();
        initRetrofit();
        initData();
        mainActivity = (MainActivity) getActivity();
        tabWidget = mainActivity.getTabwidget();


    }

    @Override
    public void onResume() {
        super.onResume();
        newsAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        retrofit = new Retrofit.Builder()//创建Retrofit.Builder
                .baseUrl(MyConstants.BASE_URL)//绑定BaseUrl
                .client(OkHttpUtils.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();//创建Retrofit
        apiService = retrofit.create(ApiService.class);//创建接口对象
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //同时解决tab切换多次网络请求，绘制UI，及详情页面多次回退
        if (view == null) {
            view = inflater.inflate(R.layout.frag_news, container, false);
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
        //开启异步任务，网络下载数据
//        downloadExecutor = Executors.newFixedThreadPool(5);
        //new JsonTask(MyConstants.NEWS_URL1_1, downloadLisntner).executeOnExecutor(downloadExecutor);
        //loadData(dataUrl);
        if (tabType == 1) {
            loadData(apiService.getNews1_1Call());
        } else if (tabType == 2) {
            loadData(apiService.getNews2_1Call());
        } else if (tabType == 3) {
            loadData(apiService.getNews3_1Call());
        } else {
            loadData(apiService.getNews4_1Call());
        }


    }

    /**
     * 初始化视图
     *
     * @param view
     */
    private void initView(View view) {
        datas = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), datas);
        //设置列表动画
        newsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        newsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recy);

        ptrFrameLayout_main = (PtrFrameLayout) view.findViewById(R.id.frag);
        newsRecyclerView.setNestedScrollingEnabled(false);
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分隔线
        //newsRecyclerView.addItemDecoration(new CustomDecoration(getContext(), LinearLayoutManager.VERTICAL));


        //item点击事件，点击进入详情页面
        newsRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                NewsBean.DataBean.ArticlesBean articlesBean = datas.get(position);
                //加入到浏览记录数据库
                boolean result = dbManager.insert(articlesBean, 2);
                //若已经有浏览记录，删掉之前的，重新添加
                if (!result) {
                    dbManager.delete(articlesBean.getPk(), 2);
                    dbManager.insert(articlesBean, 2);
                }
                //将相关信息传递到详情页面
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("item", articlesBean);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, final int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("不感兴趣？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datas.remove(position);
                                newsAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });

        //利用RecyclerView的滚动监听实现上拉加载下一页
        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == newsAdapter.getItemCount() - 1) {
                    curPage++;
                    if (curPage == 2 && tabType == 1) {
                        //new JsonTask(MyConstants.NEWS_URL1_2, downloadLisntner).executeOnExecutor(downloadExecutor);
                        loadData(apiService.getNews1_2Call());
                    } else if (curPage == 2 && tabType == 2) {
                        loadData(apiService.getNews2_2Call());
                    } else if (curPage == 3 && tabType == 2) {
                        loadData(apiService.getNews2_3Call());
                    } else {
                        Toasty.info(getActivity(), "没有更多内容啦", Toast.LENGTH_SHORT, true).show();
                    }


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (dy < 0) {
                    onScrollDown(isUp);
                } else {
                    onScrollUp(isUp);
                }
            }
        });


        //使用PtrFrameLayout实现下拉刷新
        //效果1：设置默认的经典的头视图
        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getContext());
        //设置头视图
        ptrFrameLayout_main.setHeaderView(defaultHeader);
        // 绑定UI与刷新状态的监听
        ptrFrameLayout_main.addPtrUIHandler(defaultHeader);

        // 添加刷新动作监听
        ptrFrameLayout_main.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                curPage = 1;
                initData();
                // 刷新完成，让刷新Loading消失
                ptrFrameLayout_main.refreshComplete();
            }
        });

    }


    /**
     * 上滑动画
     *
     * @param direction
     */
    private void onScrollUp(boolean direction) {
        if (direction) {
            //设置隐藏动画
            tabWidget.setAnimation(AnimationUtil.moveToViewBottom());
            tabWidget.setVisibility(View.GONE);
            isUp = false;
        }
    }

    /**
     * 下滑动画
     *
     * @param direction
     */
    private void onScrollDown(boolean direction) {
        if (!direction) {
            //设置显示动画
            tabWidget.setAnimation(AnimationUtil.moveToViewLocation());
            tabWidget.setVisibility(View.VISIBLE);
            isUp = true;
        }

    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        dbManager = new DBManager(getContext());
    }

    /**
     * 加载数据
     *
     * @param loadCall
     */
    private void loadData(Call<NewsBean> loadCall) {
        loadCall.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                if (response != null) {
                    final NewsBean newsBean = response.body();
                    if (curPage == 1) {
                        datas.clear();
                    }
                    datas.addAll(newsBean.getData().getArticles());
                    newsAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {

            }
        });
    }


}
