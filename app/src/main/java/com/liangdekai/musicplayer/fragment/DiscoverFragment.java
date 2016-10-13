package com.liangdekai.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    private ViewGroup mLayout ;
    private ViewPager mViewPager ;
    private TabLayout mTabLayout ;
    private String[] mTitle = new String[]{"个人推荐" ,"歌单" ,"主播电台" ,"排行榜" };
    private ViewPagerAdapter mAdapter ;
    private List<Fragment> mFragment ;
    private CommandFragment mCommandFragment ;
    private SongListFragment mSongListFragment ;
    private RadioFragment mRadioFragment ;
    private RankFragment mRankFragment ;

    public static DiscoverFragment newInstance(){
        DiscoverFragment discoverFragment = new DiscoverFragment();
        return discoverFragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_common, null , false);
        init(view);
        return view;
    }

    private void init(View view){
        mFragment = new ArrayList<>();
        mCommandFragment = CommandFragment.newInstance();
        mSongListFragment = SongListFragment.newInstance();
        mRadioFragment = RadioFragment.newInstance();
        mRankFragment = RankFragment.newInstance();
        mFragment.add(mCommandFragment);
        mFragment.add(mSongListFragment);
        mFragment.add(mRadioFragment);
        mFragment.add(mRankFragment);
        mAdapter = new ViewPagerAdapter(getChildFragmentManager() , mFragment , mTitle) ;
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_discover_view_pager) ;
        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_discover_tab) ;
        //mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
