package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class EscrutineoActivity extends AppCompatActivity {

    public ArrayList<CandidatoClass> datos_candidatos = new ArrayList<>();
    public AdaptadorEscrutineo adaptador;
    public RecyclerView rv;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;
    public Toolbar myToolbar;
    public SharedPreferences config;
    public SharedPreferences prefs;
    public String darkTheme;
    public int usrType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        usrType = prefs.getInt("usrType", 0);
        config = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = config.getString("opcTheme", darkTheme);
        if (darkTheme.toString().equals("DARK")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_escrutineo);

        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.padron);
        setSupportActionBar(myToolbar);

        rv = (RecyclerView)findViewById(R.id.RecView);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        adaptador = new AdaptadorEscrutineo(datos_candidatos);
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

    @Override
    public void onResume(){
        super.onResume();
        refreshView();
        config = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = config.getString("opcTheme", darkTheme);
        if (darkTheme.toString().equals("DARK")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }

    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        if(usrType==1) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Configurar botones del toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings: {
                //Creamos el Intent
                Intent intent =
                        new Intent(getBaseContext(), SettingsActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
                return true;
            }

            case R.id.action_add: {
                int count = 0;
                for (int i = 0 ; i<datos_candidatos.size(); i++){
                    count+=datos_candidatos.get(i).getCount();
                }
                Toast.makeText(getApplicationContext(), "Votos totales: " + count, Toast.LENGTH_LONG).show();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}