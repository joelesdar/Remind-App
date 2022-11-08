package com.example.twins;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Variables menuJuego
    Button botonJugar, botonInstrucciones, botonVideo;
    ImageButton imbSalir;
    TextView textoMaxPuntuacion, textoUltPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Entrelazamiento botones interfaz <> Código
        botonJugar = findViewById(R.id.botonJugar);
        botonInstrucciones = findViewById(R.id.botonInstrucciones);
        botonVideo = findViewById(R.id.botonVideoguia);
        imbSalir = findViewById(R.id.botonMainAtras);

        //Funcionalidad botones
        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Iniciando Juego...");
                iniciarJuego();
            }
        });

        botonInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Mostrar las instrucciones...");
            }
        });
        botonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Mostrar Video Guía...");
            }
        });

        imbSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Volviendo a pantalla principal (SALIR)...");
                finish();
            }
        });
    }
    //Funcionalidades
    private void iniciarJuego(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }
}