package com.liangdekai.musicplayer.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.adapter.SingleSongAdapter;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.service.PlayService;
import com.liangdekai.musicplayer.util.DividerItemDecoration;
import com.liangdekai.musicplayer.util.PlayListCache;
import com.liangdekai.musicplayer.util.MusicHelper;

import de.greenrobot.event.EventBus;

public class SingleSongFragment extends BaseLazyFragment {
    private static final String CURRENT_POSITION = "current";
    private static final String MUSIC_INFO = "musicInfo";
    private SingleSongAdapter mAdapter;
    private RecyclerView mRecyclerView ;
    private ViewGroup mLayout ;

    public static SingleSongFragment newInstance(){
        return new SingleSongFragment();
    }

    @Override
    protected void lazyLoad() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_local_music , null , false);
        init(view);
        onClick();
        mLayout = getLayout();
        if (mLayout != null){
            mLayout.removeAllViews();
            mLayout.addView(view);
            mLayout.invalidate();
        }
    }

    /**
     * 初始化控件
     * @param view
     */
    private void init(View view){
        mAdapter = new SingleSongAdapter(MusicHelper.queryMusic(getActivity()));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_local_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity() , DividerItemDecoration.VERTICAL_LIST));

        getActivity().getWindow().setBackgroundDrawableResource(R.color.menu_item_white);
    }

    /**
     * 当前界面总列表对应item点击事件
     */
    private void onClick(){
        mAdapter.onClick(new SingleSongAdapter.OnClickListener() {
            @Override
            public void onClick(SingleSongAdapter.SongHolder itemHolder, final MusicInfo musicInfo , final int position) {
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(MUSIC_INFO , musicInfo);

                        Intent intent = new Intent(getActivity() , PlayService.class);
                        //intent.putExtra(MUSIC_URL , musicInfo.getData());
                        intent.putExtra(CURRENT_POSITION , position);
                        intent.putExtras(bundle);
                        PlayListCache.rememberPosition(position);
                        PlayListCache.addCacheMusic(MusicHelper.queryMusic(getActivity()));
                        getActivity().startService(intent);
                        EventBus.getDefault().post(musicInfo);
//                        Intent sendBroadcast = new Intent(MusicAction.MUSIC_PLAY);
//                        sendBroadcast.putExtras(bundle);
//                        getActivity().sendBroadcast(sendBroadcast);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
