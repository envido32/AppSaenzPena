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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CandidatosActivity extends AppCompatActivity {

    public ListView listCandidatos;
    public Toolbar myToolbar;
    
    public String[] datos_candidatos =
            new String[]{"Perro","Gato","Chancho","Vaca","Mamut"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        listCandidatos = (ListView) findViewById(R.id.ListViewCandidatos);

        //Abrimos la base de datos_candidatos 'Candidatos' en modo escritura
        CandidatosSQLiteHelper dbCandidatosHelper =
                new CandidatosSQLiteHelper(this, "DB_Candidatos", null, 1);

        SQLiteDatabase dbCandidatos = dbCandidatosHelper.getWritableDatabase();

        //Si abrio correctamente la base de datos_candidatos
        if(dbCandidatos != null)
        {
            //Insertamos 5 usuarios de ejemplo
            for(int i=1; i<=5; i++)
            {
                //Generamos los datos_candidatos
                int codigo = i;
                String nombre = "Candidato" + i;

                //Insertamos los datos_candidatos en la tabla Usuarios
                dbCandidatos.execSQL("INSERT INTO Candidatos (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre +"')");
            }

        }

        String[] campos = new String[] {"codigo", "nombre"};
        String[] args = new String[] {"usu1"};

        Cursor dbCandidatosCursor = dbCandidatos.query("Candidatos", campos, "nombre=?", args, null, null, null);


        //Nos aseguramos de que existe al menos un registro
        if (dbCandidatosCursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            int i = 0;
            do
            {
                    String codigo = dbCandidatosCursor.getString(0);
                    String nombre = dbCandidatosCursor.getString(1);
                    datos_candidatos[i] = nombre;
                i++;
            } while (dbCandidatosCursor.moveToNext());
            dbCandidatos.close();
        }

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos_candidatos);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_list_item_1);

        listCandidatos.setAdapter(adaptador);

        listCandidatos.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent,
                                           android.view.View v, int position, long id) {
                    Toast.makeText(getApplicationContext(),
                           "Candidato: " + parent.getItemAtPosition(position),
                            Toast.LENGTH_LONG).show();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getApplicationContext(),
                            "No hay animal favorito. Soy feo :(",
                            Toast.LENGTH_LONG).show();
                }
            }
        );

        //Cerramos la base de datos_candidatos
        dbCandidatos.close();
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
