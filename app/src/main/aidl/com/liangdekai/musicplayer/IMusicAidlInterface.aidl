// IMusicAidlInterface.aidl
package com.liangdekai.musicplayer;

// Declare any non-default types here with import statements
import com.liangdekai.musicplayer.bean.MusicInfo ;

interface IMusicAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void play();
    void playMusic(String url);
    void seekTo(int currentTime);
    void pause();
    void next(in MusicInfo musicInfo);
    void pre(in MusicInfo musicInfo);
    int duration(in MusicInfo musicInfo);
    int position();
    boolean isPlaying();
    String getMusicName(in MusicInfo musicInfo);
    String getArtistName(in MusicInfo musicInfo);
}
