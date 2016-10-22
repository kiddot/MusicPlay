package com.liangdekai.musicplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.fragment.BottomControl;
import com.liangdekai.musicplayer.util.OperateMusic;

public class BottomActivity extends AppCompatActivity {
    protected BottomControl mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OperateMusic.bindToService(this);
        showQuickControl(true);
    }

    /**
     * @param show 显示或关闭底部播放控制栏
     */
    protected void showQuickControl(boolean show) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (mBottomFragment == null) {
                mBottomFragment = BottomControl.newInstance();
                fragmentTransaction.add(R.id.bottom_fl_control, mBottomFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(mBottomFragment).commitAllowingStateLoss();
            }
        } else {
            if (mBottomFragment != null)
                fragmentTransaction.hide(mBottomFragment).commitAllowingStateLoss();
        }
    }
}
