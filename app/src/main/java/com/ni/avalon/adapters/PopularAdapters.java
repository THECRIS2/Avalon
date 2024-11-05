package com.ni.avalon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ni.avalon.R;
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
        Glide.with(context).load(popularModelList.get(position).getImg_UrlP()).into(holder.popImg);
        holder.NombreP.setText(popularModelList.get(position).getNombreP());
        holder.DescripcionP.setText(popularModelList.get(position).getDescripcionP());
        holder.RatingP.setText(popularModelList.get(position).getRaitingP());
        holder.DescuentoP.setText(popularModelList.get(position).getDescuentoP());
    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popImg;
        TextView NombreP, DescripcionP, RatingP, DescuentoP;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // asociamos las variables
            popImg = itemView.findViewById(R.id.pop_img);
            NombreP = itemView.findViewById(R.id.pop_name);
            DescripcionP = itemView.findViewById(R.id.pop_description);
            RatingP = itemView.findViewById(R.id.pop_rating);
            DescuentoP = itemView.findViewById(R.id.pop_discount);
        }
    }
}
