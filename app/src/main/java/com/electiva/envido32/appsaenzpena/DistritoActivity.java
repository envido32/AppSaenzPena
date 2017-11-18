package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DistritoActivity extends AppCompatActivity  {

    private TextView txtDetect;
    private ImageButton imageButton;
    private Spinner spinner;
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrito);

        imageButton=(ImageButton) findViewById(R.id.imgbtn_gps);
        txtDetect=(TextView) findViewById(R.id.text_gps);
        spinner=(Spinner) findViewById(R.id.spinner_distrito);
        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        int prevpos = prefs.getInt("opcDistrito", -1);
        //int dist = Integer.parseInt(getResources().getStringArray(R.array.list_values_distritos)[prevposq]);
        //parent.getItemAtPosition(position));

        int[] aux = getResources().getIntArray(R.array.list_values_distritos);
        for(int i=0; i < aux.length; i++){
            if(aux[i]==prevpos){
                prevpos=i;
            }
        }
        spinner.setSelection(prevpos);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        txtDetect.setText("Seleccionado: " +
                                parent.getItemAtPosition(position));

                        SharedPreferences prefs =
                                getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        //String dist = getResources().getStringArray(R.array.list_values_distritos)[position];
                        //int dist = Integer.parseInt(getResources().getStringArray(R.array.list_values_distritos)[position]);
                        int dist = getResources().getIntArray(R.array.list_values_distritos)[position];
                        //String dist = parent.getItemAtPosition(position).toString();
                        editor.putInt("opcDistrito", dist);
                        //editor.commit();
                        editor.apply();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        txtDetect.setText("");
                    }
                });

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(getBaseContext(), PadronActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }
}