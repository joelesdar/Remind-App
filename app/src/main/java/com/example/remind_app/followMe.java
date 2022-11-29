package com.example.remind_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TimerTask;

public class followMe extends AppCompatActivity {

    Button instrucciones, botonVideo;
    TextView puntuacionMaxima, ultimaPuntuacion;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followme);
        getSupportActionBar().hide();

        instrucciones = findViewById(R.id.botonInstruccionesPicture);
        botonVideo = findViewById(R.id.botonVideoguiaPicture);

        puntuacionMaxima = findViewById(R.id.maximaPuntuacionPicture);
        ultimaPuntuacion = findViewById(R.id.ultimaPuntuacionPicture);

        /**Listener para mostrar el video guia en un pop-up**/
        botonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(followMe.this);// add here your class name
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

                /**Reinicia el video cuando se termina**/
                videoPicture.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoPicture.start();
                    };
                });
            }
        });

        /** ----------------------------------------------------------- **/


        mostrarPuntuaciones();

        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarInstrucciones();
                System.out.println("Mostrar las instrucciones...");
            }
        });



    }

    /** Regresar al menu principal **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }

    /** Ingresar al juego **/
    public void IngresoJuegoFollowMe (View view) {
        Intent game = new Intent (this, followMeGameScreen.class);
        startActivity(game);
    }

    public void mostrarPuntuaciones(){
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("FollowMe").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                                                     @Override
                                                                                                                                                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                                         if(documentSnapshot.exists()){
                                                                                                                                                             String MaxPuntuacion = documentSnapshot.getString("PuntuacionMaxima");
                                                                                                                                                             String UltPuntuacion = documentSnapshot.getString("UltimaPuntuacion");
                                                                                                                                                             puntuacionMaxima.setText("Maxima puntuación: "+ MaxPuntuacion);
                                                                                                                                                             ultimaPuntuacion.setText("Ultima puntuación: "+ UltPuntuacion);
                                                                                                                                                         }
                                                                                                                                                     }
                                                                                                                                                 }
        );
        System.out.println(mAuth.getCurrentUser().getEmail());
    }

    /** Mostrar las instrucciones del juego**/
    public void mostrarInstrucciones(){
        String instruccionesLeidas = new String();
        try {
            instruccionesLeidas = leerInstrucciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder instrucciones = new AlertDialog.Builder(com.example.remind_app.followMe.this);
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