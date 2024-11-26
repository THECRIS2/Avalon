package com.ni.avalon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ni.avalon.MqttHandler;
import com.ni.avalon.ui.orders.MyOrdersFragment;
import com.ni.avalon.R;
import com.ni.avalon.model.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> list;
    private MyOrdersFragment fragment;
    private static final String BROKER = "tcp://broker.emqx.io:1883";
    private static final String CLIENT_ID = "mqtt_crisM_"; // CLIENT_ID único
    private static final String TOPIC_SUB = "lab/redes/android";
    private MqttHandler mqttHandler;

    public OrderAdapter(Context context, List<OrderModel> list, MyOrdersFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;

        // Inicializar mqtt
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER, CLIENT_ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel model = list.get(position);


        holder.nombreOrden.setText(model.getProductNombre() != null ? model.getProductNombre() : "N/A");
        holder.precioItem.setText(model.getProductPrecio() != null ? model.getProductPrecio() : "N/A");
        holder.fechaOrden.setText(model.getCurrentDate() != null ? model.getCurrentDate() : "N/A");
        holder.horaOrden.setText(model.getCurrentTime() != null ? model.getCurrentTime() : "N/A");
        holder.precioTotalOrden.setText(model.getPrecioTotal() != null ? model.getPrecioTotal() : "N/A");
        holder.cantidadTotalOrden.setText(model.getCantTotal() != null ? model.getCantTotal() : "N/A");

        // boton confirmar ordem
        holder.confOrden.setOnClickListener(v -> {
            if (fragment != null) {
                fragment.deleteOrder(holder.getAdapterPosition(), model.getDocumentid());
            }
        });

        // botón mqtt
        holder.mqttButton.setOnClickListener(v -> {
            if (mqttHandler == null) {
                Toast.makeText(context, "Error: MqttHandler no configurado", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // crear mensaje con la información de la orden
                String orderMessage = String.format(
                        "Orden: %s\nPrecio: %s\nFecha: %s\nHora: %s\nTotal: %s\nCantidad: %s",
                        model.getProductNombre() != null ? model.getProductNombre() : "Desconocido",
                        model.getProductPrecio() != null ? model.getProductPrecio() : "Desconocido",
                        model.getCurrentDate() != null ? model.getCurrentDate() : "Desconocido",
                        model.getCurrentTime() != null ? model.getCurrentTime() : "Desconocido",
                        model.getPrecioTotal() != null ? model.getPrecioTotal() : "Desconocido",
                        model.getCantTotal() != null ? model.getCantTotal() : "Desconocido"
                );

                // publica el mensaje en mqtt
                mqttHandler.publish(TOPIC_SUB, orderMessage);
                Toast.makeText(context, "Orden enviada a MQTT", Toast.LENGTH_SHORT).show();
                Log.d("MQTT", "Mensaje publicado: " + orderMessage);
            } catch (Exception e) {
                Toast.makeText(context, "Error al enviar a MQTT: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreOrden, precioItem, fechaOrden, horaOrden, precioTotalOrden, cantidadTotalOrden;
        Button confOrden, mqttButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreOrden = itemView.findViewById(R.id.orderName);
            precioItem = itemView.findViewById(R.id.orderPrice);
            fechaOrden = itemView.findViewById(R.id.orderDate);
            horaOrden = itemView.findViewById(R.id.orderTime);
            precioTotalOrden = itemView.findViewById(R.id.orderTotalPrice);
            cantidadTotalOrden = itemView.findViewById(R.id.orderQuantity);
            confOrden = itemView.findViewById(R.id.confButton);
            mqttButton = itemView.findViewById(R.id.mqttButton);
        }
    }

    public void disconnectMqtt() {
        if (mqttHandler != null) {
            try {
                mqttHandler.disconnect();
            } catch (Exception e) {
                Log.e("OrderAdapter", "Error al desconectar MQTT: " + e.getMessage(), e);
            }
        } else {
            Log.w("OrderAdapter", "MqttHandler no está inicializado. No se puede desconectar.");
        }
    }
}