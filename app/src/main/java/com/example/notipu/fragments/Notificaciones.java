package com.example.notipu.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notipu.DetalleNotificacion;
import com.example.notipu.permisos.HttpsTrustManager;
import com.example.notipu.ListaNotificaciones;
import com.example.notipu.Notificacion;
import com.example.notipu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notificaciones extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ip="192.168.43.100";

    View vista;
    ListView lv;
    ArrayList<Notificacion> notificaciones;
    RequestQueue requestQueue; //Respuesta de la consulta

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
        lv.setOnItemClickListener(this);
        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest();
        return vista;
    }

    private void stringRequest(){
        notificaciones = new ArrayList<Notificacion>();//inicializamos la lista de contactos
        //inicializamos el adaptador
        final ListaNotificaciones miAdaptador = new ListaNotificaciones(getContext(),R.layout.lista_notificaciones,notificaciones);
        lv.setAdapter(miAdaptador);
        //Consulta
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://"+ip+"/NOtiPU_web/php/notipu/public/api/notificaciones",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String respuesta = jsonObject.getString("estado");
                    if(respuesta.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("notificaciones");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int idNotificacion = object.getInt("idNotificacion");
                            String titulo = object.getString("titulo");
                            String descripcion = object.getString("descripcion");
                            String fecha = object.getString("fecha");
                            int Grupo_idGrupo = object.getInt("Grupo_idGrupo");
                            Notificacion not = new Notificacion(titulo,descripcion,Grupo_idGrupo);
                            notificaciones.add(not);
                            miAdaptador.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(getContext(), "No hay notificaciones", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                       // Toast.makeText(getContext(), "Error de respuesta"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) ;
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String asunto = notificaciones.get(position).getAsunto();
        String descripcion = notificaciones.get(position).getDescripcion();
        Intent intent = new Intent(getContext(),DetalleNotificacion.class);
        intent.putExtra("asunto",asunto);
        intent.putExtra("descripcion",descripcion);
        startActivity(intent);
    }

}