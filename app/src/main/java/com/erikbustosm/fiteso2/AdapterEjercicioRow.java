package com.erikbustosm.fiteso2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.Beans.Ejercicio;

import java.util.ArrayList;

public class AdapterEjercicioRow extends RecyclerView.Adapter<AdapterEjercicioRow.ViewHolder> {
    private ArrayList<Ejercicio> ejercicios;
    private Context context;
    private int fragment;

    public AdapterEjercicioRow(int fragment, Context context, ArrayList<Ejercicio> products) {
        this.ejercicios = products;
        this.context = context;
        this.fragment = fragment;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView subtittleTextView;

        RelativeLayout mDetail;


        ViewHolder(View v){
            super(v);
            titleTextView= v.findViewById(R.id.ejercicio_row_titleTextView);
            titleTextView.setMovementMethod(new ScrollingMovementMethod());
            subtittleTextView=v.findViewById(R.id.ejercicio_row_categoria);
            mDetail= v.findViewById(R.id.ejercicio_row_layout);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getSubtittleTextView() {
            return subtittleTextView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ejercicio_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(ejercicios.get(holder.getAdapterPosition()).getNombre());
        holder.subtittleTextView.setText(ejercicios.get(holder.getAdapterPosition()).getCategoria());

        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "ejercicio", Toast.LENGTH_SHORT).show();
                /*
                Toast.makeText(context, products.get(holder.getAdapterPosition()).toString(),
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ActivityProduct.class);
                intent.putExtra(Constant.EXTRA_PRODUCT, products.get(holder.getAdapterPosition()));
                intent.putExtra(Constant.EXTRA_FRAGMENT, fragment);
                ((ActivityMain) context).startActivityForResult(intent, Constant.ACTIVITY_DETAIL);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }


}
