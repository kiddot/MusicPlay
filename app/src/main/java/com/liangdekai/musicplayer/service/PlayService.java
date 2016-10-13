package com.liangdekai.musicplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.liangdekai.musicplayer.IMusicAidlInterface;
import com.liangdekai.musicplayer.bean.MusicInfo;

import java.io.IOException;

public class PlayService extends Service{
    private static final String CURRENT_MUSIC = "currentMusic";
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
        mMusicInfo = intent.getParcelableExtra("musicInfo");
        if (mMusicInfo != null){
            saveCurrentMusic();
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

    private void startMusic(){
        mMediaPlayer.start();
    }

    private void saveCurrentMusic(){
        SharedPreferences.Editor editor = getSharedPreferences(CURRENT_MUSIC , Context.MODE_APPEND).edit();
        editor.putString("musicName" , mMusicInfo.getMusicName());
        editor.putString("artistName" , mMusicInfo.getArtist());
        editor.putInt("duration" , mMusicInfo.getDuration());
        editor.apply();
    }

    private void getCurrentMusic(){
        SharedPreferences preferences = getSharedPreferences(CURRENT_MUSIC , Context.MODE_APPEND) ;
        mMusicName = preferences.getString("musicName" , "");
        mArtistName = preferences.getString("artistName" , "");
        mDuration = preferences.getInt("duration" , 0);
    }

    private String getMusicName(){
        getCurrentMusic();
        return mMusicName;
    }

    private String getArtistName(){
        getCurrentMusic();
        return mArtistName;
    }

    private int getDuration(){
        getCurrentMusic();
        return mDuration;
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
        public void pause() throws RemoteException {
            mService.pauseMusic();
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
