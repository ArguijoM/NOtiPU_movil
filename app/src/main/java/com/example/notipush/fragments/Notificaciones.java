package com.example.notipush.fragments;

import android.app.Notification;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.notipush.ListaNotificaciones;
import com.example.notipush.Notificacion;
import com.example.notipush.R;

import java.util.ArrayList;

public class Notificaciones extends Fragment {
    View vista;
    ListView lv;
    ArrayList<Notificacion> notificaciones;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Notificaciones() {
    }

    public static Notificaciones newInstance(String param1, String param2) {
        Notificaciones fragment = new Notificaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        lv=vista.findViewById(R.id.listado);

        generarListado();
        ListaNotificaciones miAdaptador = new ListaNotificaciones(getContext(),R.layout.lista_notificaciones,notificaciones);
        lv.setAdapter(miAdaptador);
        return vista;
    }

    private void generarListado() {
        notificaciones = new ArrayList<Notificacion>();
        notificaciones.add(new Notificacion("Becas 2010","becas del periodo 2010","2CM1"));
        notificaciones.add(new Notificacion("Becas BEIFI","becas del grupo BEIFI","4CM1"));
        notificaciones.add(new Notificacion("Becas Delfin","becas del grupo Delfin","2CM1"));
        notificaciones.add(new Notificacion("Servicio social","informacion del servicio social","6CM1"));
        notificaciones.add(new Notificacion("Inscripciones","informacion de las inscripciones","6CM1"));
    }
}