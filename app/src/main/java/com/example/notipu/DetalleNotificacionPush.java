package com.example.notipu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetalleNotificacionPush extends AppCompatActivity {
    TextView mensaje,titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_notificacion_push);

        mensaje = findViewById(R.id.mensajePush);
        titulo = findViewById(R.id.tituloPush);
        if(getIntent().getExtras()!=null){
            for(String key: getIntent().getExtras().keySet()){
                String title = getIntent().getExtras().getString("titulo");
                String cuerpo = getIntent().getExtras().getString("cuerpo");
                titulo.setText(title);
                mensaje.setText(cuerpo);
            }
        }
    }
}