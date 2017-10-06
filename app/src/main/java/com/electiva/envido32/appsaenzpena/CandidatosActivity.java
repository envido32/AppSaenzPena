package com.electiva.envido32.appsaenzpena;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        //Abrimos la base de datos 'Candidatos' en modo r/w
        VotacionSQLiteHelper dbVotacionHelper =
                new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);

        SQLiteDatabase dbVotacion = dbVotacionHelper.getWritableDatabase();

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

                }while(dbVotacionCursor.moveToNext());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_LONG).show();
        }

        AdaptadorCandidatos adaptador =
                new AdaptadorCandidatos(this, datos_candidatos);

        listCandidatos = (ListView)findViewById(R.id.ListViewCandidatos);
        listCandidatos.setAdapter(adaptador);

        listCandidatos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                android.view.View v, int position, long id) {

                        String opcionSeleccionada =
                                ((CandidatoClass)parent.getItemAtPosition(position)).getPartido();

                        Toast.makeText(getApplicationContext(),
                                "Long! " + opcionSeleccionada,
                                Toast.LENGTH_LONG).show();
                        return true;
                    }
                });

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

    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Configurar botones del toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Creamos el Intent
                Intent intent =
                        new Intent(getBaseContext(), SettingsActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
                return true;

            case R.id.action_add:
                //TODO: agregar candidato
                Toast.makeText(getApplicationContext(), R.string.not_avaliable, Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
