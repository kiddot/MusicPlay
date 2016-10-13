package com.liangdekai.musicplayer.util;

import android.util.SparseArray;

import com.liangdekai.musicplayer.bean.MusicInfo;

import java.util.List;

/**
 * Created by asus on 2016/10/13.
 */
public class MusicCache {
    public static SparseArray<MusicInfo> mCacheMusic = new SparseArray<>();


    public static void addCacheMusic(int position , MusicInfo musicInfo){
        mCacheMusic.put(position , musicInfo);
    }

    public static MusicInfo getCacheMusic(int position){
        return mCacheMusic.get(position);
    }
}
