package com.liangdekai.musicplayer.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MusicInfo;
import com.liangdekai.musicplayer.util.OperateMusic;
import com.liangdekai.musicplayer.util.PlayListCache;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2016/10/17.
 */
public class PopupWindowAdapter extends RecyclerView.Adapter<PopupWindowAdapter.MusicHolder>{
    private List<MusicInfo> mPlayList;

    public PopupWindowAdapter(List<MusicInfo> playList) {
        mPlayList = playList;
        Log.d("test" , mPlayList.size()+"当前播放列表总数目");
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bottom_menu_head , parent , false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bottom_menu_common , parent , false);
        }
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicHolder holder, int position) {
        if (position == 0){
            //
        }else{
            holder.musicName.setText(mPlayList.get(position-1).getMusicName());
            holder.artistName.setText(mPlayList.get(position-1).getArtist());
        }
    }

    @Override
    public int getItemCount() {
        return mPlayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }else {
            return 1;
        }
    }

//    public void setOnItemClickListener(OnClickListener listener) {
//        this.mListener = listener;
//    }
//
//    public interface OnClickListener {
//        void onClick(MusicHolder musicHolder , int position , String url);
//    }

    public class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView musicName , artistName;
        public ImageView playState ;

        public MusicHolder(View itemView) {
            super(itemView);
            musicName = (TextView) itemView.findViewById(R.id.item_tv_music_name);
            artistName = (TextView) itemView.findViewById(R.id.item_tv_artist_name);
            playState = (ImageView) itemView.findViewById(R.id.item_iv_play_state);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != 0){
                OperateMusic.playMusic(mPlayList.get(getAdapterPosition()-1).getData());
                playState.setImageResource(R.mipmap.common_play_state);
                playState.setVisibility(View.VISIBLE);
                musicName.setTextColor(Color.parseColor("#d20000"));
                artistName.setTextColor(Color.parseColor("#d20000"));
                PlayListCache.addCacheMusic(mPlayList);
                PlayListCache.rememberPosition(getAdapterPosition() -1);
                EventBus.getDefault().post("null");
            }
        }
    }


//    private void onItemClick(){
//        mListener = new OnClickListener() {
//            @Override
//            public void onClick(MusicHolder musicHolder, int position) {
//                OperateMusic.playMusic(mPlayList.get(position).getData());
//                musicHolder.playState.setImageResource(R.mipmap.common_play_state);
//            }
//        };
//    }
}
