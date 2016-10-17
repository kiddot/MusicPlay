package com.liangdekai.musicplayer.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liangdekai.musicplayer.R;

/**
 * Created by asus on 2016/10/14.
 */
public class AlbumRoundFragment extends Fragment{
    private static final String ALBUM_URL = "albumPath";
    private ObjectAnimator mAlbumAnimator ;
    private String mAlbumUrl ;
    private ImageView mAlbum ;

    public static AlbumRoundFragment newInstance(String albumUrl){
        AlbumRoundFragment albumRoundFragment = new AlbumRoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_URL , albumUrl);
        albumRoundFragment.setArguments(bundle);
        return new AlbumRoundFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_play_round , container , false);
        initView(view);
        if (getArguments() != null){
            mAlbumUrl = getArguments().getString(ALBUM_URL);
        }
        loadAlbumImage(mAlbumUrl);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initAnimator(){
        mAlbumAnimator = ObjectAnimator.ofFloat(mAlbumAnimator , "rotation" , 0 , 360);
        mAlbumAnimator.setDuration(1024000);
        mAlbumAnimator.setRepeatCount(0);
        mAlbumAnimator.setInterpolator(new LinearInterpolator());
    }

    private void initView(View view){
        mAlbum = (ImageView) view.findViewById(R.id.fragment_play_album);
    }

    private void loadAlbumImage(String url){
        Glide.with(getActivity())
             .load(url)
             .placeholder(R.mipmap.fragment_play_album_defautl)
             .into(mAlbum);
    }

    @Override
    public void onStart() {
        super.onStart();
        initAnimator();
        //mAlbumAnimator.start();
    }
}
