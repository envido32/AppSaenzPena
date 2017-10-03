package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CandidatoClass {
    private int lista;
    private String partido;
    private String nombre;

    public CandidatoClass(int lis, String part, String nom){
        lista = lis;
        partido = part;
        nombre = nom;
    }

    public void editLista(int lis){
        lista = lis;
    }

    public void editPartido(String part){
        partido = part;
    }

    public void editNombre(String nom){
        nombre = nom;
    }

    public int getLista(){
        return lista;
    }

    public String getPartido(){
        return partido;
    }

    public String getNombre(){
        return nombre;
    }
}

