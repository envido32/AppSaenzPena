package com.electiva.envido32.appsaenzpena;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;

public class DeleteCandidatoActivity extends AppCompatActivity {

    public TextView textTitle;
    public int lista;
    public Button btnDel;
    public Button btnBack;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_candidato);

        btnDel = (Button) findViewById(R.id.buttonAdd);
        btnBack = (Button) findViewById(R.id.buttonCancel);
        textTitle = (TextView) findViewById(R.id.subtitleDelCandidato);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        lista = bundle.getInt("lista");

        //Construimos el mensaje a mostrar
        textTitle.setText("Eliminar Candidato: Lista " + String.valueOf(lista));

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Abrimos la base de datos 'Candidatos' en modo r/w
                dbVotacionHelper =
                        new VotacionSQLiteHelper(getBaseContext(), "DB_Votacion", null, 1);

                dbVotacion = dbVotacionHelper.getWritableDatabase();

                //Si abrio correctamente la base de datos la cargo en el array
                if(dbVotacion != null){
                    String[] args = new String[] {String.valueOf(lista)};
                    dbVotacion.delete("Candidatos", "lista=?", args);
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.db_error, Toast.LENGTH_LONG).show();
                }
                dbVotacion.close();
                finish();
            }
        });
    }
}
