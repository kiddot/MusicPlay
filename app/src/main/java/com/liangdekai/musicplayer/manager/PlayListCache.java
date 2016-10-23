package com.liangdekai.musicplayer.manager;

import android.util.SparseArray;

import com.liangdekai.musicplayer.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存歌曲的播放位置以及对象
 */
public class PlayListCache {
    public static SparseArray<MusicInfo> mCacheMusic = new SparseArray<>();
    public static int mCurrentPosition ;

    public static void rememberPosition(int position){
        mCurrentPosition = position ;
    }

    public static int getPosition(){
        return mCurrentPosition;
    }

    public static void addCacheMusic(int position , MusicInfo musicInfo){
        mCacheMusic.put(position , musicInfo);
    }

    public static int getMusicSize(){
        return mCacheMusic.size();
    }

    public static void addCacheMusic(List<MusicInfo> list){
        for (int i = 0 ; i < list.size() ; i++){
            mCacheMusic.put(i , list.get(i));
        }
    }

    public static MusicInfo getCacheMusic(int position){
        return mCacheMusic.get(position);
    }

    public static List<MusicInfo> getAllMusic(){
        List<MusicInfo> list  = new ArrayList<>();
        for (int i = 0 ; i < mCacheMusic.size() ; i++){
            list.add(mCacheMusic.get(i));
        }
        return list;
    }
}
