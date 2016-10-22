package com.liangdekai.musicplayer.net.bean;

import java.util.List;

/**
 * Created by asus on 2016/10/18.
 */
public class HotWord {

    /**
     * error_code : 22000
     * result : [{"strong":1,"linktype":0,"linkurl":"","word":"TFBOYS"},{"strong":0,"linktype":0,"linkurl":"","word":"张杰"},{"strong":0,"linktype":0,"linkurl":"","word":"薛之谦"},{"strong":0,"linktype":0,"linkurl":"","word":"从你的全世界路过"},{"strong":0,"linktype":0,"linkurl":"","word":"汪峰"},{"strong":0,"linktype":0,"linkurl":"","word":"歌在飞"},{"strong":0,"linktype":0,"linkurl":"","word":"儿歌"},{"strong":0,"linktype":0,"linkurl":"","word":"筷子兄弟《铁拳》"},{"strong":0,"linktype":0,"linkurl":"","word":"梁静茹《呵护》"},{"strong":0,"linktype":0,"linkurl":"","word":"歌在飞-苏勒亚其其格"}]
     */

    private int error_code;
    /**
     * strong : 1
     * linktype : 0
     * linkurl :
     * word : TFBOYS
     */

    private List<ResultBean> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String word;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
