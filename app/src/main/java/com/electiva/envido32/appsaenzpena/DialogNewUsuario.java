package com.electiva.envido32.appsaenzpena;

import android.animation.IntArrayEvaluator;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DialogNewUsuario extends DialogFragment {

    public EditText addNombre;
    public EditText addPass1;
    public EditText addPass2;
    public Spinner addCodigo;
    public VotacionSQLiteHelper dbVotacionHelper;
    public SQLiteDatabase dbVotacion;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mDialog = inflater.inflate(R.layout.new_usuario, null);

        //Abrimos la base de datos en modo r/w
        dbVotacionHelper =
                new VotacionSQLiteHelper(getContext(), "DB_Votacion", null, 1);

        dbVotacion = dbVotacionHelper.getWritableDatabase();

                builder.setView(mDialog);
                builder.setTitle("Agregar Usuario")
                .setIcon(R.drawable.ic_person_add_black_24dp)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        addNombre = (EditText) mDialog.findViewById(R.id.newNombre);
                        addPass1 = (EditText) mDialog.findViewById(R.id.newPass1);
                        addPass2 = (EditText) mDialog.findViewById(R.id.newPass2);
                        addCodigo = (Spinner) mDialog.findViewById(R.id.newCodigo);

                        String nombre = addNombre.getText().toString();
                        String pass1 = addPass1.getText().toString();
                        String pass2 = addPass2.getText().toString();
                        int codigo = getResources().getIntArray(R.array.list_values_usrType)[addCodigo.getSelectedItemPosition()];

                        if (pass1.equals(pass2)) {

                            ContentValues nuevoRegistro = new ContentValues();

                            nuevoRegistro.put("nombre", nombre);
                            nuevoRegistro.put("pass", pass1);
                            nuevoRegistro.put("codigo", codigo);

                            //Insertamos el registro en la base de datoss
                            dbVotacion.insert("Usuarios", null, nuevoRegistro);
                        }
                        else {
                            Toast.makeText(getContext(), R.string.pass_not_equal, Toast.LENGTH_LONG).show();
                        }

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
