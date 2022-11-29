package com.example.remind_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class pictureGameScreen extends AppCompatActivity {

    // Imagenes que se mostraran en pantalla
    ImageView image1, image2, image3, image4;
    ImageButton botonP1, botonP2, botonP3, botonP4, botonA1, botonA2, botonA3, botonA4;
    TextView time, PuntuacionPicture, tiempoJuego;
    Button instrucciones;

    int parejasRestantes;
    int puntuacion=0;
    long segs = 5000;
    long tiempo = 10000;

    int puntuacionMaxima, ultimaPuntuacion;
    String puntuacionString;
    int racha = 1;
    int mov = 1;

    ArrayList<ImageButton> botonesDisponiblesP = new ArrayList<ImageButton>();
    ArrayList<ImageButton> botonesDisponiblesA = new ArrayList<ImageButton>();
    ArrayList<ImageButton> combinacionBotones = new ArrayList<ImageButton>();
    ArrayList<ImageView> fondos = new ArrayList<ImageView>();

    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_game_screen);
        getSupportActionBar().hide();
        establecerPuntuacionMaxima();
        System.out.println(mAuth.getCurrentUser().getEmail());

        /**Fondos grises para el juego**/
        image1 = (ImageView) findViewById(R.id.teclado);
        image2 = (ImageView) findViewById(R.id.imagen2);
        image3 = (ImageView) findViewById(R.id.imagen3);
        image4 = (ImageView) findViewById(R.id.imagen4);

        time = (TextView) findViewById(R.id.gameTime);
        tiempoJuego = (TextView) findViewById(R.id.tiempoJuego);

        PuntuacionPicture = (TextView) findViewById(R.id.PuntuacionFollowMe);

        instrucciones = findViewById(R.id.botonInstruccionesPicture2);

        /**Todos los botones que se van a usar en el juego**/
        botonP1 = (ImageButton) findViewById(R.id.imageButtonP1);
        botonP2 = (ImageButton) findViewById(R.id.imageButtonP2);
        botonP3 = (ImageButton) findViewById(R.id.imageButtonP3);
        botonP4 = (ImageButton) findViewById(R.id.imageButtonP4);
        botonA1 = (ImageButton) findViewById(R.id.imageButtonA1);
        botonA2 = (ImageButton) findViewById(R.id.imageButtonA2);
        botonA3 = (ImageButton) findViewById(R.id.imageButtonA3);
        botonA4 = (ImageButton) findViewById(R.id.imageButtonA4);

        fondos.add(image1);
        fondos.add(image2);
        fondos.add(image3);
        fondos.add(image4);


        /** Funciones de juego **/
        IniciarTiempoJuego();
        RandomImages();
        bloquearP();
        bloquearA();
        IniciarTiempo();

        /**Eventos para cada botón**/
        botonA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonA1.setBackgroundColor(Color.parseColor("#17972B"));
                combinacionBotones.add(botonA1);
                bloquearA();
                desbloquearP();
                mostrarP();
            }
        });
        botonA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonA2.setBackgroundColor(Color.parseColor("#17972B"));
                combinacionBotones.add(botonA2);
                bloquearA();
                desbloquearP();
                mostrarP();
            }
        });
        botonA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonA3.setBackgroundColor(Color.parseColor("#17972B"));
                combinacionBotones.add(botonA3);
                bloquearA();
                desbloquearP();
                mostrarP();
            }
        });
        botonA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonA4.setBackgroundColor(Color.parseColor("#17972B"));
                combinacionBotones.add(botonA4);
                bloquearA();
                desbloquearP();
                mostrarP();
            }
        });
        botonP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinacionBotones.add(botonP1);
                if(combinacionBotones.get(0).getTag().equals(combinacionBotones.get(1).getTag())) {
                    puntuacion+=50*racha;
                    racha++;
                    parejasRestantes--;
                    establecerBien();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                    image1.setVisibility(View.INVISIBLE);

                }else{
                    racha=1;
                    puntuacion-=25;
                    establecerMal();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                }
                bloquearP();
                esconderP();
                desbloquearA();
                if(parejasRestantes==0){
                    RandomImages();
                    IniciarTiempo();
                    esconderA();
                }
            }
        });
        botonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinacionBotones.add(botonP2);
                if(combinacionBotones.get(0).getTag().equals(combinacionBotones.get(1).getTag())){
                    puntuacion+=50*racha;
                    racha++;
                    parejasRestantes--;
                    establecerBien();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                    image2.setVisibility(View.INVISIBLE);
                }else{
                    racha=1;
                    puntuacion-=25;
                    establecerMal();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                }
                bloquearP();
                esconderP();
                desbloquearA();
                if(parejasRestantes==0){
                    RandomImages();
                    IniciarTiempo();
                    esconderA();
                }
            }
        });
        botonP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinacionBotones.add(botonP3);
                if(combinacionBotones.get(0).getTag().equals(combinacionBotones.get(1).getTag())){
                    puntuacion+=50*racha;
                    racha++;
                    parejasRestantes--;
                    establecerBien();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                    image3.setVisibility(View.INVISIBLE);
                }else{
                    racha=1;
                    puntuacion-=25;
                    establecerMal();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                }
                bloquearP();
                esconderP();
                desbloquearA();
                if(parejasRestantes==0){
                    RandomImages();
                    IniciarTiempo();
                    esconderA();
                }
            }
        });
        botonP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinacionBotones.add(botonP4);
                if(combinacionBotones.get(0).getTag().equals(combinacionBotones.get(1).getTag())){
                    puntuacion+=50*racha;
                    racha++;
                    parejasRestantes--;
                    establecerBien();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                    image4.setVisibility(View.INVISIBLE);
                }else{
                    racha=1;
                    puntuacion-=25;
                    establecerMal();
                    PuntuacionPicture.setText("Puntuación: " + puntuacion);
                }
                bloquearP();
                esconderP();
                desbloquearA();
                if(parejasRestantes==0){
                    RandomImages();
                    IniciarTiempo();
                    esconderA();
                }
            }
        });
        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInstrucciones();
            }
        });
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

        ArrayList<Integer> imagenesAbajo = new ArrayList<Integer>();

        parejasRestantes=4;

        /**Agregar en arreglos los botones que están disponibles**/
        botonesDisponiblesP.add(botonP1);
        botonesDisponiblesP.add(botonP2);
        botonesDisponiblesP.add(botonP3);
        botonesDisponiblesP.add(botonP4);

        botonesDisponiblesA.add(botonA1);
        botonesDisponiblesA.add(botonA2);
        botonesDisponiblesA.add(botonA3);
        botonesDisponiblesA.add(botonA4);

        reiniciarFondos();
        esconderFondo();

        /**Establecer las imagenes que salen abajo**/
        int imageshow1 = imagenes[r.nextInt(imagenes.length - 1)];;
        botonP1.setImageResource(imageshow1);
        botonP1.setTag(imageshow1);
        imagenesAbajo.add(imageshow1);

        int imageshow2 = imagenes[r.nextInt(imagenes.length - 1)];
        botonP2.setImageResource(imageshow2);
        botonP2.setTag(imageshow2);
        imagenesAbajo.add(imageshow2);

        int imageshow3 = imagenes[r.nextInt(imagenes.length - 1)];
        botonP3.setImageResource(imageshow3);
        botonP3.setTag(imageshow3);
        imagenesAbajo.add(imageshow3);

        int imageshow4 = imagenes[r.nextInt(imagenes.length - 1)];
        botonP4.setImageResource(imageshow4);
        botonP4.setTag(imageshow4);
        imagenesAbajo.add(imageshow4);

        Collections.shuffle(imagenesAbajo);

        /**Aleatorizador de imagenes de abajo**/
        for (int cantidad = 4; cantidad > 0; cantidad--){
            switch (cantidad) {
                case 1: botonA1.setImageResource(imagenesAbajo.get(cantidad-1));
                    botonA1.setTag(imagenesAbajo.get(cantidad-1));
                    imagenesAbajo.remove(cantidad-1);
                    break;
                case 2: botonA2.setImageResource(imagenesAbajo.get(cantidad-1));
                    botonA2.setTag(imagenesAbajo.get(cantidad-1));
                    imagenesAbajo.remove(cantidad-1);
                    break;
                case 3: botonA3.setImageResource(imagenesAbajo.get(cantidad-1));
                    botonA3.setTag(imagenesAbajo.get(cantidad-1));
                    imagenesAbajo.remove(cantidad-1);
                    break;
                case 4: botonA4.setImageResource(imagenesAbajo.get(cantidad-1));
                    botonA4.setTag(imagenesAbajo.get(cantidad-1));
                    imagenesAbajo.remove(cantidad-1);
                    break;
            }
        }
    }



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
                bloquearP();
                bloquearA();
            }
            @Override
            public void onFinish() {
                time.setText("");
                mostrarFondo();
                esconderP();
                bloquearP();
                mostrarA();
                desbloquearA();
            }
        }.start();
    }

    /**Resultados para las comparaciones de las imagenes seleccionadas**/
    private void establecerBien(){
        combinacionBotones.get(0).setVisibility(View.VISIBLE);
        combinacionBotones.get(0).setClickable(false);
        combinacionBotones.get(1).setVisibility(View.VISIBLE);
        combinacionBotones.get(1).setClickable(false);
        combinacionBotones.get(1).setBackgroundColor(Color.parseColor("#17972B"));
        botonesDisponiblesA.remove(combinacionBotones.get(0));
        botonesDisponiblesP.remove(combinacionBotones.get(1));
        combinacionBotones.remove(0);
        combinacionBotones.remove(0);
    }
    private void establecerMal(){
        combinacionBotones.get(0).setBackgroundColor(Color.parseColor("#4E718E"));
        combinacionBotones.remove(0);
        combinacionBotones.remove(0);
        desbloquearA();
        bloquearP();
        esconderP();
    }

    /**Metodos para habilitar/inhabilitar/ocultar/mostrar objetos en el juego**/
    private void esconderP(){
        for(int i = botonesDisponiblesP.size()-1; i>=0; i--){
            botonesDisponiblesP.get(i).setVisibility(View.INVISIBLE);
        }
    }
    private void bloquearP(){
        for(int i = botonesDisponiblesP.size()-1; i>=0; i--){
            botonesDisponiblesP.get(i).setClickable(false);
        }
    }
    private void mostrarP(){
        for(int i = botonesDisponiblesP.size()-1; i>=0; i--){
            botonesDisponiblesP.get(i).setVisibility(View.VISIBLE);
        }
    }
    private void desbloquearP(){
        for(int i = botonesDisponiblesP.size()-1; i>=0; i--){
            botonesDisponiblesP.get(i).setClickable(true);
            System.out.println(botonesDisponiblesP.get(i).isClickable());
        }
    }
    private void esconderA(){
        for(int i = botonesDisponiblesA.size()-1; i>=0; i--){
            botonesDisponiblesA.get(i).setVisibility(View.INVISIBLE);
        }
    }
    private void bloquearA(){
        for(int i = botonesDisponiblesA.size()-1; i>=0; i--){
            botonesDisponiblesA.get(i).setClickable(false);
            System.out.println(botonesDisponiblesA.get(i).isClickable());
        }
    }
    private void mostrarA(){
        for(int i = botonesDisponiblesA.size()-1; i>=0; i--){
            botonesDisponiblesA.get(i).setVisibility(View.VISIBLE);
        }
    }
    private void desbloquearA(){
        for(int i = botonesDisponiblesA.size()-1; i>=0; i--){
            botonesDisponiblesA.get(i).setClickable(true);
        }
    }
    private void mostrarFondo(){
        for(int i = fondos.size()-1; i>=0; i--){
            fondos.get(i).setVisibility(View.VISIBLE);
        }
    }
    private void esconderFondo(){
        for(int i = fondos.size()-1; i>=0; i--){
            fondos.get(i).setVisibility(View.INVISIBLE);
        }
    }
    private void reiniciarFondos(){
        for(int i = botonesDisponiblesA.size()-1; i>=0; i--){
            botonesDisponiblesA.get(i).setBackgroundColor(Color.parseColor("#4E718E"));
        }
        for(int i = botonesDisponiblesP.size()-1; i>=0; i--){
            botonesDisponiblesP.get(i).setBackgroundColor(Color.parseColor("#4E718E"));
        }
    }

    /**Funcion para regresar al menu del juego picture**/
    public void RegresoPicture(View view) {
        Intent picture = new Intent (this, Picture.class);
        startActivity(picture);
    }

    /**Funcion para mostrar resultado al final de la partida**/
    public void mostrarResultadoPartida(){
        establecerPuntuaciones();
        AlertDialog.Builder resultado = new AlertDialog.Builder(pictureGameScreen.this);
        resultado.setMessage(establecerMensaje(puntuacion,puntuacionMaxima))
                .setCancelable(false)
                .setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent picture = new Intent(pictureGameScreen.this, Picture.class);
                        startActivity(picture);
                    }
                });
        AlertDialog titulo = resultado.create();
        titulo.setTitle("PICTURE");
        titulo.show();
    }

    public void establecerPuntuaciones(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Picture").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> Puntuaciones = new HashMap<>();
                    if(puntuacion>puntuacionMaxima){
                        Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacion));
                    }else{
                        Puntuaciones.put("PuntuacionMaxima", Integer.toString(puntuacionMaxima));
                    }
                    Puntuaciones.put("UltimaPuntuacion", Integer.toString(puntuacion));
                    db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Picture").set(Puntuaciones);
                }
            }
        );
    }

    public void establecerPuntuacionMaxima(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Picture").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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