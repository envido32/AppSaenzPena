package com.electiva.envido32.appsaenzpena;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Definimos la orientación de la SplashScreen como Portrait (vertical)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Escondemos el título de la app
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);


        //Creacion de la base de datos de Usuarios
        UsuariosSQLiteHelper dbUsuariosHelper = new UsuariosSQLiteHelper(this, "DB_Usuarios", null, 1);

        SQLiteDatabase dbUsuarios = dbUsuariosHelper.getWritableDatabase();

        int codigo = 1;
        String nombre = "admin";
        String pass = "admin";
        //TODO: Encriptar password

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("codigo", codigo);
        nuevoRegistro.put("nombre", nombre);
        nuevoRegistro.put("pass", pass);

        //Insertamos el registro en la base de datoss
        dbUsuarios.insert("DB_Usuarios", null, nuevoRegistro);

        //Cerramos la base de datos_candidatos
        dbUsuarios.close();

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                // Start the next activity
                Intent mainIntent = new Intent().setClass(getBaseContext(), LoginActivity.class);
                startActivity(mainIntent);
                // Terminamos la activity para que el usuario no pueda volver para atrás con el botón de back
                finish();
            }
        };

        // Simulamos con un timer un tiempo de espera definido en una constante al comienzo
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
