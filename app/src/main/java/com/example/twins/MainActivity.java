package com.example.twins;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button jugar, instrucciones, video;
    ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugar = findViewById(R.id.botonJugar);
        instrucciones = findViewById(R.id.botonInstrucciones);
        video = findViewById(R.id.botonVideoguia);
        atras = findViewById(R.id.botonMainAtras);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Iniciando Juego...");
                iniciarJuego();
            }
        });

        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Mostrar las instrucciones...");
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Mostrar Video Guía...");
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Volviendo a pantalla anterior...");
                finish();
            }
        });
    }
    private void iniciarJuego(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }
}