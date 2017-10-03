package com.electiva.envido32.appsaenzpena;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public EditText usrMail;
    public EditText usrPass;
    public Button btnLogin;
    public int usrType = 0;
    public SQLiteDatabase dbUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button) findViewById(R.id.buttonLogin);
        usrMail=(EditText) findViewById(R.id.editTextMail);
        usrPass=(EditText) findViewById(R.id.editTextPass);

        //Abrimos la base de datos 'Usuarios' en modo lectura
        UsuariosSQLiteHelper dbUsuariosHelper =
                new UsuariosSQLiteHelper(this, "DB_Usuarios", null, 1);

        dbUsuarios = dbUsuariosHelper.getReadableDatabase();

        dbUsuarios = null; //TODO: Arreglar la base de datos

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Verificar Login

                //Si abrio correctamente la base de datos
                if(dbUsuarios != null)
                {
                    String[] campos = new String[] {"codigo", "nombre", "pass"};
                    String[] args = new String[] {"usu1"};

                    Cursor dbUsuariosCursor = dbUsuarios.query("Usuarios", campos, "usuario=?", args, null, null, null);
                    if (dbUsuariosCursor.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            int codigo = dbUsuariosCursor.getInt(0);
                            String nombre = dbUsuariosCursor.getString(1);
                            String pass = dbUsuariosCursor.getString(2);

                            //TODO: Encriptar password

                            //Verifican login
                            if(usrMail.getText().toString().equals(nombre)
                                    && usrPass.getText().toString().equals(pass)) {
                                usrType = codigo;  // 0 = invalido, 1 = admin, 2 = fiscal, 3 = votante
                            }
                        } while(dbUsuariosCursor.moveToNext());
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_LONG).show();
                }


                usrType = 1; // DEBUG: fuerzo admin
                if(usrType > 0) {
                    //Creamos el Intent
                    Intent intent =
                            new Intent(getBaseContext(), MainActivity.class);

                    //Creamos la información a pasar entre actividades
                    Bundle bundleInfo = new Bundle();
                    bundleInfo.putInt("usrType", usrType);
                    bundleInfo.putString("usrMail", usrMail.getText().toString());

                    //Añadimos la información al intent
                    intent.putExtras(bundleInfo);

                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.pass_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
