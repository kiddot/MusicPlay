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

public class MineFragment extends Fragment {
    private ViewPager mViewPager ;
    private TabLayout mTabLayout ;
    private String[] mTitle = new String[]{"动态" ,"附近" ,"好友" };
    private ViewPagerAdapter mAdapter ;
    private List<Fragment> mFragment ;
    private TrendFragment mTreadFragment ;
    private NearFragment mNearFragment ;
    private FriendFragment mFriendFragment ;

    public static MineFragment newInstance(){
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
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
        mTreadFragment = TrendFragment.newInstance();
        mNearFragment = NearFragment.newInstance();
        mFriendFragment = FriendFragment.newInstance();
        mFragment.add(mTreadFragment);
        mFragment.add(mNearFragment);
        mFragment.add(mFriendFragment);
        mAdapter = new ViewPagerAdapter(getChildFragmentManager() , mFragment , mTitle) ;
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_discover_view_pager) ;
        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_discover_tab) ;
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
