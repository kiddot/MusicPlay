package com.liangdekai.musicplayer.fragment;

/**
 * Created by asus on 2016/9/18.
 */
public class SongListFragment extends BaseLazyFragment {

    public static SongListFragment newInstance(){
        SongListFragment songListFragment = new SongListFragment() ;
        return  songListFragment;
    }

    @Override
    protected void lazyLoad() {

    }
}
