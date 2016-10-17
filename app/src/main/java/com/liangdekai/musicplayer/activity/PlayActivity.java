package com.liangdekai.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.fragment.AlbumRoundFragment;
import com.liangdekai.musicplayer.util.CalculateTime;
import com.liangdekai.musicplayer.util.MusicCache;
import com.liangdekai.musicplayer.util.MusicHelper;
import com.liangdekai.musicplayer.util.QueryMusic;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2016/10/13.
 */
public class PlayActivity extends BottomActivity implements View.OnClickListener , SeekBar.OnSeekBarChangeListener,ViewPager.OnPageChangeListener{
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
    private SeekBar mSeekBar ;
    private ViewPager mViewPager ;
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
        setContentView(R.layout.activity_play);
        Log.d("test" ,MusicCache.getPosition()+"设置当前页面为" );
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
    }

    private void initInfo(){
        mTvEndTime.setText(CalculateTime.calculateFormatTime(QueryMusic.getDuration(MusicCache.getCacheMusic(MusicCache.getPosition()))));
        mTvStartTime.setText(CalculateTime.calculateFormatTime(QueryMusic.position()));
        mSeekBar.setMax(QueryMusic.getDuration(MusicCache.getCacheMusic(MusicCache.getPosition())));
        mSeekBar.setProgress(QueryMusic.position());
        mToolBar.setTitle(QueryMusic.getMusicName(MusicCache.getCacheMusic(MusicCache.getPosition())));
        mToolBar.setSubtitle(QueryMusic.getArtistName(MusicCache.getCacheMusic(MusicCache.getPosition())));
        if (QueryMusic.isPlaying()){
            mNeedleAnimator.start();
            mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_pause);
            mSeekBar.postDelayed(mUpdateSeekBar, 1000);
        }
        mMusicList = MusicHelper.queryMusic(this);//此处必须进行修改，目前仅供测试
    }

    private Runnable mUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
            mTvStartTime.setText(CalculateTime.calculateFormatTime(QueryMusic.position()));
            mSeekBar.setProgress(QueryMusic.position());
            if (QueryMusic.isPlaying()){
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
            mToolBar.setTitle(QueryMusic.getMusicName(MusicCache.getCacheMusic(MusicCache.getPosition())));
            mToolBar.setSubtitle(QueryMusic.getArtistName(MusicCache.getCacheMusic(MusicCache.getPosition())));
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
        mViewPager.setCurrentItem(MusicCache.getPosition());
        Log.d("test" ,MusicCache.getPosition()+"设置当前页面为" );
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.play_iv_play :
               if (QueryMusic.isPlaying()){
                   mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_play);
                   QueryMusic.pause();
                   mSeekBar.removeCallbacks(mUpdateSeekBar);
                   mNeedleAnimator.reverse();
               }else {
                   mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_pause);
                   QueryMusic.start();
                   mNeedleAnimator.start();
                   mSeekBar.postDelayed(mUpdateSeekBar , 1000);
               }
               EventBus.getDefault().post("null");
               break;
           case R.id.play_iv_next :
//               QueryMusic.next();
//               initInfo();
//               EventBus.getDefault().post("null");
               changeToNext();
               break;
           case R.id.play_iv_pre :
//               QueryMusic.pre();
//               initInfo();
//               EventBus.getDefault().post("null");
               changeToPre();
               break;
       }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            QueryMusic.seekTo(progress);
            mTvStartTime.setText(CalculateTime.calculateFormatTime(QueryMusic.position()));
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
//        if (position == mMusicList.size()+1){
//            mViewPager.setCurrentItem(1);
//            MusicCache.rememberPosition(0);
//            changeToNext();
//        }else if (position == 0){
//            mViewPager.setCurrentItem(mMusicList.size());
//            MusicCache.rememberPosition(mMusicList.size()-1);
//            changeToPre();
//        }else
        if (position > MusicCache.getPosition()){//向右边滑动
            changeToNext();
        }else if (position < MusicCache.getPosition()){
            changeToPre();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends PagerAdapter {

//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }

//        @Override
//        public Fragment getItem(int position) {
//            AlbumRoundFragment albumRoundFragment = AlbumRoundFragment.newInstance("http://img4.imgtn.bdimg.com/it/u=1736222420,1255659206&fm=21&gp=0.jpg");
//            return albumRoundFragment;
//        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(PlayActivity.this).inflate(R.layout.fragmnet_play_round , container , false);
            ImageView imageView = (ImageView) view.findViewById(R.id.fragment_play_album);
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
            return mMusicList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void changeToNext(){
        QueryMusic.next();
        initInfo();
        EventBus.getDefault().post("null");
        mViewPager.setCurrentItem(MusicCache.getPosition());
    }

    private void changeToPre(){
        QueryMusic.pre();
        initInfo();
        EventBus.getDefault().post("null");
        mViewPager.setCurrentItem(MusicCache.getPosition());
    }

//    private void loadAlbumImage(String url ,ImageView imageView){
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.mipmap.fragment_play_album_defautl)
//                .into(imageView);
//    }
}
