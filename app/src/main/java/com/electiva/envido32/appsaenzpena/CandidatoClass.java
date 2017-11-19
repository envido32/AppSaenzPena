package com.electiva.envido32.appsaenzpena;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CandidatoClass {
    private int lista;
    private String partido;
    private String nombre;
    private int cuenta;

    public CandidatoClass(int lis, String part, String nom){
        lista = lis;
        partido = part;
        nombre = nom;
        int cuenta = 0;
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

    public void addCount(){
        cuenta++;
    }

    public void lessCount(){
        if(cuenta>0){
            cuenta--;
        }
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

    public int getCount(){
        return cuenta;
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

class AdaptadorEscrutineo extends RecyclerView.Adapter<AdaptadorEscrutineo.EscrutineoViewHolder> {
    public ArrayList<CandidatoClass> datos;

    public static class EscrutineoViewHolder extends RecyclerView.ViewHolder {
        TextView escPartido;
        TextView escNombre;
        TextView escCount;

        EscrutineoViewHolder(View itemView) {
            super(itemView);
            escPartido = (TextView)itemView.findViewById(R.id.LblPartido);
            escNombre = (TextView)itemView.findViewById(R.id.LblNombre);
            escCount = (TextView)itemView.findViewById(R.id.LblCuenta);
        }
    }

    public AdaptadorEscrutineo(ArrayList<CandidatoClass> datos_candidatos) {
        datos = datos_candidatos;
    }

    @Override
    public EscrutineoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_candidato, viewGroup, false);
        EscrutineoViewHolder evh = new EscrutineoViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(EscrutineoViewHolder candidatoViewHolder, int position) {
        candidatoViewHolder.escNombre.setText(datos.get(position).getNombre());
        candidatoViewHolder.escPartido.setText("Lista " + datos.get(position).getLista() + " - "+ datos.get(position).getPartido());
        candidatoViewHolder.escCount.setText("Votos: " + datos.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}