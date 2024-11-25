package com.ni.avalon.SQLite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ni.avalon.R;

import java.util.ArrayList;

public class Leer extends AppCompatActivity {

    private ListView lst1;
    private ArrayList<String> arreglo = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leer);

        try {
            SQLiteDatabase db = openOrCreateDatabase("AvalonSQL", Context.MODE_PRIVATE, null);
            // Abre o crea la base de datos llamada "BD_EJEMPLO" en modo privado.

            lst1 = findViewById(R.id.lst1);
            // Asocia la variable `lst1` con el ListView definido en el archivo XML.

            final Cursor c = db.rawQuery("select * from usuarios", null);
            // Ejecuta una consulta SQL para obtener todos los registros de la tabla "persona" y guarda el resultado en un cursor.

            int id = c.getColumnIndex("id");
            int nombre = c.getColumnIndex("nombre");
            int apellido = c.getColumnIndex("apellido");
            int fnacimiento = c.getColumnIndex("fnacimiento");
            int usuario = c.getColumnIndex("usuario");
            int contraseña = c.getColumnIndex("contraseña");
            // Obtiene los índices de las columnas "id", "nombre", "apellido" y "edad" de la tabla.

            arreglo.clear();
            // Limpia el ArrayList `arreglo` para asegurarse de que no contenga datos antiguos.

            arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arreglo);
            // Inicializa el ArrayAdapter con el contexto actual y un diseño simple para mostrar los elementos del ArrayList.

            lst1.setAdapter(arrayAdapter);
            // Asocia el ArrayAdapter con el ListView para mostrar los datos.

            arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtItem, arreglo);
            lst1.setAdapter(arrayAdapter);

            final ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
            // Crea un ArrayList para almacenar objetos de la clase `Persona`.

            if (c.moveToFirst()) {
                // Verifica si el cursor tiene algún registro. Si es así, comienza a iterar sobre ellos.

                do {
                    Usuarios usuarios = new Usuarios();
                    usuarios.id = c.getString(id);
                    usuarios.nombre = c.getString(nombre);
                    usuarios.apellido = c.getString(apellido);
                    usuarios.fnacimiento = c.getString(fnacimiento);
                    usuarios.usuario = c.getString(usuario);
                    usuarios.contraseña = c.getString(contraseña);
                    // Crea un objeto `Persona` y establece sus atributos con los valores obtenidos del cursor.

                    lista.add(usuarios);
                    // Agrega la persona creada a la lista de personas.

                    arreglo.add(c.getString(id) + " \t " + c.getString(nombre) + " \t " + c.getString(apellido) + " \t " + c.getString(fnacimiento) + " \t "
                            + c.getString(usuario) + " \t " + c.getString(contraseña));
                    // Agrega una representación en texto de la persona al ArrayList `arreglo` para mostrarla en el ListView.

                } while (c.moveToNext());
                // Repite el proceso mientras el cursor tenga más registros.

                arrayAdapter.notifyDataSetChanged();
                // Notifica al ArrayAdapter que los datos han cambiado para actualizar la lista.

                lst1.invalidateViews();
                // Refresca la vista del ListView para asegurarse de que los datos actualizados se muestren.
            }

            lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long l) {
                    Usuarios usuarios = lista.get(position);
                    // Obtiene el objeto `Persona` en la posición seleccionada de la lista.

                    Intent i = new Intent(getApplicationContext(), Editar.class);
                    i.putExtra("id", usuarios.id);
                    i.putExtra("nombre", usuarios.nombre);
                    i.putExtra("apellido", usuarios.apellido);
                    i.putExtra("fnacimiento", usuarios.fnacimiento);
                    i.putExtra("usuario", usuarios.usuario);
                    i.putExtra("contraseña", usuarios.contraseña);
                    // Crea un Intent para iniciar la actividad `Editar`, pasando los datos de la persona seleccionada.

                    startActivity(i);
                    // Inicia la actividad `Editar`.
                    finish(); // con finish se mata el activity anterior para asi ahorrar recursos
                }
            });
            // Establece un listener en los elementos del ListView. Cuando se hace clic en un elemento, se abre la actividad `Editar` con los datos de esa persona.
        } catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
            // Captura cualquier excepción y muestra un mensaje de error si ocurre un problema.
        }
    }
}