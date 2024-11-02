package com.ni.avalon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ni.avalon.model.UserModel;

public class register extends AppCompatActivity {

    Button register;
    EditText reg_nombre, reg_apellido, fnacimiento, reg_email, reg_contraseña, conf_contraseña;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ImageButton backButton = findViewById(R.id.btnBack); // llamamos a nuestro ImageButton por su id
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
                // aqui decimos que al presionar el boton nos mandara al activity main de nuestro proyecto
            }
        });

        // inicializamos las variables
        auth = FirebaseAuth.getInstance(); // para la autentificacion
        database = FirebaseDatabase.getInstance(); // para la base de datos
        register = findViewById(R.id.register);
        reg_nombre = findViewById(R.id.reg_nombre);
        reg_apellido = findViewById(R.id.reg_apellido);
        fnacimiento = findViewById(R.id.fnacimiento);
        reg_email = findViewById(R.id.reg_email);
        reg_contraseña = findViewById(R.id.reg_contraseña);
        conf_contraseña = findViewById(R.id.conf_contraseña);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // llamamos la funcion createUser
                createUser();
            }
        });
    }

    // aqui creamos la funcion create user para guardar nuestro usuario
    private void createUser() {
        String Nombre = reg_nombre.getText().toString();
        String Apellido = reg_apellido.getText().toString();
        String Fnacimiento = fnacimiento.getText().toString();
        String Correo = reg_email.getText().toString();
        String Contraseña = reg_contraseña.getText().toString();
        String ConfContraseña = conf_contraseña.getText().toString();

        if (TextUtils.isEmpty(Nombre)){
            Toast.makeText(this, "El campo de nombre esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo nombre esta vacio
        }

        if (TextUtils.isEmpty(Apellido)){
            Toast.makeText(this, "El campo de apellido esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo apellido esta vacio
        }

        if (TextUtils.isEmpty(Fnacimiento)){
            Toast.makeText(this, "El campo de fecha nacimiento esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo fecha de nacimiento esta vacio
        }

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

        if (TextUtils.isEmpty(ConfContraseña)){
            Toast.makeText(this, "El campo de confirmar contraseña esta vacio", Toast.LENGTH_SHORT).show();
            return;
            // comprobara si el campo confirmar contraseña esta vacio
        }

        if (Contraseña.length() < 6){
            Toast.makeText(this, "Tu contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
            // verificara que la contraseña al menos tenga 6 caracteres
        }

        if (!Contraseña.equals(ConfContraseña)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
            // verificara si los valores en los campos de contraseña y confirmar contraseña sean iguales
        }

        // creara el usuario una vez que las condiciones anteriores se cumplan
        auth.createUserWithEmailAndPassword(Correo, Contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            UserModel userModel = new UserModel(Nombre, Apellido, Fnacimiento, Correo, Contraseña);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Usuarios").child(id).setValue(userModel);

                            Toast.makeText(register.this, "Registro completado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(register.this, menu.class);
                            startActivity(intent); // si sale todo bien nuestro usuario sera registrado correctamente y nos redirigira al menu
                        }
                        else{
                            Toast.makeText(register.this, "Ha habido un problema con el registro", Toast.LENGTH_SHORT).show();
                            // en caso de un error de conexion con firebase mostrara este mensaje
                        }
                    }
                });

    }
}