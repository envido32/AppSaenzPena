package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CandidatoClass {
    private int lista;
    private String partido;
    private String nombre;

    public CandidatoClass(int lis, String part, String nom){
        lista = lis;
        partido = part;
        nombre = nom;
    }

    public void setLista(int lis){
        lista = lis;
    }

    public void setPartido(String part){
        partido = part;
    }

    public void setNombre(String nom){
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

class AdaptadorCandidatos extends ArrayAdapter<CandidatoClass> {
    public ArrayList<CandidatoClass> datos;

    public AdaptadorCandidatos(Context context, ArrayList<CandidatoClass> datos_candidatos) {
        super(context, R.layout.listitem_candidato, datos_candidatos);
        datos = datos_candidatos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_candidato, null);

        TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
        lblNombre.setText(datos.get(position).getNombre());

        TextView lblPartido = (TextView)item.findViewById(R.id.LblPartido);
        lblPartido.setText("Lista " + datos.get(position).getLista() + " - "+ datos.get(position).getPartido());

        return(item);
    }
}