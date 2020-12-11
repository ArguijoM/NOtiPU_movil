package com.example.notipu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleNotificacion extends AppCompatActivity {
    int id;
    TextView titulo,mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_notificacion);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        titulo = findViewById(R.id.titulo);
        mensaje = findViewById(R.id.mensaje);
        titulo.setText("Becas 2010");
        mensaje.setText("Les recordamos que deben subir sus documentos antes de la fecha mencionada para que puedan quedar inscritos");
    }
}