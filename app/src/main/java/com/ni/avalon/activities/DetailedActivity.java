package com.ni.avalon.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.ni.avalon.R;
import com.ni.avalon.model.ViewAllModel;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg, additem, removeitem;
    TextView precio, rating, descripcion, cantidad;
    int cantTotal;
    Button addtocart;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        detailedImg = findViewById(R.id.detailed_img);
        additem= findViewById(R.id.add_item);
        precio = findViewById(R.id.price_item);
        rating = findViewById(R.id.detailed_rating);
        descripcion = findViewById(R.id.description_img);
        removeitem = findViewById(R.id.remove_item);
        addtocart = findViewById(R.id.add_to_cart);
        cantidad = findViewById(R.id.quantity);

        // toolbar
        toolbar = findViewById(R.id.toolbarDetailed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("descripcion");
        if (object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        if (viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            descripcion.setText(viewAllModel.getDescripcion());
            precio.setText(viewAllModel.getPrecio());
        }

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantTotal < 10){
                    cantTotal++;
                    cantidad.setText(String.valueOf(cantTotal));
                }
            }
        });

        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantTotal > 0){
                    cantTotal--;
                    cantidad.setText(String.valueOf(cantTotal));
                }
            }
        });
    }
}