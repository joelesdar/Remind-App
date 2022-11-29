package com.example.remind_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remind_app.picture.pictureGameScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TimerTask;

public class followMeGameScreen extends AppCompatActivity {

    // Imagenes que se mostraran en pantalla
    ImageView image1, image2, image3, image4, t0, t1, t2, t3;
    ImageButton b0, b1, b2, b3, bj, bm, bc;
    TextView time, PuntuacionFollowMe, tiempoJuego, textT0, textT1, textT2, textT3, textB0, textB1, textB2, textB3, textS0, textS1, textS2, textS3, textS4, textS5, textS6, textEmp;
    Button instrucciones, botonVideo;

    int puntuacion=0;
    long segs = 5000;
    long tiempo = 60000;
    long tiempoDelay = 5000;
    int puntuacionMaxima, ultimaPuntuacion;
    String puntuacionString;
    int racha = 1;
    int mov = 0;
    Integer[] botones;
    String [] listaNotas = {"do","re","mi","fa","sol","la","si"};
    String [] notasM = {"","","","","","",""}; //notas maquina
    String [] notasJ = {"","","","","","",""}; //notas jugador
    Integer[] notasImpresas;
    Integer[] notasid = {8,8,8,8};
    String [] notas = {"","","",""};
    Handler h = new Handler();
    int contadorM = 0;
    int contadorJ = 0;
    final Handler handler = new Handler();



    ArrayList<ImageButton> combinacionBotones = new ArrayList<ImageButton>();
    ArrayList<ImageView> fondos = new ArrayList<ImageView>();

    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followme_game_screen);
        getSupportActionBar().hide();
        establecerPuntuacionMaxima();
        System.out.println(mAuth.getCurrentUser().getEmail());

        botonVideo = findViewById(R.id.botonVideoguia);

        //_________________________________________________________________________________________________________
        /**Listener para mostrar el video guia en un pop-up**/
        botonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(followMeGameScreen.this);// add here your class name
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_video_guia);//add your own xml with defied with and height of videoview
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                final VideoView videoPicture = (VideoView) dialog.findViewById(R.id.guiapicture);
                Uri uriPath= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoguiafollowme);
                videoPicture.setVideoURI(uriPath);
                System.out.println(videoPicture.isPlaying());
                videoPicture.start();
                TimerTask tiempo = new TimerTask() {
                    @Override
                    public void run() {

                    }
                };
                /**Reinicia el video cuando se termina**/
                videoPicture.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoPicture.start();
                    };
                });
            }
        });

        /** Escoge las notas que se usarán en la partida **/

        int n = (int)(Math.random()*7);
        notasid[0] = n;
        while (n==notasid[0] || n==notasid[1] || n==notasid[2] || n==notasid[3]){
            n = (int)(Math.random()*7);
        }
        notasid[1] = n;
        while (n==notasid[0] || n==notasid[1] || n==notasid[2] || n==notasid[3]){
            n = (int)(Math.random()*7);
        }
        notasid[2] = n;
        while (n==notasid[0] || n==notasid[1] || n==notasid[2] || n==notasid[3]){
            n = (int)(Math.random()*7);
        }
        notasid[3] = n;

        notas[0] = listaNotas[notasid[0]];
        notas[1] = listaNotas[notasid[1]];
        notas[2] = listaNotas[notasid[2]];
        notas[3] = listaNotas[notasid[3]];

        /** Asignando las notas que se usarán en la partida **/

        textT0 = (TextView) findViewById(R.id.textT0);
        textT0.setText(notas[0]);
        textB0 = (TextView) findViewById(R.id.textB0);
        textB0.setText(notas[0]);

        textT1 = (TextView) findViewById(R.id.textT1);
        textT1.setText(notas[1]);
        textB1 = (TextView) findViewById(R.id.textB1);
        textB1.setText(notas[1]);

        textT2 = (TextView) findViewById(R.id.textT2);
        textT2.setText(notas[2]);
        textB2 = (TextView) findViewById(R.id.textB2);
        textB2.setText(notas[2]);

        textT3 = (TextView) findViewById(R.id.textT3);
        textT3.setText(notas[3]);
        textB3 = (TextView) findViewById(R.id.textB3);
        textB3.setText(notas[3]);

        b0 = (ImageButton) findViewById(R.id.b0);
        b1 = (ImageButton) findViewById(R.id.b1);
        b2 = (ImageButton) findViewById(R.id.b2);
        b3 = (ImageButton) findViewById(R.id.b3);
        bj = (ImageButton) findViewById(R.id.bj);
        bm = (ImageButton) findViewById(R.id.bm);
        bc = (ImageButton) findViewById(R.id.bc);

        botones = new Integer[]{
                R.id.b0, R.id.b1, R.id.b2, R.id.b3
        };


        textS0 = (TextView) findViewById(R.id.textS0);
        textS1 = (TextView) findViewById(R.id.textS1);
        textS2 = (TextView) findViewById(R.id.textS2);
        textS3 = (TextView) findViewById(R.id.textS3);
        textS4 = (TextView) findViewById(R.id.textS4);
        textS5 = (TextView) findViewById(R.id.textS5);
        textS6 = (TextView) findViewById(R.id.textS6);
        textEmp = (TextView) findViewById(R.id.textEmp); // este solo se usa para organizar los otros en el front end

        notasImpresas = new Integer[]{
                R.id.textS0, R.id.textS1, R.id.textS2, R.id.textS3, R.id.textS4,
                R.id.textS5, R.id.textS6
        }; //notas que se imprimen

        t0 = (ImageView) findViewById(R.id.t0);
        t1 = (ImageView) findViewById(R.id.t1);
        t2 = (ImageView) findViewById(R.id.t2);
        t3 = (ImageView) findViewById(R.id.t3);

        //_________________________________________________________________________________________________________

        /** Funciones de juego **/
        //IniciarTiempoJuego();
        //IniciarTiempo();

        /**Fondos grises para el juego**/
        time = (TextView) findViewById(R.id.gameTime);
        tiempoJuego = (TextView) findViewById(R.id.tiempoJuego);

        PuntuacionFollowMe = (TextView) findViewById(R.id.PuntuacionFollowMe);

        instrucciones = findViewById(R.id.botonInstruccionesPicture2);

        fondos.add(image1);
        fondos.add(image2);
        fondos.add(image3);
        fondos.add(image4);

        /**Eventos para cada botón**/


        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInstrucciones();
            }
        });

    }

    //___________________________________________________________________________________________


    public void clickNota(View v){

        int numBoton = Arrays.asList(botones).indexOf(v.getId());

        if(contadorJ <= contadorM) {
            notasJ[contadorJ] = notas[numBoton]; //esto evita que se exceda la cantidad de notas que se estan jugando y todo haga boom
        }

        //imprime las notas
        if(contadorJ == 0) {
            textS0.setText(notasJ[0]);
        }
        if(contadorJ == 1) {
            textS1.setText(notasJ[1]);
        }
        if(contadorJ == 2) {
            textS2.setText(notasJ[2]);
        }
        if(contadorJ == 3) {
            textS3.setText(notasJ[3]);
        }
        if(contadorJ == 4) {
            textS4.setText(notasJ[4]);
        }
        if(contadorJ == 5) {
            textS5.setText(notasJ[5]);
        }
        if(contadorJ == 6) {
            textS6.setText(notasJ[6]);
        }

        contadorJ+=1;

        //brillo en las teclas del teclado
        brilloTecla(numBoton);

        //sonido de las teclas
        repNota(notas[numBoton]);

    }

    public void compTurnoMaquina(View v){
        if(contadorM < 6){
            turnoMaquina(v);
        }else{
            mostrarResultadoPartida();
        }
    }

    public void turnoMaquina(View v){

        b0.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        bm.setVisibility(View.INVISIBLE);
        bc.setVisibility(View.INVISIBLE);
        textS0.setText("");
        textS1.setText("");
        textS2.setText("");
        textS3.setText("");
        textS4.setText("");
        textS5.setText("");
        textS6.setText("");
        textS0.setTextColor(getResources().getColor(R.color.white));
        textS1.setTextColor(getResources().getColor(R.color.white));
        textS2.setTextColor(getResources().getColor(R.color.white));
        textS3.setTextColor(getResources().getColor(R.color.white));
        textS4.setTextColor(getResources().getColor(R.color.white));
        textS5.setTextColor(getResources().getColor(R.color.white));
        textS6.setTextColor(getResources().getColor(R.color.white));

        contadorJ = 0;

        //este for agrega una nota extra en cada ronda
        for(int i=0;i <= contadorM+1;i++){
            if(notasM[i]=="") {
                notasM[i] = notas[(int) (Math.random() * 4)];
            }
        }

        contadorM+=1;

        for(int i=0;i<=contadorM;i++){

        }

        //el siguiente monstruo imprime una a una las notas de la maquina en intervalos de 1 segundo
        if(contadorM >= 0){
            textS0.setText(notasM[0]);
            repNota(notasM[0]);
            brilloTecla(Arrays.asList(notas).indexOf(notasM[0]));
            if(contadorM >= 1){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textS1.setText(notasM[1]);
                        repNota(notasM[1]);
                        brilloTecla(Arrays.asList(notas).indexOf(notasM[1]));
                        if(contadorM >= 2){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textS2.setText(notasM[2]);
                                    repNota(notasM[2]);
                                    brilloTecla(Arrays.asList(notas).indexOf(notasM[2]));
                                    if(contadorM >= 3){
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                textS3.setText(notasM[3]);
                                                repNota(notasM[3]);
                                                brilloTecla(Arrays.asList(notas).indexOf(notasM[3]));
                                                if(contadorM >= 4){
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            textS4.setText(notasM[4]);
                                                            repNota(notasM[4]);
                                                            brilloTecla(Arrays.asList(notas).indexOf(notasM[4]));
                                                            if(contadorM >= 5){
                                                                Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        textS5.setText(notasM[5]);
                                                                        repNota(notasM[5]);
                                                                        brilloTecla(Arrays.asList(notas).indexOf(notasM[5]));
                                                                        if(contadorM >= 6){
                                                                            Handler handler = new Handler();
                                                                            handler.postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    textS6.setText(notasM[6]);
                                                                                    repNota(notasM[6]);
                                                                                    brilloTecla(Arrays.asList(notas).indexOf(notasM[6]));
                                                                                }
                                                                            }, 1000);
                                                                        }
                                                                    }
                                                                }, 1000);
                                                            }
                                                        }
                                                    }, 1000);
                                                }
                                            }
                                        }, 1000);
                                    }
                                }
                            }, 1000);
                        }
                    }
                }, 1000);
            }
        }

        Handler handler = new Handler(); //aca muestra el boton de iniciar el turno del jugador cuando acaba de imprimir las notas
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bj.setVisibility(View.VISIBLE);
            }
        }, ((contadorM+1)*1000));

    }

    public void turnoJugador(View v){

        b0.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        textB0.setVisibility(View.VISIBLE);
        textB1.setVisibility(View.VISIBLE);
        textB2.setVisibility(View.VISIBLE);
        textB3.setVisibility(View.VISIBLE);
        bj.setVisibility(View.INVISIBLE);
        bm.setVisibility(View.INVISIBLE);
        bc.setVisibility(View.VISIBLE);
        textS0.setText("");
        textS1.setText("");
        textS2.setText("");
        textS3.setText("");
        textS4.setText("");
        textS5.setText("");
        textS6.setText("");

    }

    public void comprobar(View v){

        textB0.setVisibility(View.INVISIBLE);
        textB1.setVisibility(View.INVISIBLE);
        textB2.setVisibility(View.INVISIBLE);
        textB3.setVisibility(View.INVISIBLE);
        b0.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        bj.setVisibility(View.INVISIBLE);
        bc.setVisibility(View.INVISIBLE);
        textS0.setText("");
        textS1.setText("");
        textS2.setText("");
        textS3.setText("");
        textS4.setText("");
        textS5.setText("");
        textS6.setText("");
        MediaPlayer doAudio = MediaPlayer.create(this, R.raw.nota_do);
        MediaPlayer reAudio = MediaPlayer.create(this, R.raw.re);
        MediaPlayer miAudio = MediaPlayer.create(this, R.raw.mi);
        MediaPlayer faAudio = MediaPlayer.create(this, R.raw.fa);
        MediaPlayer solAudio = MediaPlayer.create(this, R.raw.sol);
        MediaPlayer laAudio = MediaPlayer.create(this, R.raw.la);
        MediaPlayer siAudio = MediaPlayer.create(this, R.raw.si);

        //COMPROBADOR comprueba una a una las notas del jugador con las de la maquina para revisar si coinciden
        if(contadorM >= 0){
            textS0.setText(notasJ[0]);
            repNota(notasM[0]);
            brilloTecla(Arrays.asList(notas).indexOf(notasM[0]));
            if(notasM[0]==notasJ[0]){
                textS0.setTextColor(getResources().getColor(R.color.Secondary));
                puntuacion += 20;
                PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
            }else{
                textS0.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                mostrarResultadoPartida();
            }
            if(contadorM >= 1){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textS1.setText(notasJ[1]);
                        repNota(notasM[1]);
                        brilloTecla(Arrays.asList(notas).indexOf(notasM[1]));
                        if(notasM[1]==notasJ[1]){
                            textS1.setTextColor(getResources().getColor(R.color.Secondary));
                            puntuacion += 20;
                            PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                        }else{
                            textS1.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                            mostrarResultadoPartida();
                        }
                        if(contadorM >= 2){
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textS2.setText(notasJ[2]);
                                    repNota(notasM[2]);
                                    brilloTecla(Arrays.asList(notas).indexOf(notasM[2]));
                                    if(notasM[2]==notasJ[2]){
                                        textS2.setTextColor(getResources().getColor(R.color.Secondary));
                                        puntuacion += 20;
                                        PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                                    }else{
                                        textS2.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                                        mostrarResultadoPartida();
                                    }
                                    if(contadorM >= 3){
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                textS3.setText(notasJ[3]);
                                                repNota(notasM[3]);
                                                brilloTecla(Arrays.asList(notas).indexOf(notasM[3]));
                                                if(notasM[3]==notasJ[3]){
                                                    textS3.setTextColor(getResources().getColor(R.color.Secondary));
                                                    puntuacion += 20;
                                                    PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                                                }else{
                                                    textS3.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                                                    mostrarResultadoPartida();
                                                }
                                                if(contadorM >= 4){
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            textS4.setText(notasJ[4]);
                                                            repNota(notasM[4]);
                                                            brilloTecla(Arrays.asList(notas).indexOf(notasM[4]));
                                                            if(notasM[4]==notasJ[4]){
                                                                textS4.setTextColor(getResources().getColor(R.color.Secondary));
                                                                puntuacion += 20;
                                                                PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                                                            }else{
                                                                textS4.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                                                                mostrarResultadoPartida();
                                                            }
                                                            if(contadorM >= 5){
                                                                Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        textS5.setText(notasJ[5]);
                                                                        repNota(notasM[5]);
                                                                        brilloTecla(Arrays.asList(notas).indexOf(notasM[5]));
                                                                        if(notasM[5]==notasJ[5]){
                                                                            textS5.setTextColor(getResources().getColor(R.color.Secondary));
                                                                            puntuacion += 20;
                                                                            PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                                                                        }else{
                                                                            textS5.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                                                                            mostrarResultadoPartida();
                                                                        }
                                                                        if(contadorM >= 6){
                                                                            Handler handler = new Handler();
                                                                            handler.postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    textS6.setText(notasJ[6]);
                                                                                    repNota(notasM[6]);
                                                                                    brilloTecla(Arrays.asList(notas).indexOf(notasM[6]));
                                                                                    if(notasM[6]==notasJ[6]){
                                                                                        textS6.setTextColor(getResources().getColor(R.color.Secondary));
                                                                                        puntuacion += 20;
                                                                                        PuntuacionFollowMe.setText("Puntuación: "+ puntuacion);
                                                                                    }else{
                                                                                        textS6.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
                                                                                        mostrarResultadoPartida();
                                                                                    }
                                                                                }
                                                                            }, 1000);
                                                                        }
                                                                    }
                                                                }, 1000);
                                                            }
                                                        }
                                                    }, 1000);
                                                }
                                            }
                                        }, 1000);
                                    }
                                }
                            }, 1000);
                        }
                    }
                }, 1000);
            }
        }

        Handler handler = new Handler(); //aca muestra el boton de iniciar el turno del jugador cuando acaba de imprimir las notas
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bm.setVisibility(View.VISIBLE);
            }
        }, ((contadorM+1)*1000));
    }

    public void brilloTecla(int t){
        if(t == 0){
            t0.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    t0.setVisibility(View.INVISIBLE);
                }
            }, 500);
        }

        if(t == 1){
            t1.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    t1.setVisibility(View.INVISIBLE);
                }
            }, 500);
        }

        if(t == 2){
            t2.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    t2.setVisibility(View.INVISIBLE);
                }
            }, 500);
        }

        if(t == 3){
            t3.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    t3.setVisibility(View.INVISIBLE);
                }
            }, 500);
        }
    }

    public void repNota(String n){
        MediaPlayer doAudio = MediaPlayer.create(this, R.raw.nota_do);
        MediaPlayer reAudio = MediaPlayer.create(this, R.raw.re);
        MediaPlayer miAudio = MediaPlayer.create(this, R.raw.mi);
        MediaPlayer faAudio = MediaPlayer.create(this, R.raw.fa);
        MediaPlayer solAudio = MediaPlayer.create(this, R.raw.sol);
        MediaPlayer laAudio = MediaPlayer.create(this, R.raw.la);
        MediaPlayer siAudio = MediaPlayer.create(this, R.raw.si);

        //sonido de las teclas
        if(n == "do"){
            doAudio.start();
        }
        if(n == "re"){
            reAudio.start();
        }
        if(n == "mi"){
            miAudio.start();
        }
        if(n == "fa"){
            faAudio.start();
        }
        if(n == "sol"){
            solAudio.start();
        }
        if(n == "la"){
            laAudio.start();
        }
        if(n == "si"){
            siAudio.start();
        }
    }





    //___________________________________________________________________________________________

    /**Temporizador partida**/
    private void IniciarTiempoJuego(){
        CountDownTimer contador = new CountDownTimer(tiempo, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l;
                long segundos = tiempo/1000;
                String timeJuego = String.format("%02d", segundos);

                tiempoJuego.setText(timeJuego);
            }
            @Override
            public void onFinish() {
                mostrarResultadoPartida();
            }
        }.start();
    }

    /**Temporizador ronda**/
    private void IniciarTiempo(){
        CountDownTimer contador = new CountDownTimer(segs, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l;
                long segundos = tiempo/1000;
                String timeJuego = String.format("%02d", segundos);

                time.setText("Tiempo restante: "+timeJuego);
    /*                bloquearP();
                    bloquearA();*/
            }
            @Override
            public void onFinish() {
                time.setText("");
    /*                mostrarFondo();
                    esconderP();
                    bloquearP();
                    mostrarA();
                    desbloquearA();*/
            }
        }.start();
    }


    /**Funcion para regresar al menu del juego followMe**/
    public void RegresoFollowMe(View view) {
        Intent followMe = new Intent (this, followMe.class);
        startActivity(followMe);
    }

    /**Funcion para mostrar resultado al final de la partida**/
    public void mostrarResultadoPartida(){
        establecerPuntuaciones();
        AlertDialog.Builder resultado = new AlertDialog.Builder(com.example.remind_app.followMeGameScreen.this);
        resultado.setMessage(establecerMensaje(puntuacion,puntuacionMaxima))
                .setCancelable(false)
                .setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent followMe = new Intent(com.example.remind_app.followMeGameScreen.this, followMe.class);
                        startActivity(followMe);
                    }
                });
        AlertDialog titulo = resultado.create();
        titulo.setTitle("FollowMe");
        titulo.show();
    }

    public void establecerPuntuaciones(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("FollowMe").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                                                     @Override
                                                                                                                                                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                                         Map<String, Object> Puntuaciones = new HashMap<>();
                                                                                                                                                         if(puntuacion>puntuacionMaxima){
                                                                                                                                                             Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacion));
                                                                                                                                                         }else{
                                                                                                                                                             Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacionMaxima));
                                                                                                                                                         }
                                                                                                                                                         Puntuaciones.put("UltimaPuntuacion", Integer.toString(puntuacion));
                                                                                                                                                         db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("FollowMe").set(Puntuaciones);
                                                                                                                                                     }
                                                                                                                                                 }
        );
    }

    public void establecerPuntuacionMaxima(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("FollowMe").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String MaxPuntuacion = documentSnapshot.getString("PuntuacionMaxima");
                    puntuacionMaxima = Integer.parseInt(MaxPuntuacion);
                }
            }
        });
    }

    public String establecerMensaje(int puntuacion, int puntuacionMaxima){
        String mensaje;
        if(puntuacion>puntuacionMaxima){
            mensaje = "¡Felicidades, has superado tu puntuación maxima, has llegado a los "+puntuacion+" puntos!";
        }else if(puntuacion>0){
            mensaje = "¡Felicidades, has obtenido "+puntuacion+" puntos!";
        }else{
            mensaje = "Has obtenido "+puntuacion+" puntos, vuelve a jugar";
        }
        return mensaje;
    }

    /** Mostrar las instrucciones del juego**/
    public void mostrarInstrucciones(){
        String instruccionesLeidas = new String();
        try {
            instruccionesLeidas = leerInstrucciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder instrucciones = new AlertDialog.Builder(com.example.remind_app.followMeGameScreen.this);
        instrucciones.setMessage(instruccionesLeidas)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = instrucciones.create();
        titulo.setTitle("FollowMe");
        titulo.show();
    }

    /** Traer las instrucciones de juego desde txt **/
    public String leerInstrucciones()throws IOException{
        String sInstrucciones = "";
        int id = getResources().getIdentifier("instruccionesfollowme","raw",getPackageName());
        InputStream is = this.getResources().openRawResource(id);
        Scanner txtInstrucciones = new Scanner(new InputStreamReader(is));

        while(txtInstrucciones.hasNextLine()){
            sInstrucciones+=txtInstrucciones.nextLine()+"\n";
        }
        is.close();
        return sInstrucciones;
    }

}