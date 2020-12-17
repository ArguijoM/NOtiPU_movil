package com.example.notipu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleNotificacion extends AppCompatActivity {
    int id;
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

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        titulo = findViewById(R.id.titulo);
        mensaje = findViewById(R.id.mensaje);
        titulo.setText("Becas 2010");
        mensaje.setText("Les recordamos que deben subir sus documentos antes de la fecha mencionada para que puedan quedar inscritos");


    }
}