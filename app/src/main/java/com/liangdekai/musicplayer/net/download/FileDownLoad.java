package com.liangdekai.musicplayer.net.download;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by asus on 2016/10/19.
 */
public class FileDownLoad {
    private DownLoadedCallBack mCallBack ;

    public FileDownLoad(DownLoadedCallBack callBack) {
        mCallBack = callBack ;
    }

    public void startDown(String url , File file){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(url, file.getAbsolutePath(), true, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                mCallBack.callBack(responseInfo.result , "GBK");
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d("test" , "下载失败 " + s);
            }
        });
    }

    public interface DownLoadedCallBack{
        void callBack(File file, String format);
    }
}
