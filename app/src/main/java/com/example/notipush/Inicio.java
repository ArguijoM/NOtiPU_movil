package com.example.notipush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Inicio extends AppCompatActivity {
    Button continuar;
    Spinner spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        spiner = (Spinner)findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opcionerSpiner());
        spiner.setAdapter(adapter);

        continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private String[] opcionerSpiner() {
        String[] buffer = new String[4];
        buffer[0]="Elija una opción";
        buffer[1]="Opción 1";
        buffer[2]="Opción 2";
        buffer[3]="Opción 3";
        return buffer;
    }
}