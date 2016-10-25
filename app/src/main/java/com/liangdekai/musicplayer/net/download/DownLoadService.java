package com.liangdekai.musicplayer.net.download;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

import de.greenrobot.event.EventBus;

/**
 * 负责下载歌词或者音乐文件
 */
public class DownLoadService extends IntentService {
    private static final String URL = "url";
    private static final String PATH_ = "path";
    private static final String FILE_NAME = "fileName";
    private static final String IS_LRC_DOWN = "lrc";

    public DownLoadService() {
        super("download");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(URL);
        String path = intent.getStringExtra(PATH_);
        String fileName = intent.getStringExtra(FILE_NAME);
        boolean lrc = intent.getBooleanExtra(IS_LRC_DOWN , false);
        if (lrc){
            HttpUtils httpUtils = new HttpUtils();
            if (url != null){
                httpUtils.download(url, path + fileName + ".lrc", true, true, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        Log.d("test" ,"downloaded" );
                        EventBus.getDefault().post(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.d("test" , "下载失败 " + s);
                    }
                });
            }
        }else{
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDestinationInExternalPublicDir(path , fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setVisibleInDownloadsUi(true);
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
        }
    }
}
