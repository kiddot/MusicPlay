package com.liangdekai.musicplayer.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.liangdekai.musicplayer.IMusicAidlInterface;
import com.liangdekai.musicplayer.service.PlayService;

public class QueryMusic {
    public static IMusicAidlInterface mQuery ;
    public static BinderConnection mConnection;

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

    public static String getArtistName(){
        try {
            if (mQuery != null){
                return mQuery.getArtistName();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDuration(){
        try {
            if (mQuery != null){
                return mQuery.duration();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMusicName(){
        try {
            if (mQuery != null){
                return mQuery.getMusicName();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
