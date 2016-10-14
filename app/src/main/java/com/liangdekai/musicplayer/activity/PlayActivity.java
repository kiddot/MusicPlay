package com.liangdekai.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.util.CalculateTime;
import com.liangdekai.musicplayer.util.QueryMusic;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2016/10/13.
 */
public class PlayActivity extends BottomActivity implements View.OnClickListener , SeekBar.OnSeekBarChangeListener{
    private static final int ANIMATOR_SPEED = 200 ;
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
        setToolBar();
        initView();
        setOnListener();
        initAnimator();
        initInfo();
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
    }

    private void initInfo(){
        mTvEndTime.setText(CalculateTime.calculateFormatTime(QueryMusic.getDuration()));
        mTvStartTime.setText(CalculateTime.calculateFormatTime(QueryMusic.position()));
        mSeekBar.setMax(QueryMusic.getDuration());
        mSeekBar.setProgress(QueryMusic.position());
        mToolBar.setTitle(QueryMusic.getMusicName());
        mToolBar.setSubtitle(QueryMusic.getArtistName());
        if (QueryMusic.isPlaying()){
            mNeedleAnimator.start();
            mIvPlayOrPause.setImageResource(R.mipmap.play_bottom_pause);
            mSeekBar.postDelayed(mUpdateSeekBar, 1000);
        }
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
            mToolBar.setTitle(QueryMusic.getMusicName());
            mToolBar.setSubtitle(QueryMusic.getArtistName());
            setSupportActionBar(mToolBar);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
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
               QueryMusic.next();
               initInfo();
               EventBus.getDefault().post("null");
               break;
           case R.id.play_iv_pre :
               QueryMusic.pre();
               initInfo();
               EventBus.getDefault().post("null");
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
}
