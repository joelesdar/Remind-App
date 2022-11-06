package com.example.remind_app.picture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.Picture;
import com.example.remind_app.R;

public class pictureGameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_game_screen);
        getSupportActionBar().hide();
    }

    /** Funcion para regresar al menu del juego picture */
    public void RegresoPicture(View view) {
        Intent picture = new Intent (this, Picture.class);
        startActivity(picture);
    }
}