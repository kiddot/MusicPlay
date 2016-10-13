package com.liangdekai.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by asus on 2016/9/4.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment ;
    private String[] mTitle = null;

    public ViewPagerAdapter(FragmentManager fm , List<Fragment> fragmentList , String[] title) {
        super(fm);
        this.mFragment = fragmentList ;
        mTitle = title ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
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
