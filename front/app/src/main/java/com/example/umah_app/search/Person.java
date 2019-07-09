package com.example.umah_app.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.umah_app.R;
import com.example.umah_app.common.LocalDatas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Person extends AppCompatActivity {

    private Toolbar mTopToolbar;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText inputPerson = (EditText) findViewById(R.id.input_person);

        mSubmit = (Button) findViewById(R.id.btn_add_person);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDatas.setUserLeft(Integer.parseInt(inputPerson.getText().toString()));
                Intent i = new Intent(getApplicationContext(), FilterKriteria.class);
                startActivity(i);
            }
        });

        LocalDatas.setCurrentUser(1);
        LocalDatas.setUserLeft(1);
        LocalDatas.setMapFilter(new HashMap<Integer, Map<String, String>>());
        LocalDatas.setMapSorting(new HashMap<Integer, Map<String, String>>());
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalDatas.setCurrentUser(1);
        LocalDatas.setUserLeft(1);
        LocalDatas.setMapFilter(new HashMap<Integer, Map<String, String>>());
        LocalDatas.setMapSorting(new HashMap<Integer, Map<String, String>>());
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
