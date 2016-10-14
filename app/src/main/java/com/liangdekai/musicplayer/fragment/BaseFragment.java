package com.liangdekai.musicplayer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.util.MusicAction;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class BaseFragment extends Fragment {

//    private BroadcastReceiver mMusicInfoListener = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            MusicInfo musicInfo = intent.getParcelableExtra("musicInfo");
//            if (action.equals(MusicAction.MUSIC_PLAY)){
//                upDateMusicInfo(musicInfo);
//            }
//        }
//    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MusicAction.MUSIC_PLAY);
//        getActivity().registerReceiver(mMusicInfoListener , intentFilter);
    }

    public void upDateMusicInfo(MusicInfo musicInfo){

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe
    public void onEvent(MusicInfo musicInfo){
        upDateMusicInfo(musicInfo);
    }

}
