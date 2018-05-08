package com.erikbustosm.fiteso2.FragmentExpEjercicios;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.FragmentConfiguracion;
import com.erikbustosm.fiteso2.FragmentExplorarEjercicios;
import com.erikbustosm.fiteso2.R;

import java.util.ArrayList;

public class AdapterEjercicios extends RecyclerView.Adapter<AdapterEjercicios.ViewHolder>{

    private android.support.v4.app.FragmentManager fragmentManager;

    ArrayList<Ejercicio> ejercicioArrayList;
    public AdapterEjercicios(ArrayList<Ejercicio> ejercicios, android.support.v4.app.FragmentManager fragmentmanager){
        this.ejercicioArrayList=ejercicios;
        this.fragmentManager=fragmentmanager;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ejercicio, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(ejercicioArrayList.get(position).getNombre());
        holder.categoria.setText(ejercicioArrayList.get(position).getCategoria());
        final String id= ejercicioArrayList.get(position).getId();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.Fragment detalle= new FragmentDetallesEjercicio();
                Bundle args= new Bundle();
                args.putString("id", id);
                detalle.setArguments(args);
                android.support.v4.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_ejercicios_relative_layout,detalle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ejercicioArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,categoria;
        public String id;

        View view;

        public ViewHolder(View v){
            super(v);
            view=v;
            name=v.findViewById(R.id.item_ejercicio_title);
            categoria=v.findViewById(R.id.item_ejercicio_categoria);

        }
    }

}
