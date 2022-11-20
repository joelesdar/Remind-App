package com.example.remind_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.picture.pictureGameScreen;
import com.example.remind_app.picture.videoGuia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Picture extends AppCompatActivity {

    Button instrucciones, video;
    Context contexto = this;

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
                mostrarInstrucciones();
                System.out.println("Mostrar las instrucciones...");
            }
        });

//        video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                new pictureVideoGuia(contexto);
//                System.out.println("Mostrar Video Guía...");
//            }
//        });

    }

    /** Regresar al menu principal **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }

    /** Ingresar al juego **/
    public void IngresoJuegoPicture (View view) {
        Intent game = new Intent (this, pictureGameScreen.class);
        startActivity(game);
    }

    /** Mostrar las instrucciones del juego**/
    public void mostrarInstrucciones(){
        String instruccionesLeidas = new String();
        try {
            instruccionesLeidas = leerInstrucciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder instrucciones = new AlertDialog.Builder(Picture.this);
        instrucciones.setMessage(instruccionesLeidas)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = instrucciones.create();
        titulo.setTitle("PICTURE");
        titulo.show();
    }

    /** Traer las instrucciones de juego desde txt **/
    public String leerInstrucciones()throws IOException{
        String sInstrucciones = "";
        int id = getResources().getIdentifier("instruccionespicture","raw",getPackageName());
        InputStream is = this.getResources().openRawResource(id);
        Scanner txtInstrucciones = new Scanner(new InputStreamReader(is));

        while(txtInstrucciones.hasNextLine()){
            sInstrucciones+=txtInstrucciones.nextLine()+"\n";
        }
        is.close();
        return sInstrucciones;
    }

    /** Funcion para ingresar al juego twins */
    public void Ingresoguia(View view) {
        Intent guia = new Intent (this, videoGuia.class);
        startActivity(guia);
    }



}