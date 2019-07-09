package com.example.umah_app.model;

import android.content.Context;

import com.example.umah_app.common.BaseAPI;
import com.example.umah_app.common.ResponseCallBack;
import com.example.umah_app.common.URLs;

public class TiperRumahAPI extends BaseAPI<TipeRumah> {

    public TiperRumahAPI(Context context) {
        super(context);
    }



    @Override
    public String GetUrl() {
        return URLs.URL_TIPERUMAH;
    }
}
