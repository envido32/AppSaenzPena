package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CandidatosActivity extends AppCompatActivity {

    public ListView listCandidatos;
    public Toolbar myToolbar;
    public CandidatoClass[] datos_candidatos =
            new CandidatoClass[5];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        //Abrimos la base de datos 'Candidatos' en modo r/w
        CandidatosSQLiteHelper dbCandidatosHelper =
                new CandidatosSQLiteHelper(getBaseContext(), "DB_Candidatos", null, 1);

        SQLiteDatabase dbCandidatos = dbCandidatosHelper.getWritableDatabase();

        //Si abrio correctamente la base de datos la cargo en el array
        if(dbCandidatos != null){
            String[] campos = new String[] {"lista", "partido", "nombre"};

            Cursor dbCandidatosCursor = dbCandidatos.query("Candidatos", campos, null, null, null, null, null);
            //dbCandidatosCursor = null; // DEBUG
            //Nos aseguramos de que existe al menos un registro
            if (dbCandidatosCursor.moveToFirst()) {
                do {

                    int lista = dbCandidatosCursor.getInt(0);
                    String partido = dbCandidatosCursor.getString(1);
                    String nombre = dbCandidatosCursor.getString(2);

                    CandidatoClass addCandidatto =
                            new CandidatoClass(lista, partido, nombre);
                    //TODO: arreglar el inflado
                    datos_candidatos[lista-1] = addCandidatto;

                }while(dbCandidatosCursor.moveToNext());
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

                        String opcionSeleccionada =
                                ((CandidatoClass)parent.getItemAtPosition(position)).getPartido();

                        Toast.makeText(getApplicationContext(),
                                "Click! " +
                                        "" + opcionSeleccionada,
                                Toast.LENGTH_LONG).show();
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

    // TODO: Migrar a CandidatoClass.java
    class AdaptadorCandidatos extends ArrayAdapter<CandidatoClass> {

        public AdaptadorCandidatos(Context context, CandidatoClass[] datos_candidatos) {
            super(context, R.layout.listitem_candidato, datos_candidatos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_candidato, null);

            TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
            lblNombre.setText(datos_candidatos[position].getNombre());

            TextView lblPartido = (TextView)item.findViewById(R.id.LblPartido);
            lblPartido.setText("Lista " + datos_candidatos[position].getLista() + " - "+ datos_candidatos[position].getPartido());

            return(item);
        }
    }
}
