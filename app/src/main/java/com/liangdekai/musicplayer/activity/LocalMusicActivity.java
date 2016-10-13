package com.liangdekai.musicplayer.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.adapter.ViewPagerAdapter;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.fragment.RadioFragment;
import com.liangdekai.musicplayer.fragment.RankFragment;
import com.liangdekai.musicplayer.fragment.SingleSongFragment;
import com.liangdekai.musicplayer.fragment.SongListFragment;
import com.liangdekai.musicplayer.service.PlayService;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class LocalMusicActivity extends BottomActivity {
    private List<Fragment> mFragment ;
    private SingleSongFragment mSingleFragment ;
    private SongListFragment mSongListFragment ;
    private RadioFragment mRadioFragment ;
    private RankFragment mRankFragment ;
    private ViewPagerAdapter mAdapter ;
    private String[] mTitle ;
    private ViewPager mViewPager ;
    private TabLayout mTabLayout ;
    private List<MusicInfo> mMusicList ;
    private ServiceConnection mConnection ;
    private boolean mIsPlaying ;
    private int mCurrentPosition ;

    public static void startActivity(Context context){
        Intent intent = new Intent(context , LocalMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loacl_music);
        init();
        //initView();
        setToolBar();
    }

//    private void initView(){
//        View view = mBottomFragment.getBottomView();
//        mSong = (TextView) view.findViewById(R.id.main_bottom_song);
//        mArtist = (TextView) view.findViewById(R.id.main_bottom_artist);
//        mPlay = (ImageView) view.findViewById(R.id.main_bottom_play);
//        mNext = (ImageView) view.findViewById(R.id.main_bottom_next);
//    }

    private void init(){
        mTitle = new String[]{"单曲" ,"歌手" ,"专辑" ,"文件夹"};
        mFragment = new ArrayList<>();
        mSingleFragment = SingleSongFragment.newInstance();
        mSongListFragment = SongListFragment.newInstance();
        mRadioFragment = RadioFragment.newInstance();
        mRankFragment = RankFragment.newInstance();
        mFragment.add(mSingleFragment);
        mFragment.add(mSongListFragment);
        mFragment.add(mRadioFragment);
        mFragment.add(mRankFragment);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragment , mTitle) ;
        mViewPager = (ViewPager) findViewById(R.id.fragment_discover_view_pager) ;
        mTabLayout = (TabLayout) findViewById(R.id.fragment_discover_tab) ;
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //mMusicList = MusicHelper.queryMusic(this);
        //bindService();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_local_bar);
        if (toolbar != null){
            toolbar.setNavigationIcon(R.mipmap.action_bar_back);
            toolbar.setTitle("本地音乐");
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void TestEventBus(MusicInfo musicInfo){
//        Log.d("test" , musicInfo.getMusicName()+"here");
////        mSong.setText(musicInfo.getMusicName());
////        mArtist.setText(musicInfo.getArtist());
////        mPlay.setImageResource(R.mipmap.main_bottom_pause);
////        Intent intent = new Intent(this , PlayService.class);
////        bindService(intent , mConnection , BIND_AUTO_CREATE);
//        mBottomFragment.bindService();
//        mBottomFragment.upDateInfo(musicInfo);
//    }

//    public void bindService(){
//        Intent intent = new Intent(this , PlayService.class);
//        bindService(intent , mConnection , BIND_AUTO_CREATE);
//        mConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                Log.d("test" , "服务已经绑定");
//                PlayService.MyBind binder = (PlayService.MyBind) service;
//                mIsPlaying = binder.isPlaying();
//                mCurrentPosition = binder.getPosition();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                Log.d("test" , "服务已经解除绑定");
//            }
//        };
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(mConnection);
        //mBottomFragment.unBindService();
    }
}
