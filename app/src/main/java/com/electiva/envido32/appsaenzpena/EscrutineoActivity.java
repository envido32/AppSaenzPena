package com.electiva.envido32.appsaenzpena;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class EscrutineoActivity extends AppCompatActivity {

    public ArrayList<CandidatoClass> datos_candidatos = new ArrayList<>();
    public AdaptadorEscrutineo adaptador;
    public  RecyclerView rv;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;
    public Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escrutineo);

        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.padron);
        setSupportActionBar(myToolbar);


        rv = (RecyclerView)findViewById(R.id.RecView);
        rv.setHasFixedSize(true);
        /*
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);
        */
        adaptador = new AdaptadorEscrutineo(getBaseContext(), datos_candidatos);
        rv.setAdapter(adaptador);

        refreshView();


    }

    public void refreshView() {
        //adaptador.clear();
        datos_candidatos.clear();

        //Abrimos la base de datos en modo read only
        dbVotacionHelper = new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);
        dbVotacion = dbVotacionHelper.getReadableDatabase();

        //Si abrio correctamente la base de datos la cargo en el array
        if (dbVotacion != null) {
            String[] campos = new String[]{"lista", "partido", "nombre"};

            Cursor dbVotacionCursor = dbVotacion.query("Candidatos", campos, null, null, null, null, null);
            //dbVotacionCursor = null; // DEBUG
            //Nos aseguramos de que existe al menos un registro
            if (dbVotacionCursor.moveToFirst()) {
                do {

                    int lista = dbVotacionCursor.getInt(0);
                    String partido = dbVotacionCursor.getString(1);
                    String nombre = dbVotacionCursor.getString(2);

                    CandidatoClass addCandidato =
                            new CandidatoClass(lista, partido, nombre);
                    datos_candidatos.add(addCandidato);

                } while (dbVotacionCursor.moveToNext());
            }
            adaptador.notifyDataSetChanged();
            dbVotacion.close();
        } else {
            Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_LONG).show();
        }
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