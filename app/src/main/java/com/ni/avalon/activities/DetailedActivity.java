package com.ni.avalon.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ni.avalon.R;
import com.ni.avalon.model.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg, additem, removeitem;
    TextView precio, rating, descripcion, cantidad;
    int cantTotal;
    int precioTotal;
    Button addtocart;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        detailedImg = findViewById(R.id.detailed_img);
        additem = findViewById(R.id.add_item);
        precio = findViewById(R.id.price_item);
        rating = findViewById(R.id.detailed_rating);
        descripcion = findViewById(R.id.description_img);
        removeitem = findViewById(R.id.remove_item);
        addtocart = findViewById(R.id.add_to_cart);
        cantidad = findViewById(R.id.quantity);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // toolbar
        toolbar = findViewById(R.id.toolbarDetailed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("descripcion");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            descripcion.setText(viewAllModel.getDescripcion());
            precio.setText(viewAllModel.getPrecio());

            // Inicializamos cantTotal con 1
            cantTotal = 1;
            cantidad.setText(String.valueOf(cantTotal));

            // Calculamos el precioTotal inicial
            int precioUnitario = Integer.parseInt(viewAllModel.getPrecio());
            precioTotal = precioUnitario * cantTotal;
        }

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantTotal < 10) {
                    cantTotal++;
                    cantidad.setText(String.valueOf(cantTotal));

                    // Actualizamos precioTotal cada vez que se incrementa la cantidad
                    int precioUnitario = Integer.parseInt(viewAllModel.getPrecio());
                    precioTotal = precioUnitario * cantTotal;
                }
            }
        });

        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantTotal > 1) { // Evita ir a 0 si quieres que la cantidad mínima sea 1
                    cantTotal--;
                    cantidad.setText(String.valueOf(cantTotal));

                    // Actualizamos precioTotal cada vez que se reduce la cantidad
                    int precioUnitario = Integer.parseInt(viewAllModel.getPrecio());
                    precioTotal = precioUnitario * cantTotal;
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedtocart();
            }
        });
    }

    private void addedtocart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productNombre", viewAllModel.getNombre());
        cartMap.put("productPrecio", precio.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("cantTotal", String.valueOf(cantTotal));
        cartMap.put("precioTotal", String.valueOf(precioTotal));

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Añadido al carrito exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}