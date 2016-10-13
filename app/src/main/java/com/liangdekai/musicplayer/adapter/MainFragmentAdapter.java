package com.liangdekai.musicplayer.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MainItem;
import com.liangdekai.musicplayer.bean.PlayInfo;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder> {

    private static final int EXPAND_ITEM = 0 ;
    private static final int NORMAL_ITEM = 1 ;
    private static final int CHILD_ITEM = 2 ;
    private ArrayList<PlayInfo> mPlayInfos ;
    private boolean mCreatedExpanded = true ;
    private boolean mCollectExpanded = true ;
    private Context mContext ;
    private List<MainItem> mItemList ;
    private SetOnClickListener mListener ;

    public MainFragmentAdapter(Context context , List<MainItem> itemList){
        this.mContext = context ;
        mItemList = itemList ;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM){
                View normal = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent , false);
                return new ItemHolder(normal);
        }else{
            View expand = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable , parent , false) ;
            return new ItemHolder(expand);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2){
            return EXPAND_ITEM ;
        }
        return NORMAL_ITEM ;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        if (position == 2){
            holder.expand.setImageResource(R.mipmap.item_expand_arrow);
            holder.title.setText("收藏的歌单");
        }else {
            MainItem mainItem = mItemList.get(position) ;
            holder.image.setImageResource(mainItem.getImageId());
            holder.text.setText(mainItem.getTitle());
            holder.count.setText("("+mainItem.getCount()+")");
            mListener.onClick(holder , position);
        }
    }

    public void onClick(SetOnClickListener listener){
        mListener = listener ;
    }

    public interface SetOnClickListener{
        void onClick(ItemHolder itemHolder , int position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size()+1;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView image , expand , more;
        protected TextView text , count , title;

        public ItemHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.fragment_main_local_image) ;
            text = (TextView) itemView.findViewById(R.id.fragment_main_local_text) ;
            count = (TextView) itemView.findViewById(R.id.fragment_main_local_count) ;

            expand = (ImageView) itemView.findViewById(R.id.fragment_expand_image) ;
            more = (ImageView) itemView.findViewById(R.id.fragment_expand_more) ;
            title = (TextView) itemView.findViewById(R.id.fragment_expand_title) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ObjectAnimator animator ;
            animator = ObjectAnimator.ofFloat(expand , "rotation" , 90 ,0) ;
            animator.setDuration(100);
            animator.setRepeatCount(0);
            animator.setInterpolator(new LinearInterpolator());
            switch (getItemViewType()){
                case 0 :
                    if (mCollectExpanded){
                        animator.start();
                        mCollectExpanded = false ;
                    }else{
                        animator.reverse();
                        mCollectExpanded = true ;
                    }
            }
        }
    }
}
