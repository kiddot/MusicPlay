package com.liangdekai.musicplayer.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.liangdekai.musicplayer.IMusicAidlInterface;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.service.PlayService;
import com.liangdekai.musicplayer.util.PlayListCache;

/**
 * 对外提供方法对音乐进行各种操作以及获取当前正在播放音乐的相关信息
 */
public class OperateMusic {
    public static IMusicAidlInterface mQuery ;
    public static BinderConnection mConnection;

    /**
     * 绑定远程服务
     * @param context
     */
    public static void bindToService(Context context){
        Intent intent = new Intent(context , PlayService.class);
        context.startService(intent);
        mConnection = new BinderConnection();
        context.bindService(intent , mConnection , context.BIND_AUTO_CREATE);
    }

    public static class BinderConnection implements ServiceConnection{

        /**
         * 连接服务成功之后
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mQuery = IMusicAidlInterface.Stub.asInterface(service);
            Log.d("test" , "连接服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public static int position(){
        try {
            return mQuery.position();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isPlaying(){
        try {
            if (mQuery != null){
                return mQuery.isPlaying();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void seekTo(int currentTime){
        try {
            mQuery.seekTo(currentTime);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void start(){
        try {
            mQuery.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pause(){
        try {
            mQuery.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void next(){
        if (PlayListCache.getPosition() == PlayListCache.getMusicSize()-1){
            PlayListCache.rememberPosition(0);
        }else{
            PlayListCache.rememberPosition(PlayListCache.getPosition()+1);
        }
        try {
            mQuery.next(PlayListCache.getCacheMusic(PlayListCache.getPosition()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pre(){
        if (PlayListCache.getPosition() == 0){
            PlayListCache.rememberPosition(PlayListCache.getMusicSize()-1);
        }else{
            PlayListCache.rememberPosition(PlayListCache.getPosition()-1);
        }
        try {
            mQuery.pre(PlayListCache.getCacheMusic(PlayListCache.getPosition()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void playMusic(String url){
        try {
            mQuery.playMusic(url);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String getArtistName(MusicInfo musicInfo){
        try {
            if (mQuery != null){
                return mQuery.getArtistName(musicInfo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDuration(MusicInfo musicInfo){
        try {
            if (mQuery != null){
                return mQuery.duration(musicInfo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMusicName(MusicInfo musicInfo){
        try {
            if (mQuery != null){
                return mQuery.getMusicName(musicInfo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
