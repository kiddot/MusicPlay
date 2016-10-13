package com.liangdekai.musicplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class SearchMusic {
//    public static List<MusicItem> searchMp3(Context context){
//        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , null ,
//            null , null , MediaStore.Audio.Media.DEFAULT_SORT_ORDER) ;
//        List<MusicItem> musicList = new ArrayList<>();
//        if (cursor != null){
//            for (int i = 0 ; i < cursor.getCount() ; i++){
//                cursor.moveToNext();
//                MusicItem musicItem = new MusicItem();
//                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
//                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//                int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
//                if (isMusic != 0){
//                    musicItem.setTitle(title);
//                    musicItem.setArtist(artist);
//                    musicItem.setDuration(duration);
//                    musicItem.setPath(path);
//                    musicList.add(musicItem);
//                }
//            }
//            cursor.close();
//        }
//        return musicList;
//    }
}
