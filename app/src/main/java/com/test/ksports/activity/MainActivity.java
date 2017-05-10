package com.test.ksports.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.test.ksports.R;
import com.test.ksports.fragment.LiveFragment;
import com.test.ksports.fragment.NewsFragment;
import com.test.ksports.fragment.SymFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTabHost tabHost;
    private TabWidget tabwidget;
    private final String[] TAGS = {"1", "2", "3"};
    //private DrawerLayout drawer;
    //private NavigationView navigationView;
    private Context mContext;
    private ViewPager mPager;
    private List<Fragment> fragmens;

    public TabWidget getTabwidget() {
        return tabwidget;
    }

    public void setTabwidget(TabWidget tabwidget) {
        this.tabwidget = tabwidget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initFragments();
        initTab();
        //initNavigation();
    }

    private void initFragments() {
        fragmens = new ArrayList<>();
        LiveFragment fragment1 = new LiveFragment();
        NewsFragment fragment2 = new NewsFragment();
        SymFragment fragment3 = new SymFragment();
        fragmens.add(fragment1);
        fragmens.add(fragment2);
        fragmens.add(fragment3);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers();
//        }
    }
/*
    private void initNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favorite:
                        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                        if (drawer.isDrawerOpen(GravityCompat.START)){
                            drawer.closeDrawers();
                        }
                        break;
                    case R.id.history:
                        Intent intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                        startActivity(intent1);
                        if (drawer.isDrawerOpen(GravityCompat.START)){
                            drawer.closeDrawers();
                        }
                        break;
                    case R.id.faq:
                        Intent intent2 = new Intent(MainActivity.this, SugesstionActivity.class);
                        startActivity(intent2);
                        if (drawer.isDrawerOpen(GravityCompat.START)){
                            drawer.closeDrawers();
                        }
                        break;
                }
                return true;
            }
        });
    }*/

    private void initTab() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabwidget = (TabWidget) findViewById(android.R.id.tabs);
        setTabwidget(tabwidget);
        manager = getSupportFragmentManager();
        // 绑定TabHost和tabContent，建立关联
        tabHost.setup(this, manager,android.R.id.tabcontent);
        tabHost.addTab(buildSpec(R.drawable.state_ball,getString(R.string.tab_live),TAGS[0]), LiveFragment.class,null);
        tabHost.addTab(buildSpec(R.drawable.state_news,getString(R.string.tab_news),TAGS[1]), NewsFragment.class,null);
        tabHost.addTab(buildSpec(R.drawable.state_data,getString(R.string.tab_mine),TAGS[2]), SymFragment.class,null);
        tabHost.setCurrentTabByTag("1");
        tabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);

    }

    public TabHost.TabSpec buildSpec(int imgId, String txtId, String tag){
        // 把布局解析成View
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView img = (ImageView) view.findViewById(R.id.tab_iv);
        TextView txt = (TextView) view.findViewById(R.id.tab_tv);
        // 从外部传递进来的图片Id和文字id设置上
        img.setImageResource(imgId);
        txt.setText(txtId);
        // 生成TabSpec(需要为当前Fragment绑定Tag标签，另外需要添加一个View)
        return tabHost.newTabSpec(tag).setIndicator(view);
    }


}
