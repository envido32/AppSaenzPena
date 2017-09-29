package com.electiva.envido32.appsaenzpena;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button) findViewById(R.id.buttonLogin);
        usrMail=(EditText) findViewById(R.id.editTextMail);
        usrPass=(EditText) findViewById(R.id.editTextPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // To Do: Agregar verificacion de usuarios con base SQL lite
                if(usrMail.getText().toString().equals("usr")
                        && usrPass.getText().toString().equals("1234")) {
                    //Creamos el Intent
                    Intent intent =
                            new Intent(LoginActivity.this, MainActivity.class);

                    //Creamos la información a pasar entre actividades
                    Bundle bundleInfo = new Bundle();
                    bundleInfo.putString("usrMail", usrMail.getText().toString());

                    //Añadimos la información al intent
                    intent.putExtras(bundleInfo);

                    //Iniciamos la nueva actividad
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Nombre de usuario y/o contraseña incorreto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
