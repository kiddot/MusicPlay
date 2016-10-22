package com.liangdekai.musicplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.liangdekai.musicplayer.net.bean.LrcInfo;
import com.liangdekai.musicplayer.net.bean.LrcLine;
import com.liangdekai.musicplayer.net.download.FileDownLoad;
import com.liangdekai.musicplayer.net.util.ParseLrc;

import java.io.File;

/**
 * Created by asus on 2016/10/19.
 */
public class LrcView extends View implements FileDownLoad.DownLoadedCallBack {
    private static final String PATH = Environment.getExternalStorageDirectory() + "/Lrc/";
    private static final String NO_LRC = "暂无歌词" ;
    private int mCurrentLine = 0 ;
    private int mLineSpace = 10 ;
    private int mLineHeight;
    private int mOtherTextSize = 45 ;
    private int mCurrentTextSize = 45 ;
    private int mMaxVelocity = 0;
    private int mLastPosition ;
    private int mDown = 0 ;
    private int mOffsetY;
    private int mLastOffsetY;
    private int mLastMotionDown;
    private float mFirst = getMeasuredWidth() / 2;
    private float mMove = 0;
    private float mShaderWidth = 300;  // 渐变过渡的距离
    private String mLrcHint = "努力搜索歌词....." ;
    private Scroller mScroller ;
    private FileDownLoad mFileDownLoad ;
    private VelocityTracker mVelocityTracker ;
    private Paint mOtherPaint;
    private Paint mCurrentPaint ;
    private LrcInfo mLrcInfo ;

    public LrcView(Context context) {
        super(context);
        init(context);
    }

    public LrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mOtherPaint = new Paint();
        mOtherPaint.setDither(true);
        mOtherPaint.setAntiAlias(true);
        mOtherPaint.setTextSize(mOtherTextSize);
        mOtherPaint.setColor(Color.parseColor("#ccffff"));
        mOtherPaint.setTextAlign(Paint.Align.CENTER);

        mCurrentPaint = new Paint();
        mCurrentPaint.setDither(true);
        mCurrentPaint.setAntiAlias(true);
        mCurrentPaint.setTextSize(mCurrentTextSize);
        mCurrentPaint.setColor(Color.parseColor("#FF4081"));
        mCurrentPaint.setTextAlign(Paint.Align.CENTER);

        mFileDownLoad = new FileDownLoad(this);
        mScroller = new Scroller(context);
        mMaxVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        measureLineHeight();
    }

    public boolean searchLrc(String url , String musicName){
        File file = new File(PATH +musicName +".lrc");
        if (file.exists()){
            Log.d("test", "文件存在");
            mLrcInfo = ParseLrc.getLrc(file , "UTF-8");
            Log.d("test" , "mLrcInfo"+mLrcInfo.lrcLines.size());
            invalidate();
            return true ;
        }else {
            mFileDownLoad.startDown(url , file);
            Log.d("test", "文件no存在");
            return false ;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLrcInfo == null){
            canvas.drawText(mLrcHint, getMeasuredWidth() / 2, getMeasuredHeight() / 2 , mOtherPaint);
            return;
        }
        mLastPosition = mLrcInfo.lrcLines.size() * mLineHeight ;
        for (int i = 0 ; i < mLrcInfo.lrcLines.size() ; i ++){
            float x = getMeasuredWidth() / 2;
            float y = getMeasuredHeight() / 2 + i * mLineHeight;
//            if(y < getMeasuredHeight() && y > getMeasuredHeight() - mShaderWidth || y < mShaderWidth) {
//                if(y < mShaderWidth) {
//                    mOtherPaint.setAlpha(26 + (int) (23000.0f * y / mShaderWidth * 0.01f));
//                } else {
//                    mOtherPaint.setAlpha(26 + (int) (23000.0f * (getMeasuredHeight() - y) / mShaderWidth * 0.01f));
//                }
//            } else {
//                mOtherPaint.setAlpha(255);
//            }
            if (i == mCurrentLine){
                canvas.drawText(mLrcInfo.lrcLines.get(mCurrentLine).getLineContent() , x, y ,mCurrentPaint);
                continue;
            }
            canvas.drawText(mLrcInfo.lrcLines.get(i).getLineContent() , x, y , mOtherPaint);
        }
    }

    private void measureLineHeight(){
        Rect lineBound = new Rect();
        mOtherPaint.getTextBounds(NO_LRC , 0 , 4 , lineBound);
        mLineHeight = lineBound.height() + mLineSpace ;
        Log.d("test" , "一行的高度"+mLineHeight);
    }

    public void scrollY(){
        //smoothScrollBy(0 , mLineHeight);
        smoothScrollTo(0 , mLineHeight * mCurrentLine);
        mOffsetY = mLineHeight * mCurrentLine ;
        mCurrentLine++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        MotionEvent motionEvent = MotionEvent.obtain(event);
        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                actionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                actionUp(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            default:
                break;
        }
        motionEvent.recycle();
        return true;
    }

    private void actionDown(MotionEvent event){
        mLastMotionDown = (int) event.getY();
        mLastOffsetY = mOffsetY ;
    }

    private void actionUp(MotionEvent event){
        if (mOffsetY < 0 ){
            mOffsetY = 0 ;
            smoothScrollTo(0 , 0);
        }else if(mOffsetY > mLastPosition){
            mOffsetY = mLastPosition;
            smoothScrollTo(0 , mLastPosition);
        }
    }

    /**
     * 手势移动执行事件
     * @param event
     * */
    private void actionMove(MotionEvent event) {
        final int y = (int) event.getY();
        mOffsetY = mLastMotionDown + mLastOffsetY - y;
        Log.d("test" , mOffsetY +"aaaaaaa" + mFirst);
        smoothScrollTo(0 , measureDamping(mOffsetY));
    }

    /**
     * 计算阻尼效果的大小
     * */
    private int measureDamping(int offsetY){
        if (offsetY < 0){
            mOffsetY = (int) (offsetY  * 0.4f);
            return mOffsetY;
        }else if(offsetY > mLastPosition){
            Log.d("test" , offsetY + "test" + mLastPosition);
            return mLastPosition ;
        }
        return offsetY;
    }

    /**
     * 判断当前View是否已经滑动到歌词的内容区域之外
     * */
    private boolean overScrolled() {
        return (mOffsetY > mLastPosition || mOffsetY < 0);
    }

    /**
     * 根据当前时间滑动到指定位置
     * @param time  时间
     * */
    public void scrollToCurrentTimeMillis(long time) {
        if (mLrcInfo != null){
            for(int i = mCurrentLine ; i < mLrcInfo.lrcLines.size(); i ++) {
                if(i == mLrcInfo.lrcLines.size() - 2) {
                    break;
                }
                LrcLine lrcStartLine = mLrcInfo.lrcLines.get(i+1);
                if(lrcStartLine != null && lrcStartLine.getStartTime() < time ) {
                    scrollY();
                    break;
                }
            }
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public void callBack(File file, String format) {
        if (file == null){
            mLrcHint = "暂无歌词" ;
        }else {
            mLrcInfo = ParseLrc.getLrc(file , format);
        }
        invalidate();
    }

    private void smoothScrollBy(int dstX, int dstY) {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dstX, dstY, 2000);
        invalidate();
    }

    //调用此方法滚动到目标位置
    private void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX() , mScroller.getCurrY());
            postInvalidate();
        }
    }

}
