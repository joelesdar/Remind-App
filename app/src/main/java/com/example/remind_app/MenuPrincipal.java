package com.example.remind_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        getSupportActionBar().hide();
    }

    /** Funcion para ingresar al juego picture */
    public void IngresoPicture(View view) {
        Intent pictureGame = new Intent (this, Picture.class);
        startActivity(pictureGame);
    }
}