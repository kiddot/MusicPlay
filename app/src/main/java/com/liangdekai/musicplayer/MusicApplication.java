package com.liangdekai.musicplayer;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by asus on 2016/8/8.
 */
public class MusicApplication extends Application {
    private static Context context;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(this , null);
    }

    public static Context getContext() {
        return context;
    }

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
