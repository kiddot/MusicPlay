package com.liangdekai.musicplayer.net.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/22.
 */
public class LrcLink {
    /**
     * lrclink : http://musicdata.baidu.com/data2/lrc/1c662ad146cb1f0c954d07b67745380d/268223987/268223987.lrc
     * artist_480_800 : http://musicdata.baidu.com/data2/pic/105465128/105465128.jpg
     * avatar_s500 : http://musicdata.baidu.com/data2/pic/690c5a8b9ce24a6049d15c8ff9800429/246639970/246639970.jpg
     * album_id : 265217536
     * author : Ailee
     * artist_1000_1000 : http://musicdata.baidu.com/data2/pic/246639952/246639952.jpg
     * song_title : 因为是爱 (사랑이니까)
     * lrc_md5 :
     * avatar_s180 : http://musicdata.baidu.com/data2/pic/73798ceb0c92aefeab4148434e37f0f9/246639955/246639955.jpg
     * song_id : 265217392
     * artist_640_1136 : http://musicdata.baidu.com/data2/pic/105465106/105465106.jpg
     * pic_s1000 : http://musicdata.baidu.com/data2/pic/177fde7fd7502edba234f10ee824afda/265217538/265217538.jpg
     * pic_type : 2
     * pic_s180 : http://musicdata.baidu.com/data2/pic/f9be911207eaa65f869d31bfaea9c0ff/265217319/265217319.jpg
     * pic_s500 : http://musicdata.baidu.com/data2/pic/5df1bce921ac75cb8bebfa56ffd4e54e/265217539/265217539.jpg
     * title : 因为是爱 (사랑이니까)
     * artist_id : 18606255
     */

    private List<SonginfoBean> songinfo;

    public List<SonginfoBean> getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(List<SonginfoBean> songinfo) {
        this.songinfo = songinfo;
    }

    public static class SonginfoBean {
        private String lrclink;
        private String author;

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
