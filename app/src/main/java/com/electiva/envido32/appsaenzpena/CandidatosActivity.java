package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CandidatosActivity extends AppCompatActivity {

    public ListView listCandidatos;
    public Toolbar myToolbar;
    
    public CandidatoClass[] datos_candidatos =
            new CandidatoClass[]{
                    new CandidatoClass(1, "Ilucionistas", "Magoo"),
                    new CandidatoClass(2, "Animales", "Etelefante"),
                    new CandidatoClass(3, "Aventureros", "Indiana"),
                    new CandidatoClass(4, "Rapido y Facil", "Whynot"),
                    new CandidatoClass(5, "Realismo Magico", "Zerocool"),
            };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        AdaptadorCandidatos adaptador =
                new AdaptadorCandidatos(this, datos_candidatos);

        listCandidatos = (ListView)findViewById(R.id.ListViewCandidatos);
        listCandidatos.setAdapter(adaptador);

        /* TODO: Arreglar base de datos
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

        //Cerramos la base de datos_candidatos
        dbCandidatos.close();
        */
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
            lblPartido.setText(datos_candidatos[position].getPartido());

            return(item);
        }
    }
}
