package com.liangdekai.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liangdekai.musicplayer.R;

public abstract class BaseLazyFragment extends Fragment {

    //时候加载过
    protected boolean mIsLoaded = false;
    //主视图
    protected View mMainView;

    //重写，每次切换页面判断是否要加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //可见，没有加载过，要装载数据的Layout不为空
        //（因为这个函数比onCreateView先运行，而Layout在CreateView的获取，所有必须判断是否为空）
        if (isVisibleToUser && !mIsLoaded && mMainView != null) {
            mIsLoaded = true;
            lazyLoad();
        }
    }

    //默认处理CreateView，加载一个“正在加载”的视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //因为第一个页面的onCreateView执行比setUserVisibleHint慢，所以这里要判断是否执行lazyLoad
        //加载主视图
        if (mMainView == null) {
            mMainView = inflater.inflate(R.layout.fragment_loading ,container , false);
        }
        if (getUserVisibleHint() && !mIsLoaded) {
            mIsLoaded = true;
            lazyLoad();
        }
        return mMainView;
    }

    //获取主视图的Layout
    protected ViewGroup getLayout() {
        if (mMainView != null) {
            return (ViewGroup) mMainView.findViewById(R.id.fragment_loading);
        } else {
            return null;
        }
    }

    //标记加载失败
    protected void setLoadFail() {
        mIsLoaded = false;
    }

    //懒加载交由之类处理
    abstract protected void lazyLoad();
}
