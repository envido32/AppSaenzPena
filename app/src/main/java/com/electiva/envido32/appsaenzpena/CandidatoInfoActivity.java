package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.Toast;

public class CandidatoInfoActivity extends AppCompatActivity {

    public Toolbar myToolbar;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    public String darkTheme;
    public SharedPreferences config;

    public SharedPreferences prefs;
    public int usrType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = PreferenceManager.getDefaultSharedPreferences(this);
        darkTheme = config.getString("opcTheme", darkTheme);
        if (darkTheme.toString().equals("DARK")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_candidato_info);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        //Definimos la Toolbar
        myToolbar = (Toolbar) findViewById(R.id.appbar);

        //Construimos el mensaje a mostrar
        myToolbar.setTitle("Partido not found");
        myToolbar.setSubtitle("Lista " +  bundle.getInt("lista"));
        setSupportActionBar(myToolbar);

        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        usrType = prefs.getInt("usrType", 0);

        //Abrimos la base de datos en modo read only
        dbVotacionHelper = new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);
        dbVotacion = dbVotacionHelper.getReadableDatabase();

        //Si abrio correctamente la base de datos la cargo en el array
        if(dbVotacion != null){
            String[] campos = new String[] {"lista", "partido", "nombre"};
            String[] args = new String[] {String.valueOf(bundle.getInt("lista"))};

            Cursor dbVotacionCursor = dbVotacion.query("Candidatos", campos, "lista=?", args, null, null, null);

            //dbVotacionCursor = null; // DEBUG
            //Nos aseguramos de que existe al menos un registro
            if (dbVotacionCursor.moveToFirst()) {
                do {

                    int lista = dbVotacionCursor.getInt(0);
                    String partido = dbVotacionCursor.getString(1);
                    String nombre = dbVotacionCursor.getString(2);

                    myToolbar.setTitle(partido);

                } while(dbVotacionCursor.moveToNext());
            }
            dbVotacion.close();
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_LONG).show();
        }

        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Lista Completa",
                res.getDrawable(R.drawable.ic_playlist_add_check_black_24dp));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Plataforma",
                res.getDrawable(R.drawable.ic_format_list_numbered_black_24dp));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });
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
    public void onResume(){
        super.onResume();
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

}