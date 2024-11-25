package com.ni.avalon.SQLite;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ni.avalon.R;

public class Editar extends AppCompatActivity {

    private EditText ed_nombre, ed_apellido, ed_fnacimiento, ed_usuario, ed_contraseña, ed_id;
    private ImageButton b_editar, b_eliminar, b_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);

        ed_nombre = findViewById(R.id.et_nombre);
        ed_apellido = findViewById(R.id.et_apellido);
        ed_fnacimiento = findViewById(R.id.et_fnacimiento);
        ed_usuario = findViewById(R.id.et_usuario);
        ed_contraseña = findViewById(R.id.et_contraseña);
        ed_id = findViewById(R.id.id);
        // Asocia las variables EditText con los elementos de la interfaz definidos en el archivo XML.

        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btn_eliminar);
        b_volver = findViewById(R.id.btn_volver);
        // Asocia las variables Button con los botones definidos en el archivo XML.

        Intent i = getIntent();
        // Obtiene el Intent que inició esta actividad, el cual contiene datos pasados desde otra actividad.

        String et_id = i.getStringExtra("id").toString();
        String et_nombre = i.getStringExtra("nombre").toString();
        String et_apellido = i.getStringExtra("apellido").toString();
        String et_fnacimiento = i.getStringExtra("fnacimiento").toString();
        String et_usuario = i.getStringExtra("usuario").toString();
        String et_contraseña= i.getStringExtra("contraseña").toString();
        // Extrae los datos pasados en el Intent (id, nombre, apellido, edad) y los convierte a String.

        ed_id.setText(et_id);
        ed_nombre.setText(et_nombre);
        ed_apellido.setText(et_apellido);
        ed_fnacimiento.setText(et_fnacimiento);
        ed_usuario.setText(et_usuario);
        ed_contraseña.setText(et_contraseña);
        // Establece los valores extraídos del Intent en los campos de texto correspondientes.

        b_editar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                editar();
            }
        });
        // Asigna un evento al botón de editar, que llama al método `editar` cuando se hace clic.

        b_eliminar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                eliminar();
            }
        });
        // Asigna un evento al botón de eliminar, que llama al método `eliminar` cuando se hace clic.

        b_volver.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(), Leer.class);
                startActivity(i);
                finish(); // con finish se mata el activity anterior para asi ahorrar recursos
            }
        });
        // Asigna un evento al botón de volver, que inicia la actividad `Leer` cuando se hace clic.
    }

    public void eliminar() {
        // Método que elimina un registro de la base de datos.

        try {
            String id = ed_id.getText().toString();
            // Obtiene el id del campo de texto `ed_id`.

            SQLiteDatabase db = openOrCreateDatabase("AvalonSQL", Context.MODE_PRIVATE, null);
            // Abre o crea la base de datos llamada "BD_EJEMPLO" en modo privado.

            String sql = "delete from usuarios where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            // Prepara una sentencia SQL para eliminar un registro donde el id coincida.

            statement.bindString(1, id);
            statement.execute();
            // Vincula el id al primer parámetro de la sentencia SQL y ejecuta la eliminación.

            Toast.makeText(this, "Datos eliminados de la base de datos.", Toast.LENGTH_LONG).show();
            // Muestra un mensaje confirmando que los datos fueron eliminados.

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_fnacimiento.setText("");
            ed_usuario.setText("");
            ed_contraseña.setText("");
            ed_nombre.requestFocus();
            // Limpia los campos de texto y establece el foco en el campo de nombre.

        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron eliminar los datos.", Toast.LENGTH_LONG).show();
            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        }
    }

    public void editar() {
        // Método que actualiza un registro en la base de datos.

        try {
            String nombre = ed_nombre.getText().toString();
            String apellido = ed_apellido.getText().toString();
            String fnacimiento = ed_fnacimiento.getText().toString();
            String usuario = ed_usuario.getText().toString();
            String contraseña = ed_contraseña.getText().toString();
            String id = ed_id.getText().toString();
            // Obtiene los valores de los campos de texto (nombre, apellido, edad, id).

            SQLiteDatabase db = openOrCreateDatabase("AvalonSQL", Context.MODE_PRIVATE, null);
            // Abre o crea la base de datos llamada "BD_EJEMPLO" en modo privado.

            String sql = "update usuarios set nombre = ?,apellido=?,fnacimiento=?, usuario=?, contraseña=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);
            // Prepara una sentencia SQL para actualizar un registro donde el id coincida.

            statement.bindString(1, nombre);
            statement.bindString(2, apellido);
            statement.bindString(3, fnacimiento);
            statement.bindString(4, usuario);
            statement.bindString(5, contraseña);
            statement.bindString(6, id);
            statement.execute();
            // Vincula los valores a los parámetros de la sentencia SQL y ejecuta la actualización.

            Toast.makeText(this, "Datos actualizados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();
            // Muestra un mensaje confirmando que los datos fueron actualizados.

            ed_nombre.setText("");
            ed_apellido.setText("");
            ed_fnacimiento.setText("");
            ed_usuario.setText("");
            ed_contraseña.setText("");
            ed_nombre.requestFocus();
            // Limpia los campos de texto y establece el foco en el campo de nombre.

        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron actualizar los datos.", Toast.LENGTH_LONG).show();
            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        }
    }
}