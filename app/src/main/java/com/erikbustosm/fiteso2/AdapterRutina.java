package com.erikbustosm.fiteso2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erikbustosm.fiteso2.Beans.RutinaSemanal;

import java.util.ArrayList;

public class AdapterRutina extends RecyclerView.Adapter<AdapterRutina.ViewHolder> {
    private ArrayList<RutinaSemanal> rutina;
    private Context context;
    private int fragment;


    public AdapterRutina(int fragment, Context context, ArrayList<RutinaSemanal> rutina) {
        this.rutina = rutina;
        this.context = context;
        this.fragment = fragment;

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView descripcion;
        ImageView image;
        RelativeLayout mDetail;

        ViewHolder(View v){
            super(v);
            nombre=v.findViewById(R.id.item_rutina_nombre);

            descripcion=v.findViewById(R.id.item_rutina_descripción);
            image=v.findViewById(R.id.item_rutina_image);
            mDetail=v.findViewById(R.id.item_rutina_layout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rutina, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(rutina.get(holder.getAdapterPosition()).getNombre());
        //    rutina.get(holder.getAdapterPosition()).getRutinaDia();
        holder.descripcion.setText(rutina.get(holder.getAdapterPosition()).getDescripción());
    }

    @Override
    public int getItemCount() {
        return rutina.size();
    }


}
