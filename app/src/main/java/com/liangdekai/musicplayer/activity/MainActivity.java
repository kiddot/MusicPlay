package com.liangdekai.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.adapter.FragmentAdapter;
import com.liangdekai.musicplayer.adapter.SlideMenuAdapter;
import com.liangdekai.musicplayer.bean.MenuItem;
import com.liangdekai.musicplayer.fragment.DiscoverFragment;
import com.liangdekai.musicplayer.fragment.MineFragment;
import com.liangdekai.musicplayer.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BottomActivity implements ViewPager.OnPageChangeListener , View.OnClickListener{
    private ActionBar mActionBar ;
    private ImageView mIvDiscover ;
    private ImageView mIvMusic ;
    private ImageView mIvFriend ;
    private ImageView mIvSearch ;
    private ViewPager mViewPager ;
    private List<Fragment> mFragment ;
    private List<MenuItem> mMenuItem ;
    private MainFragment mMainFragment ;
    private DiscoverFragment mDiscoverMusic ;
    private MineFragment mMineFragment;
    private FragmentAdapter mAdapter ;
    private RecyclerView mRecyclerView ;
    private SlideMenuAdapter mSlideAdapter ;
    private DrawerLayout mDrawerLayout;

    public static void startActivity(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        initView();
        setClickListener();
        initData();
        init();
    }

    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_draw_layout);
        mViewPager = (ViewPager) findViewById(R.id.main_vp_show) ;
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rv_menu) ;
        mIvDiscover = (ImageView) findViewById(R.id.main_iv_discover);
        mIvMusic = (ImageView) findViewById(R.id.main_iv_music);
        mIvFriend = (ImageView) findViewById(R.id.main_iv_friends);
        mIvSearch = (ImageView) findViewById(R.id.main_iv_search);
        mFragment = new ArrayList<>();
        mMenuItem = new ArrayList<>();
    }

    private void init(){
        mMainFragment = MainFragment.newInstance();
        mDiscoverMusic = DiscoverFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        mFragment.clear();
        mFragment.add(mDiscoverMusic);
        mFragment.add(mMainFragment);
        mFragment.add(mMineFragment);
        mAdapter = new FragmentAdapter(getSupportFragmentManager() , mFragment);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(this);
        mIvMusic.setSelected(true);
        mSlideAdapter = new SlideMenuAdapter(mMenuItem) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSlideAdapter);
    }

    private void initData(){
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "我的消息")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "积分商城")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "会员中心")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "在线听歌免流量")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "听歌识曲")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "主题换肤")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "夜间模式")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "定时停止播放")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "音乐闹钟")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "驾驶模式")) ;
        mMenuItem.add(new MenuItem(R.mipmap.menu_common , "我的音乐云盘")) ;
    }

    private void setClickListener(){
        mIvFriend.setOnClickListener(this);
        mIvDiscover.setOnClickListener(this);
        mIvMusic.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setNavigationIcon(R.mipmap.action_bar_menu);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTab(int position){
        switch (position){
            case 0 :
                mIvDiscover.setSelected(true);
                mIvMusic.setSelected(false);
                mIvFriend.setSelected(false);
                break;
            case 1 :
                mIvMusic.setSelected(true);
                mIvFriend.setSelected(false);
                mIvDiscover.setSelected(false);
                break;
            case 2 :
                mIvFriend.setSelected(true);
                mIvDiscover.setSelected(false);
                mIvMusic.setSelected(false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_iv_discover :
                mViewPager.setCurrentItem(0);
                break;
            case R.id.main_iv_music :
                mViewPager.setCurrentItem(1);
                break;
            case R.id.main_iv_friends :
                mViewPager.setCurrentItem(2);
                break;
            case R.id.main_iv_search :
                SearchActivity.startActivity(this);
                break;
        }
    }

    /**
     * 重写back按键，相当于home键
     */
    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(homeIntent);
    }
}
