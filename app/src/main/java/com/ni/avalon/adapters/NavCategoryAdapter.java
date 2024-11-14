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
import com.ni.avalon.activities.NavCategoryActivity;
import com.ni.avalon.activities.ViewAllActivity;
import com.ni.avalon.model.NavCategoryModel;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

    private Context context;
    private List<NavCategoryModel> list;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.img_nav_cat);
        holder.nombre.setText(list.get(position).getNombre());
        holder.descripcion.setText(list.get(position).getDescripcion());
        holder.descuento.setText(list.get(position).getDescuento());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, NavCategoryActivity.class);
                intent.putExtra("tipo", list.get(pos).getTipo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_nav_cat;
        TextView nombre, descripcion, descuento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_nav_cat = itemView.findViewById(R.id.cat_nav_img);
            nombre = itemView.findViewById(R.id.cat_nav_nombre);
            descripcion = itemView.findViewById(R.id.cat_nav_descripcion);
            descuento = itemView.findViewById(R.id.cat_nav_discount);
        }
    }
}
