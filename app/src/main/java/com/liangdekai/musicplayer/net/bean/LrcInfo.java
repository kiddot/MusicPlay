package com.liangdekai.musicplayer.net.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/19.
 */
public class LrcInfo {
    public List<LrcLine> lrcLines ;
    private String artist ;
    private String musicName ;
    private String album ;

    public List<LrcLine> getLrcLines() {
        return lrcLines;
    }

    public void setLrcLines(List<LrcLine> lrcLines) {
        this.lrcLines = lrcLines;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
