package com.ni.avalon.ui.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ni.avalon.R;
import com.ni.avalon.adapters.MyCartAdapter;
import com.ni.avalon.model.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    TextView montoTotal;
    FirebaseAuth auth;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar progressBar;
    Button buynow;

    public MyCartsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        montoTotal = root.findViewById(R.id.textView2);
        progressBar = root.findViewById(R.id.progressbarCart);
        buynow = root.findViewById(R.id.buy_now);

        View carritoVacio = root.findViewById(R.id.carritoVacio);
        View carritoLleno = root.findViewById(R.id.carritoLleno);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        // Muestra el progreso inicialmente
        progressBar.setVisibility(View.VISIBLE);
        carritoVacio.setVisibility(View.GONE);
        carritoLleno.setVisibility(View.GONE);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();
                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                if (cartModel != null) {
                                    cartModel.setDocumentid(documentId);
                                    cartModelList.add(cartModel);
                                }
                            }
                            cartAdapter.notifyDataSetChanged();

                            // Verificar si hay datos en el carrito
                            if (cartModelList.isEmpty()) {
                                carritoVacio.setVisibility(View.VISIBLE);
                                carritoLleno.setVisibility(View.GONE);
                            } else {
                                carritoVacio.setVisibility(View.GONE);
                                carritoLleno.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // Si falla la consulta o no hay datos, mostrar el diseño vacío
                            carritoVacio.setVisibility(View.VISIBLE);
                            carritoLleno.setVisibility(View.GONE);
                        }
                    }
                });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar los datos al fragmento de órdenes
                carritoVacio.setVisibility(View.VISIBLE);
                carritoLleno.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);

                // Vaciar el carrito de Firebase
                vaciarCarrito();
            }
        });

        cartAdapter.setOnCartUpdatedListener(new MyCartAdapter.OnCartUpdatedListener() {
            @Override
            public void onCartUpdated(boolean isEmpty) {
                if (isEmpty) {
                    carritoVacio.setVisibility(View.VISIBLE);
                    carritoLleno.setVisibility(View.GONE);
                } else {
                    carritoVacio.setVisibility(View.GONE);
                    carritoLleno.setVisibility(View.VISIBLE);
                }
            }
        });

        return root;
    }

    private void vaciarCarrito() {

        for (MyCartModel cartItem : cartModelList) {
            db.collection("CurrentUser")
                    .document(auth.getCurrentUser().getUid())
                    .collection("AddToCart")
                    .document(cartItem.getDocumentid())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Carrito vaciado", Toast.LENGTH_SHORT).show();
                                cartModelList.clear();
                                cartAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Error al vaciar el carrito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount", 0);
            montoTotal.setText("Monto Total :"+totalBill+"$");
        }
    };

}