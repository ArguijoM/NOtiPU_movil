package com.example.notipu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notipu.permisos.HttpsTrustManager;
import com.example.notipu.firebase.MyFirebaseInstanceIdService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String ip="192.168.43.100";

    Button salir;
    String tipo,boleta,nombrecompleto,token;
    int info,idPrograma,idUsuario;
    RequestQueue requestQueue;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = MyFirebaseInstanceIdService.firebaseToken();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        cargarInformacion();
        if(idUsuario==0){
            cargarInformacion();
        }else{
            putUsuario(idUsuario);
        }

        salir = findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.paginaInicial, R.id.notificaciones)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void cargarInformacion(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        info=preferences.getInt("info",0);
        idUsuario=preferences.getInt("idUsuario",0);
        nombrecompleto = preferences.getString("nombrecompleto","No existe la información");
        boleta = preferences.getString("boleta","No existe la información");
        tipo = preferences.getString("tipo","No existe la información");
        idPrograma = preferences.getInt("Programa_idPrograma",0);
    }

    private void putUsuario(int id){
        cargarInformacion();
        String url = "http://"+ip+"/NOtiPU_web/php/notipu/public/api/usuarios/modificar/"+id;
        HttpsTrustManager.allowAllSSL();
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            Log.d("Mensaje de volley","Usuario modificado satisfactoriamente");
                            //Toast.makeText(getApplicationContext(), "Usuario modificado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se ha podido actializar el token", Toast.LENGTH_SHORT).show();
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
                parametros.put("boleta",boleta);
                parametros.put("nombrecompleto",nombrecompleto);
                parametros.put("token",token);
                parametros.put("tipo",tipo);
                parametros.put("Programa_idPrograma", String.valueOf(idPrograma));
                return parametros;
            }
        };
        requestQueue.add(request);

    }
}