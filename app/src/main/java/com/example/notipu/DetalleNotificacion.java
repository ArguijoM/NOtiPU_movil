package com.example.notipu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleNotificacion extends AppCompatActivity {
    int id;
    String asunto,descripcion;
    TextView titulo,mensaje;
    private ActionBar actionBar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_notificacion);
        //Inicializacion
        actionBar = getSupportActionBar();
        //Titulo
        actionBar.setTitle("Notificación");
        //Boton Negro
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

       titulo = findViewById(R.id.titulo);
       mensaje = findViewById(R.id.mensaje);
       Bundle b = getIntent().getExtras();
       asunto=b.getString("asunto");
       descripcion=b.getString("descripcion");

       titulo.setText(asunto);
       mensaje.setText(descripcion);


    }
}