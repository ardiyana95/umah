package com.example.umah_app.search;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.umah_app.R;
import com.example.umah_app.common.LocalDatas;
import com.example.umah_app.result.Output;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SortKriteria extends AppCompatActivity {

    MaterialSpinner spnLokasi, spnLingkungan, spnTransportasi, spnInfras, spnHarga, spnDesain, spnFasilitas, spnRencana, spnKesiapan, spnLuasBangungan;
    private String bobotLokasi, bobotLingkungan, bobotTransportasi, bobotInfras, bobotDesain, bobot_fasilitas, bobotRencana, bobotKesiapan, bobotLuasBangunan, bobotHarga;
    private static Activity activity;

    List<String> listBobot = new ArrayList<>();
    ArrayAdapter<String> adapterLokasi, adapterLingkungan, adapterTranportasi, adapterInfras, adapterDesain, adapterFasilitas, adapterRenacan, adapterKesiapan, adapterTipeRumah, adapterHarga;

    String selectedBobotLokasi, selectedBobotLingkungan, selectedBobotTransportasi, selectedBobotInfras, selectedBobotDesain,
            selectedBobot_fasilitas, selectedBobotRencana, selectedBobotKesiapan, selectedBobotLuasBangunan, selectedBobotHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_kriteria);
        activity = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Urutkan kriteria yang menurut anda paling penting")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(mTopToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnLokasi = (MaterialSpinner) findViewById(R.id.sort_lokasi);
        spnLingkungan = (MaterialSpinner) findViewById(R.id.sort_lingkungan);
        spnTransportasi = (MaterialSpinner) findViewById(R.id.sort_transportasi);
        spnInfras = (MaterialSpinner) findViewById(R.id.sort_infras);
        spnDesain = (MaterialSpinner) findViewById(R.id.sort_desain);
        spnRencana = (MaterialSpinner) findViewById(R.id.sort_rencana);
        spnKesiapan = (MaterialSpinner) findViewById(R.id.sort_kesiapan);
        spnFasilitas = (MaterialSpinner) findViewById(R.id.sort_fasilitas);
        spnLuasBangungan = (MaterialSpinner) findViewById(R.id.sort_luasbangungan);
        spnHarga = (MaterialSpinner) findViewById(R.id.sort_harga);

        final TextView currentUserSort = (TextView) findViewById(R.id.currentUserSort);
        currentUserSort.setText(LocalDatas.getCurrentUser().toString());

        // load data bobot
        initBobot();

        Button btnSubmitSort = (Button) findViewById(R.id.btn_save_sort);
        btnSubmitSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> mapSort = new HashMap<>();
                mapSort.put("bobot_lokasi", bobotLokasi);
                mapSort.put("bobot_lingkungan", bobotLingkungan);
                mapSort.put("bobot_transportasi", bobotTransportasi);
                mapSort.put("bobot_infrastruktur", bobotInfras);
                mapSort.put("bobot_desain", bobotDesain);
                mapSort.put("bobot_rencana", bobotRencana);
                mapSort.put("bobot_kesiapan", bobotKesiapan);
                mapSort.put("bobot_fasilitas", bobot_fasilitas);
                mapSort.put("bobot_luas_bangunan", bobotLuasBangunan);
                mapSort.put("bobot_harga", bobotHarga);

                LocalDatas.addMapSorting(LocalDatas.getCurrentUser(), mapSort);

                LocalDatas.setCurrentUser(LocalDatas.getCurrentUser() + 1);
                LocalDatas.setUserLeft(LocalDatas.getUserLeft() - 1);

                if (LocalDatas.getUserLeft() == 0) {
                    startActivity(new Intent(getApplicationContext(), Output.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), FilterKriteria.class));
                }
            }
        });

        adapterLokasi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterLokasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLokasi.setAdapter(adapterLokasi);
        spnLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotLokasi != null) {
                        listBobot.add(selectedBobotLokasi);
                    }

                    bobotLokasi = listBobot.get(position);
                    selectedBobotLokasi = bobotLokasi;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterLingkungan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterLingkungan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLingkungan.setAdapter(adapterLingkungan);
        spnLingkungan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {

                    if (selectedBobotLingkungan != null) {
                        listBobot.add(selectedBobotLingkungan);
                    }

                    bobotLingkungan = listBobot.get(position);
                    selectedBobotLingkungan = bobotLokasi;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterTranportasi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterTranportasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTransportasi.setAdapter(adapterTranportasi);
        spnTransportasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotTransportasi != null) {
                        listBobot.add(selectedBobotTransportasi);
                    }

                    bobotTransportasi = listBobot.get(position);
                    selectedBobotTransportasi = bobotTransportasi;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapterInfras = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterInfras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnInfras.setAdapter(adapterInfras);
        spnInfras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotInfras != null) {
                        listBobot.add(selectedBobotInfras);
                    }

                    bobotInfras = listBobot.get(position);
                    selectedBobotInfras = bobotInfras;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterDesain = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterDesain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDesain.setAdapter(adapterDesain);
        spnDesain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotDesain != null) {
                        listBobot.add(selectedBobotDesain);
                    }

                    bobotDesain = listBobot.get(position);
                    selectedBobotDesain = bobotDesain;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterFasilitas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterFasilitas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFasilitas.setAdapter(adapterFasilitas);
        spnFasilitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobot_fasilitas != null) {
                        listBobot.add(selectedBobot_fasilitas);
                    }

                    bobot_fasilitas = listBobot.get(position);
                    selectedBobot_fasilitas = bobot_fasilitas;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterRenacan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterRenacan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRencana.setAdapter(adapterRenacan);
        spnRencana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotRencana != null) {
                        listBobot.add(selectedBobotRencana);
                    }

                    bobotRencana = listBobot.get(position);
                    selectedBobotRencana = bobotRencana;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterKesiapan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterKesiapan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKesiapan.setAdapter(adapterKesiapan);
        spnKesiapan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotKesiapan != null) {
                        listBobot.add(selectedBobotKesiapan);
                    }

                    bobotKesiapan = listBobot.get(position);
                    selectedBobotKesiapan = bobotKesiapan;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterTipeRumah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterTipeRumah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLuasBangungan.setAdapter(adapterTipeRumah);
        spnLuasBangungan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotLuasBangunan != null) {
                        listBobot.add(selectedBobotLuasBangunan);
                    }

                    bobotLuasBangunan = listBobot.get(position);
                    selectedBobotLuasBangunan = bobotLuasBangunan;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterHarga = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listBobot);
        adapterHarga.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHarga.setAdapter(adapterHarga);
        spnHarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) //01 = hint
                {
                    if (selectedBobotHarga != null) {
                        listBobot.add(selectedBobotHarga);
                    }

                    bobotHarga = listBobot.get(position);
                    selectedBobotHarga = bobotHarga;
                    listBobot.remove(position);

                    listBobot.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(Integer.valueOf(o1), Integer.valueOf(o2));
                        }
                    });
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
            startActivity(new Intent(getApplicationContext(), FilterKriteria.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void initBobot() {
        listBobot.add("1");
        listBobot.add("2");
        listBobot.add("3");
        listBobot.add("4");
        listBobot.add("5");
        listBobot.add("6");
        listBobot.add("7");
        listBobot.add("8");
        listBobot.add("9");
        listBobot.add("10");
    }
}
