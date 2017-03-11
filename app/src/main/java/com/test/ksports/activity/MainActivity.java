package com.test.ksports.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.test.ksports.R;
import com.test.ksports.fragment.BallFragment;
import com.test.ksports.fragment.NewsFragment;
import com.test.ksports.fragment.SymFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTabHost tabHost;
    private TabWidget tabwidget;
    private final String[] TAGS = {"1", "2", "3"};
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_main);
        mContext = this;
        initTab();
        initNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }
    }

    private void initNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.favorite){
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void initTab() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabwidget = (TabWidget) findViewById(android.R.id.tabs);
        manager = getSupportFragmentManager();
        // 绑定TabHost和tabContent，建立关联
        tabHost.setup(this, manager,android.R.id.tabcontent);
        tabHost.addTab(buildSpec(R.drawable.state_ball,"看球",TAGS[0]), BallFragment.class,null);
        tabHost.addTab(buildSpec(R.drawable.state_news,"新闻",TAGS[1]), NewsFragment.class,null);
        tabHost.addTab(buildSpec(R.drawable.state_data,"综合",TAGS[2]), SymFragment.class,null);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
