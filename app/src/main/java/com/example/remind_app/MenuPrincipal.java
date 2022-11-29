package com.example.remind_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuPrincipal extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

    /** Funcion para ingresar al juego twins */
    public void IngresoTwins(View view) {
        Intent twinsGame = new Intent (this, MainActivity.class);
        startActivity(twinsGame);
    }

    /** Funcion para ingresar al perfil */
    public void IngresoProfile(View view) {
        Intent perfil = new Intent (this, Profile.class);
        startActivity(perfil);
    }

    /** Funcion para ingresar a extras */
    public void IngresoExtras(View view) {
        Intent extras = new Intent (this, Extras.class);
        startActivity(extras);
    }
}