package com.example.remind_app.picture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.Picture;
import com.example.remind_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

public class pictureGameScreen extends AppCompatActivity {

    // Imagenes que se mostraran en pantalla
    ImageView image1, image2, image3, image4;

    Button instrucciones;

    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_game_screen);
        getSupportActionBar().hide();

        image1 = (ImageView) findViewById(R.id.imagen1);
        image2 = (ImageView) findViewById(R.id.imagen2);
        image3 = (ImageView) findViewById(R.id.imagen3);
        image4 = (ImageView) findViewById(R.id.imagen4);

        time = (TextView) findViewById(R.id.gameTime);

        instrucciones = findViewById(R.id.botonInstruccionesPicture2);

        /** Seleccion de imagenes aleatorias **/
        RandomImages();
        iniciarTiempo();

        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInstrucciones();
                System.out.println("Mostrar las instrucciones...");
            }
        });


    }

    /** Seleccion de imagenes aleatorias **/
    private void RandomImages () {
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

        Random r = new Random();

        int image = imagenes[r.nextInt(imagenes.length - 1)];
        image1.setImageResource(image);

        image = imagenes[r.nextInt(imagenes.length - 1)];
        image2.setImageResource(image);

        image = imagenes[r.nextInt(imagenes.length - 1)];
        image3.setImageResource(image);

        image = imagenes[r.nextInt(imagenes.length - 1)];
        image4.setImageResource(image);
    }

    // Temporizador
    private void iniciarTiempo(){
        long segs = 5;

        CountDownTimer contador = new CountDownTimer(segs, 5000) {
            @Override
            public void onTick(long l) {
                long tiempo = l;

                String timeJuego = String.format("%02d", tiempo);

                time.setText("Tiempo restante: "+timeJuego+" seg");
            }

            @Override
            public void onFinish() {
                Toast.makeText(pictureGameScreen.this, "Se acabo el tiempo", Toast.LENGTH_SHORT).show();
                time.setText("");
            }
        }.start();
    }


    /** Funcion para regresar al menu del juego picture */
    public void RegresoPicture(View view) {
        Intent picture = new Intent (this, Picture.class);
        startActivity(picture);
    }

    /** Mostrar las instrucciones del juego**/
    public void mostrarInstrucciones(){
        String instruccionesLeidas = new String();
        try {
            instruccionesLeidas = leerInstrucciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder instrucciones = new AlertDialog.Builder(pictureGameScreen.this);
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
}