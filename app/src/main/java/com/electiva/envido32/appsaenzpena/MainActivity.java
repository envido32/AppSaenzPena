package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public TextView textSaludos;
    public Button btnCandidatos;
    public Button btnPadron;
    public Button btnVotar;
    public Button btnEscrutineo;
    public Toolbar myToolbar;
    public String darkTheme;
    public SharedPreferences config;

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
        setContentView(R.layout.activity_main);

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);
        myToolbar.setTitle(R.string.app_name);
        setSupportActionBar(myToolbar);

        textSaludos = (TextView) findViewById(R.id.textSaludos);
        btnCandidatos = (Button) findViewById(R.id.buttonCandatos);
        btnPadron = (Button) findViewById(R.id.buttonPadron);
        btnVotar = (Button) findViewById(R.id.buttonVotar);
        btnEscrutineo = (Button) findViewById(R.id.buttonEscrutineo);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        textSaludos.setText("Bienvenido:  " + bundle.getString("usrMail"));

        // DEBUG
        Log.i("UsrType", "UsrType = " + bundle.getInt("usrType"));
        //Toast.makeText(getApplicationContext(), "DEBUG: UsrType = " + bundle.getInt("usrType"), Toast.LENGTH_LONG).show();

        btnCandidatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(getBaseContext(), CandidatosActivity.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //TODO: Base de datos del padron
        btnPadron.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.not_avaliable, Toast.LENGTH_LONG).show();
            }
        });

        //TODO: Activity para votar
        btnVotar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.not_avaliable, Toast.LENGTH_LONG).show();
            }
        });

        //TODO: Activity de recuento
        btnEscrutineo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.not_avaliable, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Agregar botones al Toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    @Override
    public void onResume(){
        super.onResume();
        config = PreferenceManager.getDefaultSharedPreferences(this);
        config = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = config.getString("opcTheme", darkTheme);
        if (darkTheme.toString().equals("DARK")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        //TODO: add view refresh
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
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogNewUsuario dialogo = new DialogNewUsuario();
                dialogo.show(fragmentManager, "tagAlerta");
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
