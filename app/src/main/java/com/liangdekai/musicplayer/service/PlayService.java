package com.liangdekai.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liangdekai.musicplayer.IMusicAidlInterface;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.util.MusicCache;
import com.liangdekai.musicplayer.util.MusicHelper;

import java.io.IOException;

public class PlayService extends Service{
    private static final String CURRENT_POSITION = "current";
    private static final String MUSIC_INFO = "musicInfo";
    private MediaPlayer mMediaPlayer ;
    private Binder mBinder ;
    private MusicInfo mMusicInfo;
    private String mMusicName ;
    private String mArtistName;
    private int mDuration;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mBinder = new MusicStub(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicInfo = intent.getParcelableExtra(MUSIC_INFO);
        int currentPosition = intent.getIntExtra(CURRENT_POSITION, -1);
        if (currentPosition == -1){
            currentPosition = MusicCache.getPosition();
        }else{
            MusicCache.rememberPosition(currentPosition);
        }
        Log.d("test" , currentPosition +"当前播放位置");
        if (mMusicInfo != null){
            MusicCache.addCacheMusic(MusicHelper.queryMusic(this));
            if (mMusicInfo.getData() != null){
                playMusic(mMusicInfo.getData());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void pauseMusic(){
        mMediaPlayer.pause();
    }

    private void next(){
        MusicCache.rememberPosition(MusicCache.getPosition()+1);
        playMusic(MusicCache.getCacheMusic(MusicCache.getPosition()).getData());
    }

    private void pre(){
        MusicCache.rememberPosition(MusicCache.getPosition()-1);
        playMusic(MusicCache.getCacheMusic(MusicCache.getPosition()).getData());
    }

    private void startMusic(){
        mMediaPlayer.start();
    }

    private void seekTo(int currentTime){
        mMediaPlayer.seekTo(currentTime);
    }

    private String getMusicName(){
        if (MusicCache.getCacheMusic(MusicCache.getPosition()) == null){
            return null;
        }
        return MusicCache.getCacheMusic(MusicCache.getPosition()).getMusicName();
    }

    private String getArtistName(){
        if (MusicCache.getCacheMusic(MusicCache.getPosition()) == null){
            return null;
        }
        return MusicCache.getCacheMusic(MusicCache.getPosition()).getArtist();
    }

    private int getDuration(){
        if (MusicCache.getCacheMusic(MusicCache.getPosition()) == null){
            return 0;
        }
        return MusicCache.getCacheMusic(MusicCache.getPosition()).getDuration();
    }

    private void playMusic(String url){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                }
            });
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNowPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }




    private static final class MusicStub extends IMusicAidlInterface.Stub{
        private PlayService mService;

        public MusicStub(PlayService service){
            mService = service;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void play() throws RemoteException {
            mService.startMusic();
        }

        @Override
        public void seekTo(int currentTime) throws RemoteException {
            mService.seekTo(currentTime);
        }

        @Override
        public void pause() throws RemoteException {
            mService.pauseMusic();
        }

        @Override
        public void next() throws RemoteException {
            mService.next();
        }

        @Override
        public void pre() throws RemoteException {
            mService.pre();
        }

        @Override
        public int duration() throws RemoteException {
            return mService.getDuration();
        }

        @Override
        public int position() throws RemoteException {
            return mService.getCurrentPosition();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return mService.isNowPlaying();
        }

        @Override
        public String getMusicName() throws RemoteException {
            return mService.getMusicName();
        }

        @Override
        public String getArtistName() throws RemoteException {
            return mService.getArtistName();
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

}
