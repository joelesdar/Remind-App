package com.example.remind_app.twins;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.MainActivity;
import com.example.remind_app.R;

public class videoGuia extends AppCompatActivity {

    VideoView video;
    ImageButton regreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_guia);
        getSupportActionBar().hide();

        // establecer direccion del video
        video = (VideoView) findViewById(R.id.guiapicture);
        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoguiatwins));

        video.start();

        regreso = (ImageButton) findViewById(R.id.botonJuegoAtras);

        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegresoTwins(view);
            }
        });

    }

    /**Funcion para regresar al menu del juego twins**/
    public void RegresoTwins(View view) {
        Intent twins = new Intent(this, MainActivity.class);
        startActivity(twins);
    }

}