package com.example.remind_app.picture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.Picture;
import com.example.remind_app.R;

import java.util.Random;

public class pictureGameScreen extends AppCompatActivity {

    // Imagenes que se mostraran en pantalla
    ImageView image1, image2, image3, image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_game_screen);
        getSupportActionBar().hide();

//        image1 = (ImageView) findViewById(R.id.imagen1);
//        image2 = (ImageView) findViewById(R.id.imagen2);
//        image3 = (ImageView) findViewById(R.id.imagen3);
//        image4 = (ImageView) findViewById(R.id.imagen4);

        int [] imagenes = { R.drawable.picturefigura1,
                            R.drawable.picturefigura2,
                            R.drawable.picturefigura3,
                            R.drawable.picturefigura4,
                            R.drawable.picturefigura5,
                            R.drawable.picturefigura6,
                            R.drawable.picturefigura7,
                            R.drawable.picturefigura8,
                            R.drawable.picturefigura9,
                            R.drawable.picturefigura10,
                            R.drawable.picturefigura11};

        Random r = new Random(2);

        int image = r.nextInt();

//        image1.setImageResource(R.drawable.picturefigura3);

    }

    /** Funcion para generar imagen al azar */
    public void RandomImage(View view) {
        // void
    }

    /** Funcion para regresar al menu del juego picture */
    public void RegresoPicture(View view) {
        Intent picture = new Intent (this, Picture.class);
        startActivity(picture);
    }
}