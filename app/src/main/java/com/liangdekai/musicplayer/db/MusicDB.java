package com.liangdekai.musicplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2016/9/5.
 */
public class MusicDB extends SQLiteOpenHelper{
    public static final String DATA_BASE_NAME = "Music" ;
    private static final int VERSION = 4 ;
    private static MusicDB mMusicDb = null ;
    private final Context mContext ;

    public MusicDB(Context context) {
        super(context, DATA_BASE_NAME , null , VERSION);
        this.mContext = context;
    }

    public static synchronized MusicDB getInstance(final Context context){
        if (mMusicDb == null){
            mMusicDb = new MusicDB(context.getApplicationContext());
        }
        return mMusicDb ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
