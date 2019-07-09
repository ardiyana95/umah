package com.example.umah_app.result;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.umah_app.R;
import com.example.umah_app.common.RequestHandler;
import com.example.umah_app.common.TopsisAPI;
import com.example.umah_app.common.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetailPage extends AppCompatActivity {

    private TopsisAPI topsisAPI;
    private Integer id;

    TextView namaRumah, lokasiRumah, provinsiRumah, hargaRumah, alamatRumah, zipcodeRumah, lingkunganRumah, transportasiRumah, infrastrukturRumah, tipeRumahDetail, desainRumah, fasiltasRumah, rencanaRumah, kesiapanRumah;
    ImageView imgRumah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        topsisAPI = new TopsisAPI(this);

        namaRumah = (TextView) findViewById(R.id.name_rumah);
        lokasiRumah = (TextView) findViewById(R.id.lokasi_rumah);
        lingkunganRumah = (TextView) findViewById(R.id.ligkungan_rumah);
        hargaRumah = (TextView) findViewById(R.id.harga_rumah);
        transportasiRumah = (TextView) findViewById(R.id.transportasi_rumah);
        rencanaRumah = (TextView) findViewById(R.id.rencana_rumah);
        fasiltasRumah = (TextView) findViewById(R.id.fasilitas_rumah);
        tipeRumahDetail = (TextView) findViewById(R.id.tipe_rumah_detail);
        infrastrukturRumah = (TextView) findViewById(R.id.infrastruktur_rumah);
        desainRumah = (TextView) findViewById(R.id.desain_rumah);
        kesiapanRumah = (TextView) findViewById(R.id.kesiapan_rumah);
        provinsiRumah = (TextView) findViewById(R.id.provinsi_rumah);
        alamatRumah = (TextView) findViewById(R.id.alamat_rumah);
        zipcodeRumah = (TextView) findViewById(R.id.zipcode_rumah);
        imgRumah = (ImageView) findViewById(R.id.img_rumah);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(mTopToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLs.URL_RUMAH + id,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Detail", response.toString());
                try {
                    if (!response.toString().equals("{}")) {
//                        JSONObject jsonObject = response.getJSONObject()
                        String nama = response.getString("nama");
                        String harga = response.getString("harga");
                        String provinsi = response.getString("provinsi");
                        String zipcode = response.getString("zipcode");
                        String alamat = response.getString("alamat");

                         //ini cara ambil LOKASI / KOTA
                        JSONObject lokasi = response.getJSONObject("att_kota");
                        lokasiRumah.setText(lokasi.getString("name"));

                        JSONObject kesiapan = response.getJSONObject("att_kesiapan_ditempati");
                        kesiapanRumah.setText(kesiapan.getString("name"));

                        JSONObject lingkungan = response.getJSONObject("att_lingkungan");
                        lingkunganRumah.setText(lingkungan.getString("name"));

                        JSONObject transportasi = response.getJSONObject("att_akses_transportasi_publik");
                        transportasiRumah.setText(transportasi.getString("name"));

                        JSONObject infras = response.getJSONObject("att_infrastruktur_dan_fasilitas_property");
                        infrastrukturRumah.setText(infras.getString("name"));

                        JSONObject desain = response.getJSONObject("att_desain_dan_konstruksi");
                        desainRumah.setText(desain.getString("name"));

                        JSONObject fasilitas = response.getJSONObject("att_fasilitas_lingkungan_property");
                        fasiltasRumah.setText(fasilitas.getString("name"));

                        JSONObject rencana = response.getJSONObject("att_pengembangan_area");
                        rencanaRumah.setText(rencana.getString("name"));

                        JSONObject tipRumah = response.getJSONObject("tipe_rumah");
                        tipeRumahDetail.setText(tipRumah.getString("tipe"));

                        namaRumah.setText(nama);
                        provinsiRumah.setText(provinsi);
                        hargaRumah.setText(harga);
                        alamatRumah.setText(alamat);
                        zipcodeRumah.setText(zipcode);

                        JSONArray images = response.getJSONArray("images");
                        JSONObject image = new JSONObject(images.get(0).toString());
                        // Retrieves an image specified by the URL, displays it in the UI.
                        ImageRequest request = new ImageRequest(URLs.URL_SERVER + "rumah-images/" + image.getString("file"),
                                new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        imgRumah.setImageBitmap(bitmap);
                                    }
                                }, 0, 0, null,
                                new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Response image: " + error, Toast.LENGTH_LONG).show();
                                    }
                                });
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                    }
                    progressDialog.hide();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Response image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(req);
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
