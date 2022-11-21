package com.example.remind_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.remind_app.twins.videoGuia;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Juego extends MainActivity {

    //Variables vista
    ImageButton imb00, imb01, imb02, imb03, imb04, imb05, imb06, imb07, imb08, imb09, imb10, imb11, imb12, imb13, imb14, imb15, imb16, imb17, imb18, imb19;
    ImageButton[] tablero = new ImageButton[20];
    ImageButton imbAtras;
    Button botonReiniciar, botonInstrucciones, botonVideo;
    TextView textoPuntuacion, ultPuntuacion, tiempo;
    int puntuacion;
    int pares;
    int puntuacionMaxima;
    boolean isOn = false;
    Thread cronometro;
    int mili=0,min=0,seg=0,racha=1;
    Handler h = new Handler();

    //Imagenes
    int[] imagenes;
    int oculto;

    //Variables juego
    ArrayList<Integer> arrayRandom;
    ImageButton primero;
    int nPrimero, nSegundo;
    boolean bloqueo = false;
    final Handler handler = new Handler();

    //Firebase
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        establecerPuntuacionMaxima();
        getSupportActionBar().hide();
        iniciar();
    }

    //Carga funcinoal del tablero
    private void cargarTablero(){
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);
        imb04 = findViewById(R.id.boton04);
        imb05 = findViewById(R.id.boton05);
        imb06 = findViewById(R.id.boton06);
        imb07 = findViewById(R.id.boton07);
        imb08 = findViewById(R.id.boton08);
        imb09 = findViewById(R.id.boton09);
        imb10 = findViewById(R.id.boton10);
        imb11 = findViewById(R.id.boton11);
        imb12 = findViewById(R.id.boton12);
        imb13 = findViewById(R.id.boton13);
        imb14 = findViewById(R.id.boton14);
        imb15 = findViewById(R.id.boton15);
        imb16 = findViewById(R.id.boton16);
        imb17 = findViewById(R.id.boton17);
        imb18 = findViewById(R.id.boton18);
        imb19 = findViewById(R.id.boton19);

        tablero[0] = imb00;
        tablero[1] = imb01;
        tablero[2] = imb02;
        tablero[3] = imb03;
        tablero[4] = imb04;
        tablero[5] = imb05;
        tablero[6] = imb06;
        tablero[7] = imb07;
        tablero[8] = imb08;
        tablero[9] = imb09;
        tablero[10] = imb10;
        tablero[11] = imb11;
        tablero[12] = imb12;
        tablero[13] = imb13;
        tablero[14] = imb14;
        tablero[15] = imb15;
        tablero[16] = imb16;
        tablero[17] = imb17;
        tablero[18] = imb18;
        tablero[19] = imb19;
        System.out.println("Carga del tablero");
    }

    //Carga funcional botones
    private void cargarBotones(){
        botonReiniciar = findViewById(R.id.botonReiniciar);
        tiempo = (TextView)findViewById(R.id.tiempo);
        botonInstrucciones = findViewById(R.id.botonInstrucciones);
        botonVideo = findViewById(R.id.botonVideoguia);
        imbAtras = findViewById(R.id.botonJuegoAtras);

        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
                isOn = false;
                mili = seg = min= 0;
                tiempo.setText("Tiempo: 00:00");
                System.out.println("Reiniciar nivel");
            }
        });

        botonInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInstrucciones();
            }
        });
        //Se crea el thread del cronometro
        cronometro = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(isOn){
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        mili++;
                        if(mili==999){
                            seg++;
                            mili=0;
                        }
                        if(seg==59){
                            min++;
                            seg=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String m="", s="", mi="";
                                if (mili<10){
                                    m = "00"+mili;
                                }else if(mili<100){
                                    m = "0"+mili;
                                }else{
                                    m = ""+mili;
                                }
                                if(seg<10){
                                    s = "0"+seg;
                                }else{
                                    s = ""+seg;
                                }
                                if(min<10){
                                    mi="0"+min;
                                }else{
                                    mi = ""+min;
                                }
                                tiempo.setText("Tiempo: "+mi+":"+s);
                            }
                        });
                    }
                }
            }
        });
        cronometro.start();
    }

    //Carga texto puntuación
    private void cargarTexto(){
        textoPuntuacion = findViewById(R.id.puntuacion);
        puntuacion = 0;
        textoPuntuacion.setText("Puntuación: "+ puntuacion);
        System.out.println("Carga puntuacion del juego");
    }

    //Carga de las imagenes
    private void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.i0,
                R.drawable.i1,
                R.drawable.i2,
                R.drawable.i3,
                R.drawable.i4,
                R.drawable.i5,
                R.drawable.i6,
                R.drawable.i7,
                R.drawable.i8,
                R.drawable.i9,
                R.drawable.i10,
                R.drawable.i11
        };
        oculto = R.drawable.oculto;
        System.out.println("Carga de las imágenes");
    }

    //Aleatoriza las imágenes
    private ArrayList<Integer> ordenAleatorio(int longitud){
        ArrayList<Integer> resultado = new ArrayList<Integer>();
        for (int i = 0;i<longitud*2;i++){
            resultado.add(i % longitud);
        }
        int nelim1 = 0;
        int nelim2 = nelim1;
        while(nelim2 == nelim1){
            nelim1 = (int)(Math.random()*12);
            nelim2 = (int)(Math.random()*12);
        }
        System.out.println("Los numeros a eliminar son: "+nelim1+" y "+nelim2);;
        resultado.removeAll(Collections.singleton(nelim1));
        resultado.removeAll(Collections.singleton(nelim2));

        Collections.shuffle(resultado);
        System.out.println(Arrays.toString(resultado.toArray()));
        System.out.println(resultado);
        System.out.println("Aleatoriza las imágenes");
        return resultado;
    }

    //Metodo de comprobación
    private void comprobar(int i, final ImageButton imb){
        //Carta boca abajo
        if(primero == null){
            //se le da imagen y valores a la carta pulsada
            isOn = true;
            primero = imb;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayRandom.get(i)]);
            primero.setBackgroundColor(Color.rgb(111,247,150));
            primero.setEnabled(false);
            nPrimero = arrayRandom.get(i);
        //Segunda pulsacion, queda a la vista
        }else{
            bloqueo = true;
            imb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imb.setImageResource(imagenes[arrayRandom.get(i)]);
            imb.setBackgroundColor(Color.rgb(111,247,150));
            imb.setEnabled(false);
            nSegundo = arrayRandom.get(i);
            if(nPrimero == nSegundo){
                primero.setBackgroundColor(Color.rgb(247,246,111));
                imb.setBackgroundColor(Color.rgb(247,246,111));
                primero = null;
                bloqueo = false;
                pares++;
                puntuacion+=100*racha;
                racha++;
                textoPuntuacion.setText("Puntuación: "+puntuacion);
                System.out.println("Son par");
                if(pares == arrayRandom.size()/2){
                    isOn = false;
                    establecerPuntuaciones();
                    //Toast toast = Toast.makeText(getApplicationContext(),establecerMensaje(puntuacion,puntuacionMaxima), Toast.LENGTH_LONG);
                    //toast.show();
                    mostrarResultadoPartida();
                    System.out.println("Juego completado");
                }
            }else{
                //Espera de imagenes en pantalla de 1s
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(oculto);
                        primero.setEnabled(true);
                        imb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imb.setImageResource(oculto);
                        imb.setEnabled(true);
                        primero.setBackgroundColor(Color.rgb(247,161,161));
                        imb.setBackgroundColor(Color.rgb(247,161,161));
                        bloqueo = false;
                        primero = null;
                        if (pares>0){
                            puntuacion-=50;
                            racha=1;
                        }
                        textoPuntuacion.setText("Puntuación: "+puntuacion);
                    }
                },1000);
                System.out.println("No son par");
            }
        }
    }

    public void mostrarResultadoPartida(){
        establecerPuntuaciones();
        AlertDialog.Builder resultado = new AlertDialog.Builder(Juego.this);
        resultado.setMessage(establecerMensaje(puntuacion,puntuacionMaxima))
                .setCancelable(false)
                .setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent picture = new Intent(Juego.this, MainActivity.class);
                        startActivity(picture);
                    }
                });
        AlertDialog titulo = resultado.create();
        titulo.setTitle("Twins");
        titulo.show();
    }

    public String establecerMensaje(int puntuacion, int puntuacionMaxima){
        String mensaje;
        if(puntuacion>puntuacionMaxima){
            mensaje = "¡Felicidades, has superado tu puntuación maxima, has llegado a los "+puntuacion+" puntos!";
        }else if(puntuacion>0){
            mensaje = "¡Felicidades, has obtenido "+puntuacion+" puntos!";
        }else{
            mensaje = "Has obtenido "+puntuacion+" puntos, ¡pero aún se puede mejorar!";
        }
        return mensaje;
    }

    public void establecerPuntuaciones(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Twins").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  Map<String, Object> Puntuaciones = new HashMap<>();
                  if(puntuacion>puntuacionMaxima){
                      Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacion));
                  }else{
                      Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacionMaxima));
                  }
                  Puntuaciones.put("UltimaPuntuacion", Integer.toString(puntuacion));
                  db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Twins").set(Puntuaciones);
              }
          }
        );
    }

    public void establecerPuntuacionMaxima(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Twins").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String MaxPuntuacion = documentSnapshot.getString("PuntuacionMaxima");
                    puntuacionMaxima = Integer.parseInt(MaxPuntuacion);
                }
            }
        });
    }

    //Método principal
    private void iniciar(){
        cargarTablero();
        cargarBotones();
        cargarTexto();
        cargarImagenes();
        arrayRandom = ordenAleatorio(imagenes.length);

        //Se muestran las imágenes
        for(int i = 0;i< tablero.length;i++){
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(imagenes[arrayRandom.get(i)]);
            //tablero[i].setImageResource(fondo);
            System.out.println("Se muestran las imagenes por poco tiempo");
        }
        //Luego de medio segundo se ocultan las imágenes
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i< tablero.length;i++){
                    tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //tablero[i].setImageResource(imagenes[arrayRandom.get(i)]);
                    tablero[i].setImageResource(oculto);
                    tablero[i].setBackgroundColor(Color.rgb(247,161,161));
                    System.out.println("Se ocultan las imagenes");
                }
            }
        },500);

        //interascción
        for(int i = 0;i< tablero.length;i++){
            final int j = i;
            tablero[i].setEnabled(true);
            tablero[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!bloqueo){
                        comprobar(j, tablero[j]);
                        System.out.println("Listo para la comprobación");
                    }
                }
            });
        }
    }

    /** Funcion visualizar el video guia */
    public void Ingresoguia(View view) {
        Intent guia = new Intent (this, videoGuia.class);
        startActivity(guia);
    }

    /** Regresar al menu **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MainActivity.class);
        startActivity(menu);
    }

}
