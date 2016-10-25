package com.liangdekai.musicplayer.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by asus on 2016/10/24.
 */
public class DragViewLayout extends PopupWindow {
    private ViewDragHelper mViewDragHelper;
    private View mDragView ;

    public DragViewLayout(Context context) {
        this(context ,null);
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this ,1.0f , new DragHelperCallback());
    }


}
