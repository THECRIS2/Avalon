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
import com.ni.avalon.activities.ViewAllActivity;
import com.ni.avalon.model.HomeCategory;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<HomeCategory> categoryList;

    public HomeAdapter(Context context, List<HomeCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(categoryList.get(position).getUrl_img()).into(holder.catImg);
        holder.nombre.setText(categoryList.get(position).getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("tipo", categoryList.get(pos).getTipo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size(); // esta linea de codigo permite visualizar nuestras cardview
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView nombre;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.hom_cat_img);
            nombre = itemView.findViewById(R.id.cat_home_name);
        }
    }
}
