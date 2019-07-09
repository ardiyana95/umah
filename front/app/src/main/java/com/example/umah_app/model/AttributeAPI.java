package com.example.umah_app.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.umah_app.common.BaseAPI;
import com.example.umah_app.common.RequestHandler;
import com.example.umah_app.common.ResponseCallBack;
import com.example.umah_app.common.URLs;

import org.json.JSONArray;

import java.util.List;

public class AttributeAPI extends BaseAPI<Attribute> {

    public AttributeAPI(Context context) {
        super(context);
    }

    public void getAttributeByKriteriaName(final ResponseCallBack responseCallBack, String name) {
        JsonArrayRequest request = new JsonArrayRequest(url + "search/" + name,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallBack.onResponse(response);
                        Log.d(tag, "Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }


    @Override
    public String GetUrl() {
        return URLs.URL_ATTRIBUTE;
    }
}
