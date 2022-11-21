package com.example.remind_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

        // obtencion id's
        imagenPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        userName = (TextView) findViewById(R.id.UserName);
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
                        // Puntaje total
                        puntuacionTotal.setText("Tienes " + totalPoints + " puntos !");
                    }
                }
            }
        );

        System.out.println(mAuth.getCurrentUser().getEmail());
    }

    /** Regresar al menu principal **/
    public void Regresar (View view) {
        Intent menu = new Intent (this, MenuPrincipal.class);
        startActivity(menu);
    }
}