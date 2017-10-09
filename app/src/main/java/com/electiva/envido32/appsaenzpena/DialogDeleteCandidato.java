package com.electiva.envido32.appsaenzpena;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialogDeleteCandidato extends DialogFragment {

    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mDialog = inflater.inflate(R.layout.new_candidato, null);
        final int lista = this.getArguments().getInt("lista");

        //Abrimos la base de datos 'Candidatos' en modo r/w
        dbVotacionHelper =
                new VotacionSQLiteHelper(getContext(), "DB_Votacion", null, 1);

        dbVotacion = dbVotacionHelper.getWritableDatabase();

        //builder.setView(mDialog);
        builder.setTitle("Eliminar Candidato: Lista " + String.valueOf(lista));
        builder.setMessage(R.string.delete_candidato);
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Si abrio correctamente la base de datos la cargo en el array
                        if(dbVotacion != null){
                            String[] campos = new String[] {"lista", "partido", "nombre"};
                            String[] args = new String[] {String.valueOf(lista)};
                            dbVotacion.delete("Candidatos", "lista=?", args);
                        }
                        else
                        {
                            Toast.makeText(getContext(), R.string.db_error, Toast.LENGTH_LONG).show();
                        }
                        dbVotacion.close();
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
