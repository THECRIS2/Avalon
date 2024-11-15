package com.ni.avalon.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ni.avalon.R;
import com.ni.avalon.activities.MainActivity;
import com.ni.avalon.model.UserModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView perfilImg;
    EditText nombre, apellido, fnacimiento, correo, contraseña;
    Button actualizar, logoff;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();

        perfilImg = root.findViewById(R.id.profile_img);
        nombre = root.findViewById(R.id.profile_nombre);
        apellido = root.findViewById(R.id.profile_apellido);
        fnacimiento = root.findViewById(R.id.profile_fnacimiento);
        correo = root.findViewById(R.id.profile_email);
        contraseña = root.findViewById(R.id.profile_contraseña);
        actualizar = root.findViewById(R.id.update_profile);
        logoff = root.findViewById(R.id.logoff_profile);

        db.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel userModel = snapshot.getValue(UserModel.class);
                                nombre.setText(userModel.getNombre());
                                apellido.setText(userModel.getApellido());
                                fnacimiento.setText(userModel.getFnacimiento());
                                correo.setText(userModel.getCorreo());
                                contraseña.setText(userModel.getContraseña());

                                Glide.with(getContext()).load(userModel.getProfileImg()).into(perfilImg);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                            }
                        });

        perfilImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarPerfilUsuario();
            }
        });

        //Cerrar sesion
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Eliminar preferencia de recordar sesión
                SharedPreferences.Editor editor = getContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE).edit();
                editor.putBoolean("remember_me", false);
                editor.apply();

                // Cerrar sesión de Firebase
                auth.signOut();

                // Reedirigira al usuario al activity de nuestro login
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar el stack de actividades
                startActivity(intent);
            }
        });

        return root;
    }

    private void actualizarPerfilUsuario() {
        String nuevoNombre = nombre.getText().toString();
        String nuevoApellido = apellido.getText().toString();
        String nuevaFechaNacimiento = fnacimiento.getText().toString();
        String nuevoCorreo = correo.getText().toString();
        String nuevaContraseña = contraseña.getText().toString();

        // Actualizar los datos en Firebase
        UserModel usuarioActualizado = new UserModel(nuevoNombre, nuevoApellido, nuevaFechaNacimiento, nuevoCorreo, nuevaContraseña);
        db.getReference().child("Usuarios").child(auth.getUid())
                .setValue(usuarioActualizado)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al actualizar perfil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Actualizar la contraseña en Firebase Authentication si ha sido modificada
        if (!nuevaContraseña.isEmpty()) {
            auth.getCurrentUser().updatePassword(nuevaContraseña)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al actualizar contraseña", Toast.LENGTH_SHORT).show());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica que data no sea nulo y contiene un URI válido
        if (data != null && data.getData() != null) {
            Uri profileUri = data.getData();
            perfilImg.setImageURI(profileUri); // Actualiza la imagen en la vista

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Actualizada correctamente", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            // Muestra un mensaje si no se seleccionó una imagen
            Toast.makeText(getContext(), "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }
}