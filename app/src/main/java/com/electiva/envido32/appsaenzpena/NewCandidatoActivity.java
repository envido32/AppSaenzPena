package com.electiva.envido32.appsaenzpena;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCandidatoActivity extends AppCompatActivity {

    public EditText addPartido;
    public EditText addCandidato;
    public EditText addLista;
    public Button btnAdd;
    public Button btnBack;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_candidato);

        addLista = (EditText) findViewById(R.id.newLista);
        addPartido = (EditText) findViewById(R.id.newPartido);
        addCandidato = (EditText) findViewById(R.id.newCandidato);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnBack = (Button) findViewById(R.id.buttonCancel);

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Abrimos la base de datos 'Candidatos' en modo r/w
                dbVotacionHelper = new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);
                dbVotacion = dbVotacionHelper.getWritableDatabase();

                int lista = Integer.parseInt(addLista.getText().toString());
                String partido = addPartido.getText().toString();
                String nombre = addCandidato.getText().toString();
                ContentValues nuevoRegistro = new ContentValues();

                //TODO: Verificar duplicado
                nuevoRegistro.put("lista", lista);
                nuevoRegistro.put("partido", partido);
                nuevoRegistro.put("nombre", nombre);

                //Insertamos el registro en la base de datoss
                dbVotacion.insert("Candidatos", null, nuevoRegistro);
                dbVotacion.close();
                finish();
            }
        });
    }
}
