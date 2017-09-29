package com.electiva.envido32.appsaenzpena;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public TextView textSaludos;
    public Button btnCandidatos;
    public Button btnPadron;
    public Button btnVotar;
    public Button btnEscrutineo;
    public Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(myToolbar);

        textSaludos = (TextView) findViewById(R.id.textSaludos);
        btnCandidatos = (Button) findViewById(R.id.buttonCandatos);
        btnPadron = (Button) findViewById(R.id.buttonPadron);
        btnVotar = (Button) findViewById(R.id.buttonVotar);
        btnEscrutineo = (Button) findViewById(R.id.buttonEscrutineo);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        //textSaludos.setText("Bienvenido:  " + bundle.getString("usrMail"));
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
                        new Intent(MainActivity.this, SettingsActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
