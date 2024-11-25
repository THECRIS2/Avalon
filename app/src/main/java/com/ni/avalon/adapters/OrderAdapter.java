package com.ni.avalon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ni.avalon.ui.orders.MyOrdersFragment;
import com.ni.avalon.R;
import com.ni.avalon.model.OrderModel;


import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> list;
    private MyOrdersFragment fragment;

    public OrderAdapter(Context context, List<OrderModel> list, MyOrdersFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel model = list.get(position);

        // Validar datos antes de asignarlos
        holder.nombreOrden.setText(model.getProductNombre() != null ? model.getProductNombre() : "N/A");
        holder.precioItem.setText(model.getProductPrecio() != null ? model.getProductPrecio() : "N/A");
        holder.fechaOrden.setText(model.getCurrentDate() != null ? model.getCurrentDate() : "N/A");
        holder.horaOrden.setText(model.getCurrentTime() != null ? model.getCurrentTime() : "N/A");
        holder.precioTotalOrden.setText(model.getPrecioTotal() != null ? model.getPrecioTotal() : "N/A");
        holder.cantidadTotalOrden.setText(model.getCantTotal() != null ? model.getCantTotal() : "N/A");

        holder.confOrden.setOnClickListener(v -> {
            if (fragment != null) {
                fragment.deleteOrder(holder.getAdapterPosition(), model.getDocumentid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreOrden, precioItem, fechaOrden, horaOrden, precioTotalOrden, cantidadTotalOrden;
        Button confOrden;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreOrden = itemView.findViewById(R.id.orderName);
            precioItem = itemView.findViewById(R.id.orderPrice);
            fechaOrden = itemView.findViewById(R.id.orderDate);
            horaOrden = itemView.findViewById(R.id.orderTime);
            precioTotalOrden = itemView.findViewById(R.id.orderTotalPrice);
            cantidadTotalOrden = itemView.findViewById(R.id.orderQuantity);
            confOrden = itemView.findViewById(R.id.confButton);
        }
    }
}