package com.electiva.envido32.appsaenzpena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CandidatoInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato_info);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        Toast.makeText(getApplicationContext(),
                "Partido: " + bundle.getInt("lista"),
                Toast.LENGTH_LONG).show();
    }
}
