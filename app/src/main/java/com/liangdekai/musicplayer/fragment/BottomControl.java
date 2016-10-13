package com.liangdekai.musicplayer.fragment;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.util.QueryMusic;

public class BottomControl extends BaseFragment implements View.OnClickListener{
    private ImageView mPlay ;
    private ImageView mNext;
    private ImageView mMenu ;
    private TextView mSong ;
    private TextView mArtist ;
    private ProgressBar mProgress ;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                mProgress.setProgress(QueryMusic.position());
                handler.sendEmptyMessageDelayed(1 , 1000);
            }
        }
    };

    public static BottomControl newInstance(){
        return new BottomControl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_control_music ,container , false);
        init(view);
        setMusicInfo();
        setListener();
        return view;
    }

    /**
     * 实例化控件
     * @param view
     */
    private void init(View view){
        mProgress = (ProgressBar) view.findViewById(R.id.main_bottom_progress);
        mSong = (TextView) view.findViewById(R.id.main_bottom_song);
        mArtist = (TextView) view.findViewById(R.id.main_bottom_artist);
        mPlay = (ImageView) view.findViewById(R.id.main_bottom_play);
        mNext = (ImageView) view.findViewById(R.id.main_bottom_next);
    }

    /**
     * 初始化底部播放控制台的音乐信息
     */
    private void setMusicInfo(){
        mSong.setText(QueryMusic.getMusicName());
        mArtist.setText(QueryMusic.getArtistName());
        if (QueryMusic.isPlaying()){
            Log.d("test" , "正在播放音乐");
            mPlay.setImageResource(R.mipmap.main_bottom_pause);
            mProgress.setMax(QueryMusic.getDuration());
            handler.sendEmptyMessageDelayed(1 , 100);
        }
    }

    /**
     * 设置按钮的事件监听
     */
    private void setListener(){
        mPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_bottom_play :
                if (QueryMusic.isPlaying()){
                    QueryMusic.pause();
                }else{
                    QueryMusic.start();
                }
                upDateIcon();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroyView();
    }

    @Override
    public void upDateMusicInfo(MusicInfo musicInfo) {
        mProgress.setMax(musicInfo.getDuration());
        mSong.setText(musicInfo.getMusicName());
        mArtist.setText(musicInfo.getArtist());
        handler.sendEmptyMessageDelayed(1 , 1000);
        mPlay.setImageResource(R.mipmap.main_bottom_pause);
    }

    private void upDateIcon(){
        if (QueryMusic.isPlaying()){
            mPlay.setImageResource(R.mipmap.main_bottom_pause);
        }else {
            mPlay.setImageResource(R.mipmap.main_bottom_play);
        }
    }
}
