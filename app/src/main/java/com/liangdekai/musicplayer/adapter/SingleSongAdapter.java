package com.liangdekai.musicplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MusicInfo;

import java.util.List;

public class SingleSongAdapter extends RecyclerView.Adapter<SingleSongAdapter.SongHolder> {
    private List<MusicInfo> mMusicList ;
    private OnClickListener mListener ;


    public SingleSongAdapter(List<MusicInfo> list){
        mMusicList = list ;
        //PlayListCache.addCacheMusic(mMusicList);
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
//        switch (viewType){
//            case 1 :
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_head , parent , false);
//                return new SongHolder(view);
//            case 0 :
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_local_music , parent , false);
//                return new SongHolder(view);
//        }
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_head , parent , false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_local_music , parent , false);
        }
        return new SongHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        }else {
            return 0;
        }
    }

    public void onClick(OnClickListener listener){
        mListener = listener ;
    }

    public interface OnClickListener {
        void onClick(SongHolder itemHolder , MusicInfo musicInfo , int position);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        if (position == 0){
            //
        }else {
            holder.name.setText(mMusicList.get(position-1).getMusicName());
            holder.artist.setText(mMusicList.get(position-1).getArtist());
            mListener.onClick(holder , mMusicList.get(position-1) , position-1);
        }
    }

    @Override
    public int getItemCount() {
        return mMusicList.size()+1;
    }

    public class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView ,music , more;
        TextView name , artist , text;

        public SongHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_tv_name);
            artist = (TextView) itemView.findViewById(R.id.item_tv_artist);
            music = (ImageView) itemView.findViewById(R.id.item_iv_music);

            text = (TextView) itemView.findViewById(R.id.item_head_text);
            imageView = (ImageView) itemView.findViewById(R.id.item_head_image);
            more = (ImageView) itemView.findViewById(R.id.item_head_more);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
