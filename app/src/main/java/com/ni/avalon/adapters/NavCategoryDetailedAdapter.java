package com.ni.avalon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ni.avalon.R;
import com.ni.avalon.model.NavCategoryDetailedModel;
import com.ni.avalon.model.MyCartModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {

    Context context;
    List<NavCategoryDetailedModel> list;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavCategoryDetailedModel currentItem = list.get(position);

        Glide.with(context).load(currentItem.getImg_url()).into(holder.imageView);
        holder.nombre.setText(currentItem.getNombre());
        holder.precio.setText(currentItem.getPrecio());


        holder.quantity.setText("1");

        // agregar
        holder.comprarAhora.setOnClickListener(v -> {
            int cantidad = Integer.parseInt(holder.quantity.getText().toString());
            agregarAlCarrito(currentItem, cantidad);
        });

        // aumentar
        holder.addItem.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.quantity.getText().toString());
            holder.quantity.setText(String.valueOf(currentQuantity + 1));
        });

        // disminuir
        holder.removeItem.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.quantity.getText().toString());
            if (currentQuantity > 1) {
                holder.quantity.setText(String.valueOf(currentQuantity - 1));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void agregarAlCarrito(NavCategoryDetailedModel producto, int cantidadSeleccionada) {

        int cantidad = cantidadSeleccionada;

        // Convertir el precio del producto de String a int
        int precioUnitario = 0;
        try {
            precioUnitario = Integer.parseInt(producto.getPrecio());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Calcular el precio total multiplicando el precio unitario por la cantidad
        int precioTotal = precioUnitario * cantidad;

        // Crear un modelo para el carrito de compras con la información del producto
        MyCartModel myCartModel = new MyCartModel(
                producto.getNombre(),
                String.valueOf(precioUnitario),
                getCurrentDate(),
                getCurrentTime(),
                String.valueOf(cantidad),
                String.valueOf(precioTotal),
                "ID_Documento"
        );

        // Guardar en Firestore
        firestore.collection("CurrentUser")
                .document(auth.getCurrentUser().getUid())
                .collection("AddToCart")
                .add(myCartModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al agregar al carrito", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // Método para obtener la fecha actual
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Método para obtener la hora actual
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, addItem, removeItem;
        TextView nombre, precio, quantity;
        Button comprarAhora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cat_nav_img);
            nombre = itemView.findViewById(R.id.cat_nav_nombre);
            precio = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            addItem = itemView.findViewById(R.id.add_item);
            removeItem = itemView.findViewById(R.id.remove_item);
            comprarAhora = itemView.findViewById(R.id.comprarAhora);
        }
    }
}
