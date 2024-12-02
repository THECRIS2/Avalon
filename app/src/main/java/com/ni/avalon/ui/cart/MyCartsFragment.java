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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


        progressBar.setVisibility(View.VISIBLE);
        carritoVacio.setVisibility(View.GONE);
        carritoLleno.setVisibility(View.GONE);

        // Obtener los productos del carrito de Firebase
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

                            // Verificar si el carrito está vacío
                            actualizarVisibilidadCarrito(carritoVacio, carritoLleno);
                            calcularMontoTotal();
                        } else {
                            // Si no hay productos, mostrar el layout de carrito vacío
                            carritoVacio.setVisibility(View.VISIBLE);
                            carritoLleno.setVisibility(View.GONE);
                        }
                    }
                });

        // Acción del botón "Comprar ahora"
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartModelList.isEmpty()) {
                    Toast.makeText(getContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show();
                    return; // Evita continuar si el carrito está vacío
                }

                // Enviar los datos al fragmento de órdenes
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);

                // Vaciar el carrito de Firebase
                vaciarCarrito();
            }
        });

        // Listener para actualizar el carrito cuando se elimine un artículo
        cartAdapter.setOnCartUpdatedListener(new MyCartAdapter.OnCartUpdatedListener() {
            @Override
            public void onCartUpdated(boolean isEmpty) {
                actualizarVisibilidadCarrito(carritoVacio, carritoLleno);
                calcularMontoTotal();  // Recalcular el monto total después de cada actualización
            }
        });

        return root;
    }

    // Función para vaciar el carrito
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
                                // Vaciar el carrito local
                                cartModelList.clear();
                                cartAdapter.notifyDataSetChanged(); // Actualizar RecyclerView

                                // Mostrar mensaje
                                Toast.makeText(getContext(), "Carrito vaciado", Toast.LENGTH_SHORT).show();

                                // Actualizar visibilidad de las vistas del carrito
                                actualizarVisibilidadCarrito(getView().findViewById(R.id.carritoVacio), getView().findViewById(R.id.carritoLleno));
                            } else {
                                Toast.makeText(getContext(), "Error al vaciar el carrito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Función para actualizar la visibilidad del carrito
    private void actualizarVisibilidadCarrito(View carritoVacio, View carritoLleno) {
        if (cartModelList.isEmpty()) {
            carritoVacio.setVisibility(View.VISIBLE);
            carritoLleno.setVisibility(View.GONE);
        } else {
            carritoVacio.setVisibility(View.GONE);
            carritoLleno.setVisibility(View.VISIBLE);
        }
    }

    // Función para calcular el monto total del carrito
    private void calcularMontoTotal() {
        int montoTotalCarrito = 0;

        for (MyCartModel item : cartModelList) {
            try {
                // Asegúrate de que el precio total se obtenga como un entero
                int precioTotal = Integer.parseInt(item.getPrecioTotal());
                montoTotalCarrito += precioTotal;  // Sumar al monto total
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Mostrar el monto total en el TextView
        montoTotal.setText("Monto Total: " + montoTotalCarrito + "$");
    }

    // Receptor para recibir el total del carrito
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            montoTotal.setText("Monto Total: " + totalBill + "$");
        }
    };
}

