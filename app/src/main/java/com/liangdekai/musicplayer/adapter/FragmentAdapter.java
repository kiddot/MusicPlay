package com.liangdekai.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragment ;
    private FragmentManager mFragmentManger ;

    public FragmentAdapter(FragmentManager fm , List<Fragment> fragmentList) {
        super(fm);
        mFragment = fragmentList ;
        mFragmentManger = fm ;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
