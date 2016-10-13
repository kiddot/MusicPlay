package com.liangdekai.musicplayer.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2016/9/5.
 */
public class MusicInfo implements Parcelable{

    public static final String SONG_ID = "songID";
    public static final String ALBUM_ID = "albumId" ;
    public static final String ALBUM_NAME = "albumName" ;
    public static final String ALBUM_DATA = "albumData" ;
    public static final String DURATION = "duration" ;
    public static final String MUSIC_NAME = "musicName" ;
    public static final String ARTIST = "artist" ;
    public static final String ARTIST_ID = "artistId" ;
    public static final String DATA = "data" ;
    public static final String FOLDER = "folder" ;
    public static final String SIZE = "size" ;
    public static final String LRC = "lrc" ;
    public static final String IS_LOCAL = "isLocal" ;

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumData() {
        return albumData;
    }

    public void setAlbumData(String albumData) {
        this.albumData = albumData;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    private long songId ;
    private int albumId ;
    private String albumName ;
    private String albumData ;
    private int duration ;
    private String musicName ;
    private String artist ;
    private long artistId ;
    private String data ;
    private String folder ;
    private String lrc ;
    private boolean isLocal ;
    private int size ;

    private int favorite = 0 ;

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            MusicInfo musicInfo = new MusicInfo();
            Bundle bundle = in.readBundle(getClass().getClassLoader());
            musicInfo.setSongId(bundle.getLong(SONG_ID));
            musicInfo.setAlbumId(bundle.getInt(ALBUM_ID));
            musicInfo.setAlbumName(bundle.getString(ALBUM_NAME));
            musicInfo.setDuration(bundle.getInt(DURATION));
            musicInfo.setMusicName(bundle.getString(MUSIC_NAME));
            musicInfo.setArtist(bundle.getString(ARTIST));
            musicInfo.setArtistId(bundle.getLong(ARTIST_ID));
            musicInfo.setData(bundle.getString(DATA));
            musicInfo.setFolder(bundle.getString(FOLDER));
            musicInfo.setAlbumData(bundle.getString(ALBUM_DATA));
            musicInfo.setSize(bundle.getInt(SIZE));
            return musicInfo;
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putLong(SONG_ID , songId);
        bundle.putInt(ALBUM_ID , albumId);
        bundle.putString(ALBUM_NAME , albumName);
        bundle.putString(ALBUM_DATA , albumData);
        bundle.putInt(DURATION , duration);
        bundle.putString(MUSIC_NAME , musicName);
        bundle.putString(ARTIST , artist);
        bundle.putLong(ARTIST_ID , artistId);
        bundle.putString(DATA , data);
        bundle.putString(FOLDER , folder);
        bundle.putInt(SIZE ,size);
        bundle.putBoolean(IS_LOCAL, isLocal);
        parcel.writeBundle(bundle);
    }
}
