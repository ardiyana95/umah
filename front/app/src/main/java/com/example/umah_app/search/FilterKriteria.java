package com.example.umah_app.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umah_app.R;
import com.example.umah_app.common.LocalDatas;
import com.example.umah_app.common.ResponseCallBack;
import com.example.umah_app.model.AttributeAPI;
import com.example.umah_app.model.TiperRumahAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FilterKriteria extends AppCompatActivity  {

    private String lokasiId, lingkunganId, transportasiId, infrasId, desainId, fasilitasId, rencanaId, kesiapanId, tipeRumahId, hargaId;
    private static Activity activity;

    EditText minHarga, maxHarga;
    MaterialSpinner spnLokasi, spnLingkungan, spnTransportasi, spnInfras, spnDesain, spnFasilitas, spnRencana, spnKesiapan, spnTipeRumah;
    List<String> listItemsLokasi = new ArrayList<>();
    List<String> listItemsLingkungan = new ArrayList<>();
    List<String> listItemsAksesTranportasi = new ArrayList<>();
    List<String> listItemsInfrastruktur = new ArrayList<>();
    List<String> listItemsDesain = new ArrayList<>();
    List<String> listItemsFasilitas = new ArrayList<>();
    List<String> listItemsRencana = new ArrayList<>();
    List<String> listItemsKesiapan = new ArrayList<>();
    List<String> listItemsTipeRumah = new ArrayList<>();
    ArrayAdapter<String> adapterLokasi, adapterLingkungan, adapterTranportasi, adapterInfras, adapterDesain, adapterFasilitas, adapterRenacan, adapterKesiapan, adapterTipeRumah;

    final Map<String, Integer> mapLokasi = new HashMap<>();
    final Map<String, Integer> mapLingkungan = new HashMap<>();
    final Map<String, Integer> mapAksesTransportasi = new HashMap<>();
    final Map<String, Integer> mapInfrastruktur = new HashMap<>();
    final Map<String, Integer> mapDesain = new HashMap<>();
    final Map<String, Integer> mapFasilitas = new HashMap<>();
    final Map<String, Integer> mapRencana = new HashMap<>();
    final Map<String, Integer> mapKesiapan = new HashMap<>();
    final Map<String, Integer> mapTipeRumah = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        activity = this;

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(mTopToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnLokasi = (MaterialSpinner) findViewById(R.id.lokasi);
        spnLingkungan = (MaterialSpinner) findViewById(R.id.lingkungan);
        spnTransportasi = (MaterialSpinner) findViewById(R.id.transportasi);
        spnInfras = (MaterialSpinner) findViewById(R.id.infrastruktur);
        spnDesain = (MaterialSpinner) findViewById(R.id.desain);
        spnRencana = (MaterialSpinner) findViewById(R.id.rencana);
        spnKesiapan = (MaterialSpinner) findViewById(R.id.kesiapan);
        spnFasilitas = (MaterialSpinner) findViewById(R.id.fasilitas);
        spnTipeRumah = (MaterialSpinner) findViewById(R.id.tipeRumah);
        minHarga = (EditText) findViewById(R.id.min_harga);
        maxHarga = (EditText) findViewById(R.id.max_harga);

        final TextView currentUser = (TextView) findViewById(R.id.currentUser);
        currentUser.setText(LocalDatas.getCurrentUser().toString());

        Button mBtnSaveKriteria = (Button) findViewById(R.id.btn_save_kriteria);
        mBtnSaveKriteria.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseSparseArrays")
            @Override
            public void onClick(View v) {

                Map<String, String> mapFilters = new HashMap<>();
                mapFilters.put("lokasi", lokasiId);
                mapFilters.put("lingkungan", lingkunganId);
                mapFilters.put("transportasi", transportasiId);
                mapFilters.put("infrastruktur", infrasId);
                mapFilters.put("desain", desainId);
                mapFilters.put("rencana", rencanaId);
                mapFilters.put("kesiapan", kesiapanId);
                mapFilters.put("fasilitas", fasilitasId);
                mapFilters.put("tipeRumah", tipeRumahId);
                mapFilters.put("minHarga", minHarga.getText().toString());
                mapFilters.put("maxHarga", maxHarga.getText().toString());

                LocalDatas.addMapFilter(LocalDatas.getCurrentUser(), mapFilters);

                Intent i = new Intent(getApplicationContext(), SortKriteria.class);
                startActivity(i);
            }
        });

        final AttributeAPI attributeAPI = new AttributeAPI(this);
        TiperRumahAPI tiperRumahAPI = new TiperRumahAPI(this);

        adapterLokasi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsLokasi);
        adapterLokasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLokasi.setAdapter(adapterLokasi);
        // Attribute Lokasi
        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapLokasi.put(name, id);
                        listItemsLokasi.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "kota");

        spnLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsLokasi.get(position);
                    lokasiId =  mapLokasi.get(listItemsLokasi.get(position)).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Lingkungan
        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapLingkungan.put(name, id);
                        listItemsLingkungan.add(name);


                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "lingkungan");

        adapterLingkungan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsLingkungan);
        adapterLingkungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLingkungan.setAdapter(adapterLingkungan);
        spnLingkungan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsLingkungan.get(position);
                    lingkunganId =  mapLingkungan.get(listItemsLingkungan.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Transportasi

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapAksesTransportasi.put(name, id);
                        listItemsAksesTranportasi.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Akses Transportasi Publik");

        adapterTranportasi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsAksesTranportasi);
        adapterTranportasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTransportasi.setAdapter(adapterTranportasi);
        spnTransportasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsAksesTranportasi.get(position);
                    transportasiId =  mapAksesTransportasi.get(listItemsAksesTranportasi.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Infrastruktur

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapInfrastruktur.put(name, id);
                        listItemsInfrastruktur.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Infrastruktur dan Fasilitas Sekitar");

        adapterInfras = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsInfrastruktur);
        adapterInfras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnInfras.setAdapter(adapterInfras);
        spnInfras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
//                    int selected = Integer.parseInt(spnLokasi.getItemAtPosition(position).toString());
//                    if (selected % 2 == 0) {
//                        spnLokasi.setError("This is error message");
//                    }
                    infrasId =  mapInfrastruktur.get(listItemsInfrastruktur.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Desain

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapDesain.put(name, id);
                        listItemsDesain.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Desain dan Konstruksi");

        adapterDesain = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsDesain);
        adapterDesain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDesain.setAdapter(adapterDesain);
        spnDesain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsDesain.get(position);
                    desainId =  mapDesain.get(listItemsDesain.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Fasilitas

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapFasilitas.put(name, id);
                        listItemsFasilitas.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Fasilitas dalam lingkungan properti");

        adapterFasilitas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsFasilitas);
        adapterFasilitas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFasilitas.setAdapter(adapterFasilitas);
        spnFasilitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsFasilitas.get(position);
                    fasilitasId =  mapFasilitas.get(listItemsFasilitas.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Rencana Pengembangan Area

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapRencana.put(name, id);
                        listItemsRencana.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Rencana Pengembangan Area");

        adapterRenacan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsRencana);
        adapterRenacan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRencana.setAdapter(adapterRenacan);
        spnRencana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsRencana.get(position);
                    rencanaId =  mapRencana.get(listItemsRencana.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Attribute Kesiapan

        attributeAPI.getAttributeByKriteriaName(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        Integer value = jsonObject.getInt("value");
                        String name = jsonObject.getString("name");
                        Integer kriteria_id = jsonObject.getInt("kriteria_id");

                        mapKesiapan.put(name, id);
                        listItemsKesiapan.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        }, "Kesiapan untuk ditempati");

        adapterKesiapan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsKesiapan);
        adapterKesiapan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKesiapan.setAdapter(adapterKesiapan);
        spnKesiapan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsKesiapan.get(position);
                    kesiapanId =  mapKesiapan.get(listItemsKesiapan.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Tipe Rumah

        tiperRumahAPI.GetAll(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Integer id = jsonObject.getInt("id");
                        String tipe = jsonObject.getString("tipe");

                        mapTipeRumah.put(tipe, id);
                        listItemsTipeRumah.add(tipe);

                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
            }
        });

        adapterTipeRumah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItemsTipeRumah);
        adapterTipeRumah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipeRumah.setAdapter(adapterTipeRumah);
        spnTipeRumah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    String att = listItemsTipeRumah.get(position);
                    tipeRumahId =  mapTipeRumah.get(listItemsTipeRumah.get(position)).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static Activity getInstance() {
        return activity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), Person.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
