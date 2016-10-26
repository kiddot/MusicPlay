package com.liangdekai.musicplayer.manager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1 ;
    public static final int TASK_COME = 0;
    private static ThreadPoolManager mThreadPoolManager;
    private ExecutorService mFixedThreadPool;
    private Handler mThreadHandler;
    private LinkedList<Runnable> mTaskList ;

    public static ThreadPoolManager getInstance(){
        if (mThreadPoolManager == null){
            synchronized (ThreadPoolManager.class){
                if (mThreadPoolManager == null){
                    mThreadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return mThreadPoolManager;
    }

    public ThreadPoolManager() {
        init();
    }

    /**
     * 初始化实例，创建线程池，启动线程
     */
    private void init(){
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        mThreadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (!mFixedThreadPool.isShutdown()){
                    mFixedThreadPool.execute(getLoadTask());
                }
            }
        };
        mFixedThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        mTaskList = new LinkedList<>();
    }

    /**
     * 从任务队列中获取任务
     * @return
     */
    public Runnable getLoadTask(){
        return mTaskList.removeLast();
    }

    /**
     * 添加任务
     * @param loadTask
     */
    public void addLoadTask(Runnable loadTask){
        mTaskList.add(loadTask);
        mThreadHandler.sendEmptyMessage(TASK_COME);//发送消息通知执行任务
    }
}
