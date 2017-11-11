package com.electiva.envido32.appsaenzpena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import java.util.ArrayList;

public class EscrutineoActivity extends AppCompatActivity {

    public ArrayList<CandidatoClass> datos_candidatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escrutineo);

        RecyclerView rv = (RecyclerView)findViewById(R.id.RecView);
        rv.setHasFixedSize(true);
        /*
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);
        */
        AdaptadorEscrutineo adapter = new AdaptadorEscrutineo(getBaseContext(), datos_candidatos);
        rv.setAdapter(adapter);

    }

    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}