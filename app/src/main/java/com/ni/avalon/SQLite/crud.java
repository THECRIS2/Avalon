package com.ni.avalon.SQLite;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ni.avalon.R;
import com.ni.avalon.activities.register;

public class crud extends AppCompatActivity {

    // declaramos las variables
    private EditText ed_nombre, ed_apellido, ed_fnacimiento, ed_usuario, ed_contraseña;
    private ImageButton b_agregar, b_ver, b_atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud);

        // instanciamos o asociamos las variables con los campos de texto
        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_fnacimiento = findViewById(R.id.et_fnacimiento);
        ed_usuario = findViewById(R.id.et_usuario);
        ed_contraseña = findViewById(R.id.et_contraseña);

        // lo mismo con los botones
        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);
        b_atras = findViewById(R.id.backCrud);

        b_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Leer.class);
                startActivity(i);
            }
        });

        b_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(crud.this, register.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void insertar() {
        // Método para insertar un nuevo registro en la base de datos.

        try {
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String fnacimiento = ed_fnacimiento.getText().toString();
            String usuario = ed_usuario.getText().toString();
            String contraseña = ed_contraseña.getText().toString();
            // Obtiene el texto ingresado por el usuario en los campos de nombre, apellido y edad.

            SQLiteDatabase db = openOrCreateDatabase("AvalonSQL", Context.MODE_PRIVATE, null);
            // Abre o crea la base de datos llamada "AvalonSQL" en modo privado.

            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, apellido VARCHAR, fnacimiento VARCHAR, " +
                    "usuario VARCHAR, contraseña VARCHAR)");
            // Crea la tabla "persona" si no existe, con las columnas id (clave primaria auto-incremental), nombre, apellido y edad.

            String sql = "insert into usuarios(nombre, apellido, fnacimiento, usuario, contraseña) values(?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            // Prepara una instrucción SQL para insertar los valores de nombre, apellido y edad en la tabla "persona".

            statement.bindString(1, nombre);
            statement.bindString(2, apellido);
            statement.bindString(3, fnacimiento);
            statement.bindString(4, usuario);
            statement.bindString(5, contraseña);
            // Vincula los valores ingresados por el usuario a los parámetros de la instrucción SQL.

            statement.execute();
            // Ejecuta la instrucción SQL para insertar el registro en la base de datos.

            Toast.makeText(this, "Datos agregados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();
            // Muestra un mensaje al usuario indicando que los datos fueron agregados correctamente.

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_fnacimiento.setText("");
            ed_usuario.setText("");
            ed_contraseña.setText("");
            ed_nombre.requestFocus();
            // Limpia los campos de entrada de texto y coloca el cursor en el campo de nombre.
        } catch (Exception ex) {
            Toast.makeText(this, "Error no se pudieron guardar los datos.", Toast.LENGTH_LONG).show();
            // Captura cualquier excepción y muestra un mensaje de error si algo sale mal.
        }
    }
}