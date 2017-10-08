package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class VotacionSQLiteHelper extends SQLiteOpenHelper {

    // Table Names
    private static final String TABLE_CANDIDATOS = "Candidatos";
    private static final String TABLE_USUARIOS = "Usuarios";

    //Sentencia SQL para crear la tabla de Usuarios
    String[] sqlCreate = {"CREATE TABLE " + TABLE_CANDIDATOS +
            " (lista INTEGER, partido TEXT, nombre TEXT)",
                        "CREATE TABLE " + TABLE_USUARIOS +
            " (codigo INTEGER, nombre TEXT, pass TEXT)" };

    public VotacionSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        for (int i=0; i<sqlCreate.length; i++){
            db.execSQL(sqlCreate[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTE: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos_candidatos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);

        //Se crea la nueva versión de la tabla
        onCreate(db);
    }
}