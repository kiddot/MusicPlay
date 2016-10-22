package com.liangdekai.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.liangdekai.musicplayer.IMusicAidlInterface;
import com.liangdekai.musicplayer.bean.MusicInfo;

import java.io.IOException;

public class PlayService extends Service{
    private static final String MUSIC_INFO = "musicInfo";
    private MediaPlayer mMediaPlayer ;
    private Binder mBinder ;
    private MusicInfo mMusicInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mBinder = new MusicStub(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicInfo = intent.getParcelableExtra(MUSIC_INFO);
//        int currentPosition = intent.getIntExtra(CURRENT_POSITION, -1);
//        if (currentPosition == -1){
//            currentPosition = PlayListCache.getPosition();
//        }else{
//            PlayListCache.rememberPosition(currentPosition);
//        }
//        Log.d("test" , currentPosition +"当前播放位置" +"，实际位置"+PlayListCache.getPosition());
        if (mMusicInfo != null){
            //PlayListCache.addCacheMusic(MusicHelper.queryMusic(this));
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

    private void next(MusicInfo musicInfo){
//        if (PlayListCache.getPosition() == PlayListCache.getMusicSize()-1){
//            PlayListCache.rememberPosition(0);
//            playMusic(PlayListCache.getCacheMusic(PlayListCache.getPosition()).getData());
//        }else{
//            PlayListCache.rememberPosition(PlayListCache.getPosition()+1);
//            playMusic(PlayListCache.getCacheMusic(PlayListCache.getPosition()).getData());
//        }
        playMusic(musicInfo.getData());
    }

    private void pre(MusicInfo musicInfo){
//        if (PlayListCache.getPosition() == 0){
//            PlayListCache.rememberPosition(PlayListCache.getMusicSize()-1);
//            playMusic(PlayListCache.getCacheMusic(PlayListCache.getPosition()).getData());
//        }else{
//            PlayListCache.rememberPosition(PlayListCache.getPosition()-1);
//            playMusic(PlayListCache.getCacheMusic(PlayListCache.getPosition()).getData());
//        }
        playMusic(musicInfo.getData());
    }

    private void startMusic(){
        mMediaPlayer.start();
    }

    private void seekTo(int currentTime){
        mMediaPlayer.seekTo(currentTime);
    }

    private String getMusicName(MusicInfo musicInfo){
        if (musicInfo == null){
            return null;
        }
        return musicInfo.getMusicName();
    }

    private String getArtistName(MusicInfo musicInfo){
        if (musicInfo == null){
            return null;
        }
        return musicInfo.getArtist();
    }

    private int getDuration(MusicInfo musicInfo){
        if (musicInfo == null){
            return 0;
        }
        return musicInfo.getDuration();
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
        public void playMusic(String url) throws RemoteException {
            mService.playMusic(url);
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
        public void next(MusicInfo musicInfo) throws RemoteException {
            mService.next(musicInfo);
        }

        @Override
        public void pre(MusicInfo musicInfo) throws RemoteException {
            mService.pre(musicInfo);
        }

        @Override
        public int duration(MusicInfo musicInfo) throws RemoteException {
            return mService.getDuration(musicInfo);
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
        public String getMusicName(MusicInfo musicInfo) throws RemoteException {
            return mService.getMusicName(musicInfo);
        }

        @Override
        public String getArtistName(MusicInfo musicInfo) throws RemoteException {
            return mService.getArtistName(musicInfo);
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

}
