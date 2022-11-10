package com.example.remind_app.picture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.Picture;
import com.example.remind_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class pictureGameScreen extends AppCompatActivity {

    // Imagenes que se mostraran en pantalla
    ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    ImageView imguser1, imguser2, imguser3, imguser4;

    Button instrucciones;

    TextView time, PuntuacionPicture;

    int puntuacion=0;

    Rect area1, area2, area3, area4;

    long segs = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_game_screen);
        getSupportActionBar().hide();

        // imagenes del juego
        image1 = (ImageView) findViewById(R.id.imagen1);
        image2 = (ImageView) findViewById(R.id.imagen2);
        image3 = (ImageView) findViewById(R.id.imagen3);
        image4 = (ImageView) findViewById(R.id.imagen4);
        image5 = (ImageView) findViewById(R.id.imagen5);
        image6 = (ImageView) findViewById(R.id.imagen6);
        image7 = (ImageView) findViewById(R.id.imagen7);
        image8 = (ImageView) findViewById(R.id.imagen8);
        image5.setVisibility(View.INVISIBLE);
        image6.setVisibility(View.INVISIBLE);
        image7.setVisibility(View.INVISIBLE);
        image8.setVisibility(View.INVISIBLE);

        // imagenes con las que interactua el usuario
        imguser1 = (ImageView) findViewById(R.id.img1);
        imguser2 = (ImageView) findViewById(R.id.img2);
        imguser3 = (ImageView) findViewById(R.id.img3);
        imguser4 = (ImageView) findViewById(R.id.img4);

        time = (TextView) findViewById(R.id.gameTime);

        PuntuacionPicture = (TextView) findViewById(R.id.PuntuacionPicture);

        instrucciones = findViewById(R.id.botonInstruccionesPicture2);

        /** Funciones de juego **/
        RandomImages();
        iniciarTiempo();

        /**Seleccion de imagenes**/



    }

    /** Seleccion de imagenes aleatorias **/
    private void RandomImages () {
        int[] imagenes = {R.drawable.picturefigura1,
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

        int imageshow1 = imagenes[r.nextInt(imagenes.length - 1)];
        image1.setImageResource(imageshow1);
        imguser1.setImageResource(imageshow1);

        int imageshow2 = imagenes[r.nextInt(imagenes.length - 1)];
        image2.setImageResource(imageshow2);
        imguser2.setImageResource(imageshow2);

        int imageshow3 = imagenes[r.nextInt(imagenes.length - 1)];
        image3.setImageResource(imageshow3);
        imguser3.setImageResource(imageshow3);

        int imageshow4 = imagenes[r.nextInt(imagenes.length - 1)];
        image4.setImageResource(imageshow4);
        imguser4.setImageResource(imageshow4);

    }



    /**Temporizador**/
    private void iniciarTiempo(){

        CountDownTimer contador = new CountDownTimer(segs, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l;
                long segundos = tiempo/1000;

                String timeJuego = String.format("%02d", segundos);

                time.setText("Tiempo restante: "+timeJuego+" seg");
            }

            @Override
            public void onFinish() {
                time.setText("");
                // ocultar imagenes
                image1.setVisibility(View.INVISIBLE);
                image2.setVisibility(View.INVISIBLE);
                image3.setVisibility(View.INVISIBLE);
                image4.setVisibility(View.INVISIBLE);
                image5.setVisibility(View.VISIBLE);
                image6.setVisibility(View.VISIBLE);
                image7.setVisibility(View.VISIBLE);
                image8.setVisibility(View.VISIBLE);

                imguser1.setVisibility(View.VISIBLE);
                imguser2.setVisibility(View.VISIBLE);
                imguser3.setVisibility(View.VISIBLE);
                imguser4.setVisibility(View.VISIBLE);

                generarArea();
                //verificarImagenes();

                imguser1.setClickable(true);
                imguser2.setClickable(true);
                imguser3.setClickable(true);
                imguser4.setClickable(true);
            }
        }.start();
    }

    private Rect getLocationOnScreen(View mView) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mView.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mView.getWidth();
        mRect.bottom = location[1] + mView.getHeight();

        return mRect;
    }

    private void generarArea(){
        area1 = getLocationOnScreen(image1);
        area2 = getLocationOnScreen(image2);
        area3 = getLocationOnScreen(image3);
        area4 = getLocationOnScreen(image4);
    }

//    public View onClick(View v) {
//        View vista = v;
//        return vista;
//    }

//    private void verificarImagenes(){
//        int[] values = new int[2];
//        view.getLocationOnScreen(values);
//        if(area1.contains(values1[0],values1[1])){
//            puntuacion+=50;
//            image1.setVisibility(View.VISIBLE);
//            image5.setVisibility(View.INVISIBLE);
//        }else if(area2.contains(values2[0],values2[1])){
//            puntuacion+=50;
//            image2.setVisibility(View.VISIBLE);
//            image6.setVisibility(View.INVISIBLE);
//        }else if(area2.contains(values3[0],values3[1])){
//            puntuacion+=50;
//            image3.setVisibility(View.VISIBLE);
//            image7.setVisibility(View.INVISIBLE);
//        }else if(area2.contains(values4[0],values4[1])){
//            puntuacion+=50;
//            image4.setVisibility(View.VISIBLE);
//            image8.setVisibility(View.INVISIBLE);
//        }else{
//            puntuacion-=25;
//        }
//        PuntuacionPicture.setText("PuntuaciÃ³n: "+ (puntuacion-50));
//    }

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