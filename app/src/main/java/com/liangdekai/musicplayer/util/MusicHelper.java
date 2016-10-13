package com.liangdekai.musicplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.view.menu.ListMenuItemView;

import com.liangdekai.musicplayer.bean.ArtistInfo;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.service.PlayService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/9/5.
 */
public class MusicHelper {
    private static String[] allMusic = new String[]{
            MediaStore.Audio.Media._ID ,
            MediaStore.Audio.Media.ALBUM_ID ,
            MediaStore.Audio.Media.ALBUM ,
            MediaStore.Audio.Media.DURATION ,
            MediaStore.Audio.Media.TITLE ,
            MediaStore.Audio.Media.ARTIST ,
            MediaStore.Audio.Media.ARTIST_ID ,
            MediaStore.Audio.Media.DATA ,
            MediaStore.Audio.Media.SIZE
    };
    private static String[] albumMusic = new String[]{
            MediaStore.Audio.Albums._ID ,
            MediaStore.Audio.Albums.ALBUM_ART ,
            MediaStore.Audio.Albums.ALBUM ,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS ,
            MediaStore.Audio.Albums.ARTIST
    };
    private static String[] artistMusic = new String[]{
            MediaStore.Audio.Artists.ARTIST ,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS ,
            MediaStore.Audio.Artists._ID
    };
    private static String[] folderMusic = new String[]{
            MediaStore.Files.FileColumns.DATA
    };

    public static List<ArtistInfo> queryArtist(Context context){
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI ;
        ContentResolver contentResolver = context.getContentResolver() ;
        List<ArtistInfo> artistInfoList = getArtistList(contentResolver.query(uri , artistMusic , null ,
                null , MediaStore.Audio.Artists.DEFAULT_SORT_ORDER));
        return artistInfoList ;
    }

    public static List<ArtistInfo> getArtistList(Cursor cursor){
        List<ArtistInfo> artistInfoList = new ArrayList<>();
        while(cursor.moveToNext()){
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
            artistInfo.setNumberTracks(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));
            artistInfo.setArtistId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
            artistInfoList.add(artistInfo);
        }
        cursor.close();
        return artistInfoList ;
    }

    public static List<MusicInfo> queryMusic(Context context){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ;
        ContentResolver contentResolver = context.getContentResolver() ;
        List<MusicInfo> musicInfoList = getMusicList(contentResolver.query(uri , allMusic , null ,
                null , MediaStore.Audio.Media.DEFAULT_SORT_ORDER));
        return musicInfoList ;
    }

    public static List<MusicInfo> getMusicList(Cursor cursor){
        List<MusicInfo> musicInfoList = new ArrayList<>() ;
        while (cursor.moveToNext()){
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setSongId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicInfo.setAlbumId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicInfo.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            //
            musicInfo.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            musicInfo.setMusicName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicInfo.setArtistId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)) ;
            musicInfo.setData(path);
            musicInfo.setFolder(path.substring(0 , path.lastIndexOf(File.separator)));
            musicInfo.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
            musicInfo.setLocal(true);
            musicInfoList.add(musicInfo) ;
        }
        cursor.close();
        return musicInfoList ;
    }

}
