package com.liangdekai.musicplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/9/2.
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout" ;
    private List<List<View>> mAllView = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 如果要自定义ViewGroup支持子控件的layout_margin参数，
     * 则自定义的ViewGroup类必须重载generateLayoutParams()函数，
     * 并且在该函数中返回一个ViewGroup.MarginLayoutParams派生类对象，
     * 这样才能使用margin参数。
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext() , attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT);
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    /**
     * 自定义ViewGroup的onMeasure()方法中，除了计算自身的尺寸外，
     * 还需要调用measureChildren()函数来计算子控件的尺寸
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.d(TAG , sizeWidth +" ," +sizeHeight);
        /**
         * 记录每一行的宽度和高度，width不断取最大宽度
         */
        int width = 0 ;
        int height = 0 ;
        /**
         * 每一行的高度和宽度，累加
         */
        int lineWidth = 0 ;
        int lineHeight = 0 ;

        int childCount = getChildCount();
        // 遍历每个子元素
        for (int i = 0 ; i < childCount ; i++){
            View child = getChildAt(i);
            measureChild(child , widthMeasureSpec , heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getHeight() + params.topMargin + params.bottomMargin ;

            if (lineWidth + childWidth > sizeWidth){
                width =Math.max(lineWidth , childWidth);
                lineWidth = childWidth ;
                height += lineHeight ;
                lineHeight = childHeight ;
            }else {
                lineWidth += childWidth ;
                lineHeight = Math.max(lineHeight , childHeight);
            }
            if (i == childCount - 1){
                width = Math.max(lineWidth , width);
                height += lineHeight ;
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width ,
                            (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
        Log.d(TAG , width+","+height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllView.clear();
        mLineHeight.clear();

        int width = getWidth() ;

        int lineWidth = 0 ;
        int lineHeight = 0 ;
        // 存储每一行所有的childView
        List<View> lineView = new ArrayList<>();
        int count = getChildCount();
        // 遍历所有的孩子
        for (int i = 0 ; i < count ; i++){
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + params.leftMargin + params.rightMargin +lineWidth > width){
                mLineHeight.add(lineHeight);// 记录这一行所有的View以及最大高度
                mAllView.add(lineView);// 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                lineWidth = 0 ;
                lineView = new ArrayList<>();
            }

            lineWidth += childWidth + params.leftMargin + params.rightMargin ;
            lineHeight = Math.max(lineHeight , childHeight + params.topMargin + params.bottomMargin);
            lineView.add(child) ;
        }

        mLineHeight.add(lineHeight);
        mAllView.add(lineView);

        int left = 0 ;
        int top = 0 ;

        int lineNum = mAllView.size();
        for (int i = 0 ; i < lineNum ; i++){
            lineView = mAllView.get(i);
            lineHeight = mLineHeight.get(i);

            Log.d(TAG, "第" + i + "行 ：" + lineView.size() + " , " + lineView);
            Log.d(TAG, "第" + i + "行， ：" + lineHeight);

            for (int j = 0 ; j < lineView.size() ; j++){
                View child = lineView.get(j);
                if (child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + params.leftMargin;
                int tc = top + params.topMargin  ;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
                        + rc + " , b = " + bc);

                child.layout(lc , tc , rc , bc);
                //如果我们在调用layout()的时候传进去的宽度值不与getMeasuredWidth()相同，那必然getMeasuredWidth()与getWidth()的值就不再一样了。

                left += child.getMeasuredWidth() + params.rightMargin + params.leftMargin ;
            }
            left = 0 ;
            top += lineHeight ;
        }
    }
}
