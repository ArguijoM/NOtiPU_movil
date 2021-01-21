package com.example.notipu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notipu.firebase.MyFirebaseInstanceIdService;
import com.example.notipu.permisos.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Inicio extends AppCompatActivity {

    private static final String ip="192.168.43.100";

    EditText clave;
    Button continuar;
    Spinner spiner;
    int info,idPrograma,idUsuario;
    String nombrecompleto,programa,boleta,tipo;

    RequestQueue requestQueue; //Respuesta de la consulta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        cargarInformacion();
        if(info==1){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else{
            cargarInformacion();
        }

        clave = findViewById(R.id.clave);
        spiner = (Spinner)findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opcionerSpiner());
        spiner.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boleta = clave.getText().toString();
                tipo = spiner.getSelectedItem().toString();
                switch (tipo){
                    case "ALUMNO":
                       String urlAlumno ="http://sistemas.upiiz.ipn.mx/isc/nopu/api/alumno.php?boleta="+boleta;
                       stringRequest(urlAlumno);
                    break;
                    case "EMPLEADO":
                       String urlEmpleado ="http://sistemas.upiiz.ipn.mx/isc/nopu/api/empleado.php?numempleado="+boleta;
                       stringRequest(urlEmpleado);
                    break;
                    default:
                        Toast.makeText(getApplicationContext(), "Elija una de las 2 opciónes", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        });
    }

    private void stringRequest(String url){
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String respuesta = jsonObject.getString("estado");
                    if(respuesta.equals("1")){
                        nombrecompleto = jsonObject.getString("nombre");
                        programa=jsonObject.getString("programa");
                        getPrograma(programa);
                        getUsuario(boleta);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Revise su información", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error de respuesta"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) ;
        requestQueue.add(request);
    }

    private String[] opcionerSpiner() {
        String[] buffer = new String[3];
        buffer[0]="Elija una opción";
        buffer[1]="ALUMNO";
        buffer[2]="EMPLEADO";
        return buffer;
    }

    private void guardarInformacion() {
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        //nombrecompleto  boleta  tipo  programa
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("info", 1);
        editor.putInt("idUsuario", idUsuario);
        editor.putString("nombrecompleto", nombrecompleto);
        editor.putString("boleta", boleta);
        editor.putString("tipo", tipo);
        editor.putInt("Programa_idPrograma", idPrograma);
        editor.commit();
    }
    private void cargarInformacion(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        info=preferences.getInt("info",0);
    }

    private void getUsuario(final String boleta){
        String url = "http://"+ip+"/NOtiPU_web/php/notipu/public/api/usuariosBoleta/"+boleta;
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String respuesta = jsonObject.getString("estado");
                    if(respuesta.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("usuario");
                        for(int i=0; i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            idUsuario = object.getInt("idUsuario");
                            guardarInformacion();
                           // Toast.makeText(getApplicationContext(), "idUsuario: "+idUsuario, Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        postUsuario();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error de volley: : ", e.toString());
                    // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error de respuesta"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) ;
        requestQueue.add(request);
    }

    private void postUsuario(){
        String url = "http://"+ip+"/NOtiPU_web/php/notipu/public/api/usuarios/nuevo";
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            getUsuario(boleta);
                           //Toast.makeText(getApplicationContext(), "Usuario agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        }else{
                           Toast.makeText(getApplicationContext(), "Agrege los datos correspondientes", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombrecompleto",nombrecompleto);
                parametros.put("boleta",boleta);
                parametros.put("tipo",tipo);
                parametros.put("Programa_idPrograma", String.valueOf(idPrograma));
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    private void postPrograma(final String nombrePrograma){
        String url = "http://"+ip+"/NOtiPU_web/php/notipu/public/api/programas/nuevo";
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            //Toast.makeText(getApplicationContext(), "Usuario agregado satisfactoriamente", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Agrege los datos correspondientes", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Nombre",nombrePrograma);
                parametros.put("Descripcion",nombrePrograma);
                return parametros;
            }
        };
        requestQueue.add(request);
        getPrograma(nombrePrograma);
    }

    private void getPrograma(final String nombrePrograma){
        String url = "http://"+ip+"/NOtiPU_web/php/notipu/public/api/programas/"+nombrePrograma;
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String respuesta = jsonObject.getString("estado");
                    if(respuesta.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("programa");
                        for(int i=0; i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            idPrograma = object.getInt("idPrograma");
                            guardarInformacion();
                            Log.d("ID programa: ", String.valueOf(idPrograma));
                            //Toast.makeText(getApplicationContext(), "Ya existe el programa", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        postPrograma(nombrePrograma);
                        Toast.makeText(getApplicationContext(), "Revise su información", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error de volley: : ", e.toString());
                    // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error de volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Error de respuesta"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) ;
        requestQueue.add(request);
    }
}