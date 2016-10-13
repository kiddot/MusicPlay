package com.liangdekai.musicplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangdekai.musicplayer.R;
import com.liangdekai.musicplayer.bean.MenuItem;

import java.util.List;

public class SlideMenuAdapter extends RecyclerView.Adapter<SlideMenuAdapter.MenuItemHolder> {
    private List<MenuItem> mMenuItem ;

    public SlideMenuAdapter(List<MenuItem> menuItems){
        mMenuItem = menuItems ;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_head , parent , false) ;
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_main, parent , false) ;
        }
        MenuItemHolder menuItemHolder = new MenuItemHolder(view) ;
        return menuItemHolder;
    }

    @Override
    public void onBindViewHolder(MenuItemHolder holder, int position) {
        if (position == 0){
            holder.itemHead.setImageResource(R.mipmap.menu_head);
        }else {
            holder.itemImg.setImageResource(mMenuItem.get(position-1).getImageId());
            holder.textView.setText(mMenuItem.get(position-1).getMenuText());
        }
    }

    @Override
    public int getItemCount() {
        return mMenuItem.size()+1 ;
    }

    public class MenuItemHolder extends RecyclerView.ViewHolder{
        protected ImageView itemImg , itemHead ;
        protected TextView textView ;

        public MenuItemHolder(View itemView) {
            super(itemView);
            itemImg = (ImageView) itemView.findViewById(R.id.main_menu_image) ;
            itemHead = (ImageView) itemView.findViewById(R.id.main_item_head) ;
            textView = (TextView) itemView.findViewById(R.id.main_menu_text) ;
        }
    }
}
