package com.ni.avalon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ni.avalon.R;

public class MainActivity extends AppCompatActivity {

    EditText email, password; // declaramos el tipo de variable de nuestro user y password
    FirebaseAuth auth; // declaramos la variable auth
    ProgressBar LoginProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.btnRegister); // llamamos a nuestro Button por su id
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
                // aqui decimos que al presionar el boton nos mandara al activity register de nuestra aplicacion
            }
        });

        auth = FirebaseAuth.getInstance(); // instanciamos la variable con la funcion auth de firebase
        email = findViewById(R.id.email); // declaramos una variable para nuestro campo de texto de usuario
        password = findViewById(R.id.password); // declaramos una variable para nuestro campo de texto de contraseña
        LoginProgressBar = findViewById(R.id.LoginProgressBar);
        LoginProgressBar.setVisibility(View.GONE);

        Button loginButton = findViewById(R.id.btnLogin); // llamamos a nuestro Button por su id
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NameUser = email.getText().toString().trim(); // convertimos el texto del campo de usuario en string
                String PassUser = password.getText().toString().trim(); // convertimos el texto del campo de contraseña en string

                if(NameUser.isEmpty() && PassUser.isEmpty()){ // definimos una condicional
                    Toast.makeText(MainActivity.this, "Porfavor ingresa los datos", Toast.LENGTH_SHORT).show();
                    // en caso de que no haya ningun campo de texto nos mostrara un mensaje pidiendo que rellenemos los campos de nuestro login
                }
                else{
                    LoginProgressBar.setVisibility(View.VISIBLE); // mostramos la progressbar mientras se ejecuta la funcion
                    loginUser(); // llamamos al metodo de loginUser
                }
            }
        });
    }

    // programamos nuestra funcion
    private void loginUser(){
        String Correo = email.getText().toString();
        String Contraseña = password.getText().toString();

        if (TextUtils.isEmpty(Correo)){
            Toast.makeText(this, "El campo de correo esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo correo esta vacio
        }

        if (TextUtils.isEmpty(Contraseña)){
            Toast.makeText(this, "El campo de contraseña esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo contraseña esta vacio
        }

        if (Contraseña.length() < 6){
            Toast.makeText(this, "Tu contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
            // verificara que la contraseña al menos tenga 6 caracteres
        }

        // Inicio de sesion
        auth.signInWithEmailAndPassword(Correo, Contraseña) // hara la verificacion de los datos con el sistema de autentificacion de firebase
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(MainActivity.this, "Inicio se sesion correcto", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, menu.class);
                           startActivity(intent);
                           LoginProgressBar.setVisibility(View.GONE); // se vuelve a ocultar la progresbar
                           // en caso de que los datos sean correctos procedera a redirigirnos al menu de nuestra aplicacion
                           finish(); // una vez terminada su ejecucion matamos el activity para ahorrar recursos
                       }
                       else{
                           Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                           // en caso de errores o que los datos no coincidan con los que hay en firebase mandara este error
                           LoginProgressBar.setVisibility(View.GONE); // se vuelve a ocultar la progressbar
                       }
                    }
                });
    }
}