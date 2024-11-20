package com.ni.avalon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ni.avalon.adapters.OrderAdapter;
import com.ni.avalon.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    FirebaseFirestore db;
    FirebaseAuth auth;

    List<OrderModel> orderModelList;
    OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        // Inicialización de Firebase y UI
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.recyclerViewOrder);
        progressBar = root.findViewById(R.id.progressbarOrder);
        ConstraintLayout ordenVacia = root.findViewById(R.id.ordenVacia);
        ConstraintLayout ordenLlena = root.findViewById(R.id.ordenLlena);

        // Configuración inicial de vistas
        progressBar.setVisibility(View.VISIBLE);
        ordenVacia.setVisibility(View.GONE);
        ordenLlena.setVisibility(View.GONE);

        // Configuración de RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), orderModelList);
        recyclerView.setAdapter(orderAdapter);

        // Validar si el usuario está autenticado
        if (auth.getCurrentUser() == null) {
            progressBar.setVisibility(View.GONE);
            ordenVacia.setVisibility(View.VISIBLE);
            return root;
        }

        // Consultar datos de Firestore
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful() && task.getResult() != null) {
                            orderModelList.clear(); // Limpiar lista antes de agregar nuevos datos

                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();
                                OrderModel orderModel = documentSnapshot.toObject(OrderModel.class);

                                // Validar que el modelo no sea nulo y contenga campos válidos
                                if (orderModel != null && orderModel.getProductNombre() != null) {
                                    orderModel.setDocumentid(documentId);
                                    orderModelList.add(orderModel);
                                }
                            }

                            orderAdapter.notifyDataSetChanged();

                            // Mostrar diseño según la existencia de datos
                            if (orderModelList.isEmpty()) {
                                ordenVacia.setVisibility(View.VISIBLE);
                                ordenLlena.setVisibility(View.GONE);
                            } else {
                                ordenVacia.setVisibility(View.GONE);
                                ordenLlena.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // Mostrar vista vacía en caso de error
                            ordenVacia.setVisibility(View.VISIBLE);
                            ordenLlena.setVisibility(View.GONE);
                            Log.e("MyOrdersFragment", "Error al obtener datos: ", task.getException());
                        }
                    }
                });

        return root;
    }
}