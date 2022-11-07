package com.example.remind_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.picture.pictureGameScreen;

public class Picture extends AppCompatActivity {

    Button jugar, instrucciones, video;
    ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        getSupportActionBar().hide();

        instrucciones = findViewById(R.id.botonInstruccionesPicture);
        video = findViewById(R.id.botonVideoguiaPicture);

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


    }

    /** Regresar al menu principal */
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }

    /** Ingresar al juego */
    public void IngresoJuegoPicture (View view) {
        Intent game = new Intent (this, pictureGameScreen.class);
        startActivity(game);
    }
}