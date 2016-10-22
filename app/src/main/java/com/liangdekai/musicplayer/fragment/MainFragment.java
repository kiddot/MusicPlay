package com.liangdekai.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.activity.LocalMusicActivity;
import com.liangdekai.musicplayer.adapter.MainFragmentAdapter;
import com.liangdekai.musicplayer.bean.MainItem;
import com.liangdekai.musicplayer.util.DividerItemDecoration;
import com.liangdekai.musicplayer.util.MusicHelper;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener {

    private MainFragmentAdapter mAdapter ;
    private RecyclerView mRecyclerView ;
    private SwipeRefreshLayout mSwipeRefreshLayout ;
    private List<MainItem> mItemList ;
    private ViewGroup mLayout ;


    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater ,container , savedInstanceState);
    }

    @Override
    protected void lazyLoad() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main , null ,false);
        initView(view);
        setOnClickListener();
        mLayout = getLayout();
        if (mLayout != null) {
            mLayout.removeAllViews();
            mLayout.addView(view);
            mLayout.invalidate();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view){
        mItemList = new ArrayList<>();
        calculateCount();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_main_refresh) ;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_recycler) ;
        mSwipeRefreshLayout.setColorSchemeColors(R.color.toolBar);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MainFragmentAdapter(getActivity() , mItemList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity() , DividerItemDecoration.VERTICAL_LIST));

        getActivity().getWindow().setBackgroundDrawableResource(R.color.menu_item_white);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //
    }



    private void setOnClickListener(){
        mAdapter.onClick(new MainFragmentAdapter.SetOnClickListener() {
            @Override
            public void onClick(MainFragmentAdapter.ItemHolder itemHolder, int position) {
                switch (position){
                    case 0 :
                        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LocalMusicActivity.startActivity(getActivity());
                            }
                        });
                        break;
                    case 1 :
                        itemHolder.itemView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                            }
                        });
                }
            }
        });
    }

    /**
     * 计算音乐歌手总共数量
     */
    private void calculateCount(){
        int localMusicCount = MusicHelper.queryMusic(getActivity()).size() ;
        int artistCount = MusicHelper.queryArtist(getActivity()).size() ;
        setData("本地音乐" , localMusicCount , R.mipmap.main_icon_local);
        setData("我的歌手" , artistCount , R.mipmap.main_icon_artist);
    }

    /**
     * 设置当前界面的总列表的子Item数据
     * @param title
     * @param count
     * @param id
     */
    private void setData(String title , int count , int id){
        MainItem mainItem = new MainItem() ;
        mainItem.setTitle(title);
        mainItem.setCount(count);
        mainItem.setImageId(id);
        mItemList.add(mainItem);
    }

}
