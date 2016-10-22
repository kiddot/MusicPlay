package com.liangdekai.musicplayer.net.util;

import android.util.Log;


import com.liangdekai.musicplayer.net.bean.LrcInfo;
import com.liangdekai.musicplayer.net.bean.LrcLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by asus on 2016/10/19.
 */
public class ParseLrc {

    public static LrcInfo getLrc(File file , String charsetName){
        LrcInfo lrcInfo = new LrcInfo();
        lrcInfo.lrcLines = new ArrayList<>();
        if (file.exists() && !file.isDirectory()){
            InputStreamReader inputStreamReader ;
            BufferedReader bufferedReader ;
            try{
                inputStreamReader = new InputStreamReader(new FileInputStream(file), charsetName);
                bufferedReader = new BufferedReader(inputStreamReader);
                String line ;
                while((line = bufferedReader.readLine()) != null){
                    parseLrc(lrcInfo , line);
                }
            }catch (IOException e){
                Log.d("test" , "出现IO异常");
            }
        }
        return lrcInfo;
    }

    public static void parseLrc(LrcInfo lrcInfo , String line){
        Log.d("test" , line);
        if (line.startsWith("[ti:")){
            return;
        }
        if (line.startsWith("[ar:")){
            return;
        }
        if (line.startsWith("[offset:")){
            return;
        }
        if (line != null && line.startsWith("[") && line.lastIndexOf("]") == 9){
            LrcLine lrcLine = new LrcLine();
            lrcLine.setLineContent(line.substring(10 , line.length()).trim());
            lrcLine.setStartTime(transformTime(line.substring(0 ,10).trim()));
            lrcInfo.lrcLines.add(lrcLine);
        }
    }

    public static long transformTime(String time){
        long minute = Long.parseLong(time.substring(1, 3));
        long second = Long.parseLong(time.substring(4, 6));
        long millisecond = Long.parseLong(time.substring(7, 9));
        return millisecond + second * 1000 + minute * 60 * 1000;
    }
}
