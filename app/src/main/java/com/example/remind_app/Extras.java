package com.example.remind_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Extras extends AppCompatActivity {

    CircleImageView julien, mort, cabo, kowalski, skipper, rico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        getSupportActionBar().hide();

        julien = (CircleImageView) findViewById(R.id.reyjulien);
        mort = (CircleImageView) findViewById(R.id.mort);
        cabo = (CircleImageView) findViewById(R.id.cabo);
        kowalski = (CircleImageView) findViewById(R.id.kowalski);
        skipper = (CircleImageView) findViewById(R.id.skipper);
        rico = (CircleImageView) findViewById(R.id.rico);

        // Mostrar info Rey Julien
        julien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programador Front-End");
                builder.setTitle("Joel Diaz");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        // Mostrar info Mort
        mort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programadora Back-End");
                builder.setTitle("Tatiana Alvarez");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        // Mostrar info Cabo
        cabo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programador Bases de datos");
                builder.setTitle("Fabian Chaparro");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        // Mostrar info Kowalski
        kowalski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programador Back-End");
                builder.setTitle("Santiago Rodriguez");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        // Mostrar info Skipper
        skipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programador Back-End");
                builder.setTitle("Alejandro Duarte");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        // Mostrar info Rico
        rico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(Extras.this).create();
                builder.setMessage("Programador Bases de datos");
                builder.setTitle("Nicolas Hernandez");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cool",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }

    /** Regresar al menu principal **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }

}