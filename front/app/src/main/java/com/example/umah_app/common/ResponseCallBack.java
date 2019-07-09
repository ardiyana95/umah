package com.example.umah_app.common;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yogi on 04/11/2017.
 */

public interface ResponseCallBack {
    void onResponse(JSONArray response);
    void onResponse(JSONObject response);
    void onResponse(String response);
    void onError(String error);
}
