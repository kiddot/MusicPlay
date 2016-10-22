package com.liangdekai.musicplayer.net.util;

import com.google.gson.Gson;
import com.liangdekai.musicplayer.net.bean.LrcLink;

import org.json.JSONStringer;

import java.util.List;

/**
 * Created by asus on 2016/10/22.
 */
public class ParseResponse {

    public static String parseLrcResponse(String result){
        Gson gson = new Gson();
        LrcLink lrcLink = gson.fromJson(result , LrcLink.class);
        List<LrcLink.SonginfoBean> list = lrcLink.getSonginfo();
        String link  = null;
        if (list != null){
            link = list.get(0).getLrclink();
        }
        return link;
    }
}
