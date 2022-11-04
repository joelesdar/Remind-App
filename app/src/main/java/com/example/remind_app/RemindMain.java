package com.example.remind_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class RemindMain extends AppCompatActivity {

    public static void main(String[] args) {
        SpringApplication.run(RemindMain.class, args);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}


