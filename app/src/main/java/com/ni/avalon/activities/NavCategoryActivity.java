package com.ni.avalon.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ni.avalon.R;
import com.ni.avalon.adapters.NavCategoryDetailedAdapter;
import com.ni.avalon.model.HomeCategory;
import com.ni.avalon.model.NavCategoryDetailedModel;
import com.ni.avalon.model.NavCategoryModel;
import com.ni.avalon.model.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nav_category);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.nav_cat_det_rec);
        String tipo = getIntent().getStringExtra("tipo");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Almacenamiento
        if (tipo != null && tipo.equalsIgnoreCase("Almacenamiento")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "Almacenamiento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // Smartphone
        if (tipo != null && tipo.equalsIgnoreCase("Smartphone")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "Smartphone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // Memorias Ram
        if (tipo != null && tipo.equalsIgnoreCase("Memorias Ram")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "Memorias Ram").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // Consola
        if (tipo != null && tipo.equalsIgnoreCase("Consola")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "Consola").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // Almacenamiento Externo
        if (tipo != null && tipo.equalsIgnoreCase("Almacenamiento Externo")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "Almacenamiento Externo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // PC Escritorio
        if (tipo != null && tipo.equalsIgnoreCase("PC Escritorio")){
            db.collection("NavCategoryDetailed").whereEqualTo("tipo", "PC Escritorio").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(navCategoryDetailedModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}