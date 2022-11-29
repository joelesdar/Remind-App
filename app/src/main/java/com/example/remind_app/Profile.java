package com.example.remind_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    CircleImageView imagenPerfil;
    TextView userName, puntuacionPicture, puntuacionTwins, puntuacionFollowme, puntuacionTotal;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int totalPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Obtencion ID's
        imagenPerfil = (CircleImageView) findViewById(R.id.reyjulien);
        userName = (TextView) findViewById(R.id.UserNameJulien);
        puntuacionPicture = (TextView) findViewById(R.id.puntajePicture);
        puntuacionTwins = (TextView) findViewById(R.id.puntajeTwins);
        puntuacionFollowme = (TextView) findViewById(R.id.puntajeFollowme);
        puntuacionTotal = (TextView) findViewById(R.id.puntajeTotal);

        // Obtener el nombre de usuario
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                                                                                                        {
                                                                                                            @Override
                                                                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                if(documentSnapshot.exists()){
                                                                                                                    String name = documentSnapshot.getString("Nombre");
                                                                                                                    userName.setText(name);
                                                                                                                }
                                                                                                            }
                                                                                                        }
        );

        //Mostrar la foto de perfil del usuario
        mostrarFotoPerfil();
        mostrarPuntuaciones();
    }

    public void mostrarPuntuaciones(){
        // Puntuacion Picture
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Picture").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                                                                                                                                                {
                                                                                                                                                    @Override
                                                                                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                                        if(documentSnapshot.exists()){
                                                                                                                                                            String picturePoints = documentSnapshot.getString("PuntuacionMaxima");
                                                                                                                                                            puntuacionPicture.setText("Picture: "+ picturePoints);
                                                                                                                                                            totalPoints += Integer.parseInt(picturePoints);
                                                                                                                                                            puntuacionTotal.setText("Tienes " + totalPoints + " puntos!");
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
        );

        // Puntuacion Twins
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("Twins").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                                                                                                                                              {
                                                                                                                                                  @Override
                                                                                                                                                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                                      if(documentSnapshot.exists()){
                                                                                                                                                          String twinsPoints = documentSnapshot.getString("PuntuacionMaxima");
                                                                                                                                                          puntuacionTwins.setText("Twins: "+ twinsPoints);
                                                                                                                                                          totalPoints += Integer.parseInt(twinsPoints);
                                                                                                                                                          puntuacionTotal.setText("Tienes " + totalPoints + " puntos!");
                                                                                                                                                      }
                                                                                                                                                  }
                                                                                                                                              }
        );

        // Puntuacion Follow Me
        db.collection("Usuario").document(mAuth.getCurrentUser().getEmail()).collection("Juego").document("FollowMe").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                                                                                                                                                 {
                                                                                                                                                     @Override
                                                                                                                                                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                                         if(documentSnapshot.exists()){
                                                                                                                                                             String followMePoints = documentSnapshot.getString("PuntuacionMaxima");
                                                                                                                                                             puntuacionFollowme.setText("Follow Me: "+ followMePoints);
                                                                                                                                                             totalPoints += Integer.parseInt(followMePoints);
                                                                                                                                                             puntuacionTotal.setText("Tienes " + totalPoints + " puntos!");
                                                                                                                                                         }
                                                                                                                                                     }
                                                                                                                                                 }
        );
    }

    public void mostrarFotoPerfil() {
        //Codigo para descargar la imagen de perfil y usarlo en el ImageView
        //Obtenido de https://stackoverflow.com/questions/3118691/android-make-an-image-at-a-url-equal-to-imageviews-image
        try {
            String imageUrl = mAuth.getCurrentUser().getPhotoUrl().toString();
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            imagenPerfil.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Regresar al menu principal **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }
}