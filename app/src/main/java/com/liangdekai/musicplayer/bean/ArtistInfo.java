package com.liangdekai.musicplayer.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2016/9/6.
 */
public class ArtistInfo implements Parcelable {
    private static final String ARTIST_NAME = "artistName" ;
    private static final String NUMBER_OF_TRACKS = "numberTracks" ;
    private static final String ARTIST_ID = "artistId" ;

    private String artistName ;
    private int numberTracks ;
    private long artistId ;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getNumberTracks() {
        return numberTracks;
    }

    public void setNumberTracks(int numberTracks) {
        this.numberTracks = numberTracks;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public static final Creator<ArtistInfo> CREATOR = new Creator<ArtistInfo>() {
        @Override
        public ArtistInfo createFromParcel(Parcel in) {
            Bundle bundle = in.readBundle(getClass().getClassLoader());
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(bundle.getString(ARTIST_NAME));
            artistInfo.setNumberTracks(bundle.getInt(NUMBER_OF_TRACKS));
            artistInfo.setArtistId(bundle.getLong(ARTIST_ID));
            return artistInfo;
        }

        @Override
        public ArtistInfo[] newArray(int size) {
            return new ArtistInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME , artistName);
        bundle.putInt(NUMBER_OF_TRACKS , numberTracks);
        bundle.putLong(ARTIST_ID , artistId);
        parcel.writeBundle(bundle);
    }
}
