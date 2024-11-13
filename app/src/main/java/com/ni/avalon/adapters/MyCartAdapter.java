package com.ni.avalon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ni.avalon.R;
import com.ni.avalon.model.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    int precio_total = 0;  // Mantén el precio total como un entero

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCartModel currentItem = cartModelList.get(position);

        holder.nombre.setText(currentItem.getProductNombre());
        holder.precio.setText(currentItem.getProductPrecio());
        holder.fecha.setText(currentItem.getCurrentDate());
        holder.hora.setText(currentItem.getCurrentTime());
        holder.cantidad.setText(currentItem.getCantTotal());
        holder.precio_total.setText(currentItem.getPrecioTotal());

        // Convertir el precio total de String a int antes de sumarlo
        try {
            int precioItem = Integer.parseInt(currentItem.getPrecioTotal());
            precio_total += precioItem;  // Sumar al total
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Manejar el error en caso de que el precio no sea válido
        }

        // Enviar el monto total como un Intent
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", precio_total);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, precio, fecha, hora, cantidad, precio_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.productName);
            precio = itemView.findViewById(R.id.productPrice);
            fecha = itemView.findViewById(R.id.productDate);
            hora = itemView.findViewById(R.id.productTime);
            cantidad = itemView.findViewById(R.id.productQuantity);
            precio_total = itemView.findViewById(R.id.productTotalPrice);
        }
    }
}
