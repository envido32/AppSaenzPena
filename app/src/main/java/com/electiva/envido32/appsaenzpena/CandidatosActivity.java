package com.electiva.envido32.appsaenzpena;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CandidatosActivity extends AppCompatActivity {

    public ListView lstvwUnica;
    public String[] datos =
            new String[]{"Perro","Gato","Chancho","Vaca","Mamut"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        lstvwUnica = (ListView)findViewById(R.id.ListView1);

        //Abrimos la base de datos 'Animales' en modo escritura
        CandidatosSQLiteHelper animalesdb =
                new CandidatosSQLiteHelper(this, "DB_Candidatos", null, 1);

        SQLiteDatabase db = animalesdb.getWritableDatabase();

        //Si abrio correctamente la base de datos
        if(db != null)
        {
            //Insertamos 5 usuarios de ejemplo
            for(int i=1; i<=5; i++)
            {
                //Generamos los datos
                int codigo = i;
                String nombre = "Candidato" + i;

                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Candidatos (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre +"')");
            }

            //Cerramos la base de datos
            db.close();
        }

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_list_item_1);

        lstvwUnica.setAdapter(adaptador);

        lstvwUnica.setOnItemSelectedListener(
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
                });
    }
}
