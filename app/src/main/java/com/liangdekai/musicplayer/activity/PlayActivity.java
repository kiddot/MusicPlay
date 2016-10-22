package com.liangdekai.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.net.MusicApi;
import com.liangdekai.musicplayer.net.VolleyHelper;
import com.liangdekai.musicplayer.net.util.ParseResponse;
import com.liangdekai.musicplayer.util.OperateMusic;
import com.liangdekai.musicplayer.util.CalculateTime;
import com.liangdekai.musicplayer.util.PlayListCache;
import com.liangdekai.musicplayer.util.MusicHelper;
import com.liangdekai.musicplayer.view.LrcView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2016/10/13.
 */
public class PlayActivity extends BottomActivity implements View.OnClickListener , SeekBar.OnSeekBarChangeListener,ViewPager.OnPageChangeListener {
    private static final int ANIMATOR_SPEED = 500 ;
    private static final int ANIMATOR_ROTATION = -30 ;
    private static final int ANIMATOR_REPEAT_COUNT = 0 ;
    private Toolbar mToolBar ;
    private ImageView mIvPlayOrPause ;
    private ImageView mIvPrevious ;
    private ImageView mIvNext ;
    private ImageView mIvNeedle ;
    private TextView mTvStartTime ;
    private TextView mTvEndTime ;
    private LrcView mLvLrc ;
    private SeekBar mSeekBar ;
    private ViewPager mViewPager ;
    private FrameLayout mAlbumLayout;
    private RelativeLayout mLrcLayout ;
    private LinearLayout mBottomBar ;
    private List<MusicInfo> mMusicList ;
    private ObjectAnimator mNeedleAnimator ;
    private ObjectAnimator mAlbumAnimator ;

    public static void startActivity(Context context){
        Intent intent = new Intent(context , PlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);//android:layout_marginTop="?attr/actionBarSize"
        setToolBar();
        initView();
        setOnListener();
        initAnimator();
        initInfo();
        initViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSeekBar.removeCallbacks(mUpdateSeekBar);
    }

    private void initView(){
        mIvPlayOrPause = (ImageView) findViewById(R.id.play_iv_play);
        mIvPrevious = (ImageView) findViewById(R.id.play_iv_pre);
        mIvNext = (ImageView) findViewById(R.id.play_iv_next);
        mSeekBar = (SeekBar) findViewById(R.id.play_sb_progress);
        mIvNeedle = (ImageView) findViewById(R.id.play_iv_needle);
        mTvEndTime = (TextView) findViewById(R.id.play_tv_end);
        mTvStartTime = (TextView) findViewById(R.id.play_tv_start);
        mViewPager = (ViewPager) findViewById(R.id.play_vp_slide);
        mAlbumLayout = (FrameLayout) findViewById(R.id.play_fl_album);
        mLrcLayout = (RelativeLayout) findViewById(R.id.play_rl_lrc);
        mBottomBar = (LinearLayout) findViewById(R.id.play_ll_bottom_tool);
        mLvLrc = (LrcView) findViewById(R.id.play_lv_lrc);
    }

    private void initInfo(){
        mTvEndTime.setText(CalculateTime.calculateFormatTime(OperateMusic.getDuration(PlayListCache.getCacheMusic(PlayListCache.getPosition()))));
        mTvStartTime.setText(CalculateTime.calculateFormatTime(OperateMusic.position()));
        mSeekBar.setMax(OperateMusic.getDuration(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
        mSeekBar.setProgress(OperateMusic.position());
        mToolBar.setTitle(OperateMusic.getMusicName(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
        mToolBar.setSubtitle(OperateMusic.getArtistName(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
        if (OperateMusic.isPlaying()){
            mNeedleAnimator.start();
            mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_pause);
            mSeekBar.postDelayed(mUpdateSeekBar, 1000);
        }
        mMusicList = MusicHelper.queryMusic(this);//此处必须进行修改，目前仅供测试
    }

    private Runnable mUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
            mTvStartTime.setText(CalculateTime.calculateFormatTime(OperateMusic.position()));
            mSeekBar.setProgress(OperateMusic.position());
            if (mLvLrc != null){
                mLvLrc.scrollToCurrentTimeMillis(OperateMusic.position());
            }
            if (OperateMusic.isPlaying()){
                mSeekBar.postDelayed(mUpdateSeekBar, 1000);
            }
        }
    };

    private void setOnListener(){
        mIvPlayOrPause.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mIvNext.setOnClickListener(this);
        mIvPrevious.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
        mLrcLayout.setOnClickListener(this);
    }

    private void initAnimator(){
        mNeedleAnimator = ObjectAnimator.ofFloat(mIvNeedle , "rotation" , ANIMATOR_ROTATION , 0);
        mNeedleAnimator.setDuration(ANIMATOR_SPEED);
        mNeedleAnimator.setRepeatCount(ANIMATOR_REPEAT_COUNT);
        mNeedleAnimator.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void showQuickControl(boolean show) {

    }

    private void setToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.play_tb_bar);
        if (mToolBar != null){
            mToolBar.setNavigationIcon(R.mipmap.action_bar_back);
            mToolBar.setTitle(OperateMusic.getMusicName(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
            mToolBar.setSubtitle(OperateMusic.getArtistName(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
            setSupportActionBar(mToolBar);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void initViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(PlayListCache.getPosition()+1);
        Log.d("test" , PlayListCache.getPosition()+"设置当前页面为" );
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.play_iv_play :
               if (OperateMusic.isPlaying()){
                   mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_play);
                   OperateMusic.pause();
                   mSeekBar.removeCallbacks(mUpdateSeekBar);
                   mNeedleAnimator.reverse();
               }else {
                   mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_pause);
                   OperateMusic.start();
                   mNeedleAnimator.start();
                   mSeekBar.postDelayed(mUpdateSeekBar , 1000);
               }
               EventBus.getDefault().post("null");
               break;
           case R.id.play_iv_next :
               changeToNext();
               break;
           case R.id.play_iv_pre :
               changeToPre();
               break;
           case R.id.play_rl_lrc :
               Log.d("test" , "我点击了歌词");
               if (mLrcLayout.getVisibility() == View.VISIBLE) {
                   mAlbumLayout.setVisibility(View.VISIBLE);
                   mLrcLayout.setVisibility(View.INVISIBLE);
                   mBottomBar.setVisibility(View.VISIBLE);
               }
               break;
       }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            OperateMusic.seekTo(progress);
            mTvStartTime.setText(CalculateTime.calculateFormatTime(OperateMusic.position()));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            OperateMusic.pre();
            initInfo();
            EventBus.getDefault().post("null");
            mViewPager.setCurrentItem(mMusicList.size());
        }else if (position == mMusicList.size() +1){
            Log.d("test" , "尽头也为");
            OperateMusic.next();
            initInfo();
            EventBus.getDefault().post("null");
            mViewPager.setCurrentItem(1);
        }else if (position > PlayListCache.getPosition()+1){//向右边滑动
            changeToNext();
        }else if (position < PlayListCache.getPosition()+1){
            changeToPre();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener ,VolleyHelper.VolleyListener{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(PlayActivity.this).inflate(R.layout.fragmnet_play_round , container , false);
            ImageView imageView = (ImageView) view.findViewById(R.id.fragment_play_album);
            imageView.setOnClickListener(this);
            imageView.setImageResource(R.mipmap.fragment_play_album_defautl);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return mMusicList.size()+2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_play_album:
                    tryToLoadLrc();
                    break;
            }
        }

        private void tryToLoadLrc(){
            if (mAlbumLayout.getVisibility() == View.VISIBLE) {
                mAlbumLayout.setVisibility(View.INVISIBLE);
                mLrcLayout.setVisibility(View.VISIBLE);
                mBottomBar.setVisibility(View.INVISIBLE);
                int position = PlayListCache.getPosition() ;
                MusicInfo musicInfo = PlayListCache.getCacheMusic(position);
                String musicName = OperateMusic.getMusicName(musicInfo) ;
                String artist = OperateMusic.getArtistName(musicInfo) ;
                String lrcLink = MusicApi.Search.searchLrcPic( musicName, artist);
                Log.d("test" , "这是歌词连接json数据"+lrcLink);
                if (!mLvLrc.searchLrc(null , musicName)){
                    VolleyHelper.sendByVolley(lrcLink , this);
                }
            }
        }

        @Override
        public void onSucceed(String jsonString) {
            Log.d("test" , jsonString);
            String link = ParseResponse.parseLrcResponse(jsonString);
            Log.d("test" , "这是歌词下载连接"+link);
            mLvLrc.searchLrc(link , OperateMusic.getArtistName(PlayListCache.getCacheMusic(PlayListCache.getPosition())));
        }

        @Override
        public void onFailed() {
            Log.d("test" , "网络请求失败");
        }
    }

    private void changeToNext(){
        OperateMusic.next();
        initInfo();
        EventBus.getDefault().post("null");
        mViewPager.setCurrentItem(PlayListCache.getPosition()+1);
    }

    private void changeToPre(){
        OperateMusic.pre();
        initInfo();
        EventBus.getDefault().post("null");
        mViewPager.setCurrentItem(PlayListCache.getPosition()+1);
    }
}
