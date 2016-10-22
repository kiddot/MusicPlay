package com.liangdekai.musicplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.net.MusicApi;

/**
 * Created by asus on 2016/9/5.
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_DELAY_MILLIS = 1500 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splash();
//            }
//        } , SPLASH_DELAY_MILLIS);
        splash();
    }

    private void splash(){
        MainActivity.startActivity(SplashActivity.this);
        finish();
    }
}
