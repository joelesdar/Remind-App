
package com.example.remind_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

    public class RemindApp extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

        }

    /*  Aplicacion de calculadora que suma

    public class MainActivity extends AppCompatActivity {


        // Cuando se usan interfaces graficas, se deben indicar primero los componentes que se van
        //a utilizar

        // Componentes que se van a utilizar
        private EditText et1;
        private EditText et2;
        private TextView tv1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Asignacion de los componentes de la interfaz a las variables, usando casteo
            et1 = (EditText)findViewById(R.id.inputNum1);
            et2 = (EditText)findViewById(R.id.inputNum2);
            tv1 = (TextView)findViewById(R.id.txtResultado);

        }

        // Metodo para realizar la suma
        public void sumar(View view){
            String v1 = et1.getText().toString();
            String v2 = et2.getText().toString();

            int num1 = Integer.parseInt(v1);
            int num2 = Integer.parseInt(v2);

            int suma = num1 + num2;

            String resultado = String.valueOf(suma);
            tv1.setText(resultado);

        }*/

    /* ejemplos del manejo del ciclo de vida de un activity

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show();
        // La actividad esta a punto de hacerse visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        // La actividad se ha vuelto visible (ahora se "reanuda").
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show();
        // Enfocarse en otra actividad  (esta actividad esta a punto de ser "detenida").
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show();
        // La actividad ya no es visible (ahora esta "detenida")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
        // La actividad esta a punto de ser destruida.
    } */
    }