package com.example.notipush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaNotificaciones extends BaseAdapter {
    private Context c;
    private int diseno;
    private ArrayList<Notificacion> notificaciones;

    public ListaNotificaciones(Context c, int diseno, ArrayList<Notificacion> notificaciones) {
        this.c = c;
        this.diseno = diseno;
        this.notificaciones = notificaciones;
    }

    @Override
    public int getCount() {
        return notificaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return notificaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaDiseno = convertView;
        if(vistaDiseno==null){
            //Creamos la vista
            LayoutInflater LayoutInflater = android.view.LayoutInflater.from(c);
            vistaDiseno = LayoutInflater.inflate(diseno,null);
        }
        TextView asunto = vistaDiseno.findViewById(R.id.notificacion);
        asunto.setText(notificaciones.get(position).getAsunto());
        return vistaDiseno;
    }
}
