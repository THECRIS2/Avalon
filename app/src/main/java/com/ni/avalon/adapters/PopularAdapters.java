package com.ni.avalon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ni.avalon.R;
import com.ni.avalon.ViewAllActivity;
import com.ni.avalon.model.PopularModel;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {

    private Context context;
    private List<PopularModel> popularModelList;

    public PopularAdapters(Context context, List<PopularModel> popularModelList) {
        this.context = context;
        this.popularModelList = popularModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // hay que importar primero la libreria de glide
        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.popImg);
        holder.nombre.setText(popularModelList.get(position).getNombre());
        holder.descripcion.setText(popularModelList.get(position).getDescripcion());
        holder.rating.setText(popularModelList.get(position).getRaiting());
        holder.descuento.setText(popularModelList.get(position).getDescuento());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("tipo", popularModelList.get(pos).getTipo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popImg;
        TextView nombre, descripcion, rating, descuento;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // asociamos las variables
            popImg = itemView.findViewById(R.id.pop_img);
            nombre = itemView.findViewById(R.id.pop_name);
            descripcion = itemView.findViewById(R.id.pop_description);
            rating = itemView.findViewById(R.id.pop_rating);
            descuento = itemView.findViewById(R.id.pop_discount);
        }
    }
}
