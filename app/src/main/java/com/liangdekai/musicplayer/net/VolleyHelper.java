package com.liangdekai.musicplayer.net;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.liangdekai.musicplayer.MusicApplication;

import org.json.JSONObject;

/**
 * Created by asus on 2016/10/22.
 */
public class VolleyHelper {

    public interface VolleyListener{
        void onSucceed(String jsonString);
        void onFailed();
    }

    public static void sendByVolley(String address , final VolleyListener listener){
        RequestQueue requestQueue = MusicApplication.getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(address, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("jsonString" , jsonObject.toString());
                        listener.onSucceed(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onFailed();
            }
        });
        requestQueue.add(jsonObjectRequest) ;
    }
}
