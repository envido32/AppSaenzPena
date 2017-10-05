package com.electiva.envido32.appsaenzpena;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Definimos la orientación de la SplashScreen como Portrait (vertical)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Escondemos el título de la app
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        //Creacion de la base de datos de Usuarios
        do {
            UsuariosSQLiteHelper dbUsuariosHelper =
                    new UsuariosSQLiteHelper(this, "DB_Usuarios", null, 1);

            SQLiteDatabase dbUsuarios = dbUsuariosHelper.getWritableDatabase();

            //TODO: Encriptar password
            int codigo = 1;
            String nombre = "admin";
            String pass = "admin";

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", codigo);
            nuevoRegistro.put("nombre", nombre);
            nuevoRegistro.put("pass", pass);

            //Insertamos el registro en la base de datoss
            dbUsuarios.insert("Usuarios", null, nuevoRegistro);

            codigo = 2;
            nombre = "fiscal";
            pass = "fiscal";

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", codigo);
            nuevoRegistro.put("nombre", nombre);
            nuevoRegistro.put("pass", pass);

            //Insertamos el registro en la base de datoss
            dbUsuarios.insert("Usuarios", null, nuevoRegistro);

            codigo = 3;
            nombre = "votante";
            pass = "";

            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", codigo);
            nuevoRegistro.put("nombre", nombre);
            nuevoRegistro.put("pass", pass);

            //Insertamos el registro en la base de datoss
            dbUsuarios.insert("Usuarios", null, nuevoRegistro);

            //Cerramos la base de datos_candidatos
            dbUsuarios.close();
            Log.i("SQL", "Base de datos creada: Usuarios");
        } while(false);

        //Creacion de la base de datos de Candidatos
        do {
            CandidatosSQLiteHelper dbCandidatosHelper =
                    new CandidatosSQLiteHelper(this, "DB_Candidatos", null, 1);

            SQLiteDatabase dbCandidatos = dbCandidatosHelper.getWritableDatabase();

            int lista = 1;
            String partido = "Ilucionistas";
            String nombre = "Magoo";
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("lista", lista);
            nuevoRegistro.put("partido", partido);
            nuevoRegistro.put("nombre", nombre);

            //Insertamos el registro en la base de datoss
            dbCandidatos.insert("Candidatos", null, nuevoRegistro);

            lista++;
            partido = "Animales";
            nombre = "Etelefante";

            nuevoRegistro.put("lista", lista);
            nuevoRegistro.put("partido", partido);
            nuevoRegistro.put("nombre", nombre);

            //Insertamos el registro en la base de datoss
            dbCandidatos.insert("Candidatos", null, nuevoRegistro);

            lista++;
            partido = "Aventureros";
            nombre = "Indiana";

            nuevoRegistro.put("lista", lista);
            nuevoRegistro.put("partido", partido);
            nuevoRegistro.put("nombre", nombre);

            //Insertamos el registro en la base de datoss
            dbCandidatos.insert("Candidatos", null, nuevoRegistro);

            lista++;
            partido = "Rapido y Facil";
            nombre = "Whynot";

            nuevoRegistro.put("lista", lista);
            nuevoRegistro.put("partido", partido);
            nuevoRegistro.put("nombre", nombre);

            //Insertamos el registro en la base de datoss
            dbCandidatos.insert("Candidatos", null, nuevoRegistro);

            lista++;
            partido = "Realismo Magico";
            nombre = "Zerocool";

            nuevoRegistro.put("lista", lista);
            nuevoRegistro.put("partido", partido);
            nuevoRegistro.put("nombre", nombre);

            //Insertamos el registro en la base de datoss
            dbCandidatos.insert("Candidatos", null, nuevoRegistro);

            //Cerramos la base de datos_candidatos
            dbCandidatos.close();
            Log.i("SQL", "Base de datos creada: " + lista + "Candidatos");
        } while (false);

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
