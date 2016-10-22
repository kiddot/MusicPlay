package com.liangdekai.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liangdekai.musicplayer.R;

/**
 * Created by asus on 2016/10/18.
 */
public class SearchActivity extends AppCompatActivity {

    public static void startActivity(Context context){
        Intent intent = new Intent(context , SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
