package com.electiva.envido32.appsaenzpena;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogNewCandidato extends DialogFragment {

    public EditText addPartido;
    public EditText addCandidato;
    public EditText addLista;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mDialog = inflater.inflate(R.layout.new_candidato, null);

        //Abrimos la base de datos 'Candidatos' en modo r/w
        dbVotacionHelper =
                new VotacionSQLiteHelper(getContext(), "DB_Votacion", null, 1);

        dbVotacion = dbVotacionHelper.getWritableDatabase();

                builder.setView(mDialog);
                builder.setTitle("Agregar Candidato")
                .setIcon(R.drawable.ic_people_black_24dp)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        addLista = (EditText) mDialog.findViewById(R.id.newLista);
                        addPartido = (EditText) mDialog.findViewById(R.id.newPartido);
                        addCandidato = (EditText) mDialog.findViewById(R.id.newCandidato);

                        int lista = Integer.parseInt(addLista.getText().toString());
                        String partido = addPartido.getText().toString();
                        String nombre = addCandidato.getText().toString();
                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("lista", lista);
                        nuevoRegistro.put("partido", partido);
                        nuevoRegistro.put("nombre", nombre);

                        //Insertamos el registro en la base de datoss
                        dbVotacion.insert("Candidatos", null, nuevoRegistro);
                        dbVotacion.close();

                        dialog.cancel();
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
