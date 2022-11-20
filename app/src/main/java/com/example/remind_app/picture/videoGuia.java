package com.example.remind_app.picture;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.R;

public class videoGuia extends AppCompatActivity {

    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_guia);
        getSupportActionBar().hide();

        video = (VideoView) findViewById(R.id.guiapicture);
        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoguiapicture));

        video.start();

    }
}