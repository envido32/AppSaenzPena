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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CandidatosActivity extends AppCompatActivity {

    public ListView listCandidatos;
    public Toolbar myToolbar;
    public ArrayList<CandidatoClass> datos_candidatos = new ArrayList<>();
    public AdaptadorCandidatos adaptador;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    public String darkTheme;
    public boolean opcDialog;
    public SharedPreferences config;

    public SharedPreferences prefs;
    public int usrType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = config.getString("opcTheme", darkTheme);
        if (darkTheme.toString().equals("DARK")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_candidatos);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.candidatos);
        setSupportActionBar(myToolbar);
        adaptador = new AdaptadorCandidatos(this, datos_candidatos);
        listCandidatos = (ListView)findViewById(R.id.ListViewCandidatos);
        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        usrType = prefs.getInt("usrType", 0);

        refreshView();

        listCandidatos.setAdapter(adaptador);

        if(usrType==1) {
            listCandidatos.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> parent,
                                                       android.view.View v, int position, long id) {

                            config = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            opcDialog = config.getBoolean("opcDialog", opcDialog);

                            int lista = ((CandidatoClass) parent.getItemAtPosition(position)).getLista();
                            Bundle bundle = new Bundle();
                            bundle.putInt("lista", lista);

                            if (opcDialog) {

                                FragmentManager fragmentManager = getSupportFragmentManager();

                                DialogDeleteCandidato dialogo = new DialogDeleteCandidato();
                                dialogo.setArguments(bundle);
                                dialogo.show(fragmentManager, "tagAlerta");
                            } else {
                                //Creamos el Intent
                                Intent intent =
                                        new Intent(getBaseContext(), DeleteCandidatoActivity.class);

                                //Añadimos la información al intent
                                intent.putExtras(bundle);
                                //Iniciamos la nueva actividad
                                startActivity(intent);
                            }
                            return true;
                        }
                    });
        }

        listCandidatos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {

                        int opcionSeleccionada =
                                ((CandidatoClass)parent.getItemAtPosition(position)).getLista();

                        //Creamos el Intent
                        Intent intent =
                                new Intent(getBaseContext(), CandidatoInfoActivity.class);

                        //Creamos la información a pasar entre actividades
                        Bundle bundleInfo = new Bundle();
                        bundleInfo.putInt("lista", opcionSeleccionada);

                        //Añadimos la información al intent
                        intent.putExtras(bundleInfo);


                        //Iniciamos la nueva actividad
                        startActivity(intent);
                    }
                });
    }

    public void refreshView() {
        adaptador.clear();
        datos_candidatos.clear();

        //Abrimos la base de datos en modo read only
        dbVotacionHelper = new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);
        dbVotacion = dbVotacionHelper.getReadableDatabase();

        //Si abrio correctamente la base de datos la cargo en el array
        if(dbVotacion != null){
            String[] campos = new String[] {"lista", "partido", "nombre"};

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

                } while(dbVotacionCursor.moveToNext());
            }
            adaptador.notifyDataSetChanged();
            dbVotacion.close();
        }
        else
        {
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
                config = PreferenceManager.getDefaultSharedPreferences(this);
                opcDialog = config.getBoolean("opcDialog", opcDialog);
                if (opcDialog) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogNewCandidato dialogo = new DialogNewCandidato();
                    dialogo.show(fragmentManager, "tagAlerta");
                }
                else {
                    //Creamos el Intent
                    Intent intent =
                            new Intent(getBaseContext(), NewCandidatoActivity.class);
                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}