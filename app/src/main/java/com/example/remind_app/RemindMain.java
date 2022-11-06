package com.example.remind_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class RemindMain extends AppCompatActivity {

//    public static void main(String[] args) {
//        SpringApplication.run(RemindMain.class, args);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

    }

    /** Funcion para ingresar a la aplciacion cuando se oprime el boton ingresar */
    public void Ingresar(View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }

}


