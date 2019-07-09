package com.example.umah_app.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.umah_app.R;
import com.example.umah_app.common.LocalDatas;
import com.example.umah_app.common.RequestHandler;
import com.example.umah_app.common.ResponseCallBack;
import com.example.umah_app.common.TopsisAPI;
import com.example.umah_app.common.URLs;
import com.example.umah_app.model.Rumah;
import com.example.umah_app.search.FilterKriteria;
import com.example.umah_app.search.SortKriteria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Output extends AppCompatActivity {

    private TopsisAPI topsisAPI;
    TextView rName, rHarga, rLokasi;
    ImageView rImage;
    private RecyclerView recyclerView;
    private TopsisAdapter adapter;
    private int bordaId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        // kill filter and sort activity
        SortKriteria.getInstance().finish();
        FilterKriteria.getInstance().finish();

        topsisAPI = new TopsisAPI(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new TopsisAdapter(this, new ArrayList<Rumah>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Output.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        rName = (TextView) findViewById(R.id.rName);
        rHarga = (TextView) findViewById(R.id.rHarga);
        rLokasi = (TextView) findViewById(R.id.rLokasi);
        rImage = (ImageView) findViewById(R.id.rImage);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar_output);
        setSupportActionBar(mTopToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Map<Integer, Map<String, String>> mapFilter = LocalDatas.getMapFilter();
        Map<Integer, Map<String, String>> mapSorting = LocalDatas.getMapSorting();

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        topsisAPI.hitung(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.hide();

                    // VIEW BORDA
                    if (response.getString("borda").equals("null")) {
                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        String stringBorda = response.getString("borda").equals("null") ? "{}" : response.getString("borda");
                        JSONObject borda = new JSONObject(stringBorda);
                        if (!borda.toString().equals("{}")) {
                            bordaId = borda.getInt("id");
                            rName.setText(borda.getString("nama"));
                            rHarga.setText(borda.getString("harga"));
                            JSONObject bordaLokasi = borda.getJSONObject("att_kota");
                            rLokasi.setText(bordaLokasi.getString("name"));
                        }

                        JSONArray bordaImages = borda.getJSONArray("images");
                        JSONObject bordaImage = new JSONObject(bordaImages.get(0).toString());

                        // Retrieves an image specified by the URL, displays it in the UI.
                        ImageRequest request = new ImageRequest(URLs.URL_SERVER + "rumah-images/" + bordaImage.getString("file"),
                                new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        rImage.setImageBitmap(bitmap);
                                    }
                                }, 0, 0, null,
                                new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Response image: " + error, Toast.LENGTH_LONG).show();
                                    }
                                });
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);


                        // VIEW TOPSIS
                        JSONObject topsis = new JSONObject(response.getString("topsis"));
                        Iterator<String> keys = topsis.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            if (topsis.get(key) instanceof JSONObject) {
                                final JSONObject rumahUser = new JSONObject(topsis.get(key).toString());
                                final Rumah rumah = new Rumah();
                                rumah.setPengguna(key.toString());
                                rumah.setId(rumahUser.getInt("id"));
                                rumah.setNama(rumahUser.getString("nama"));
                                rumah.setHarga(rumahUser.getString("harga"));

                                JSONObject lokasi = borda.getJSONObject("att_kota");
                                rumah.setKota(lokasi.getString("name"));
                                JSONArray images = rumahUser.getJSONArray("images");
                                JSONObject image = new JSONObject(images.get(0).toString());

                                // Retrieves an image specified by the URL, displays it in the UI.
                                ImageRequest imageRequest = new ImageRequest(URLs.URL_SERVER + "rumah-images/" + image.getString("file"),
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                rumah.setImage(bitmap);
                                                adapter.add(rumah);
                                            }
                                        }, 0, 0, null,
                                        new Response.ErrorListener() {
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getApplicationContext(), "Response image: " + error, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.hide();
                }
            }

            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        }, mapFilter, mapSorting);
    }

    public void goToDetail(View view) {
        Intent detail = new Intent(getApplicationContext(), DetailPage.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Id", bordaId);
        detail.putExtras(bundle);
        this.startActivity(detail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
