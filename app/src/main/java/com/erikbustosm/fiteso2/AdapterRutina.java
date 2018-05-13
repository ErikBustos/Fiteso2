package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erikbustosm.fiteso2.Beans.Rutina;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.erikbustosm.fiteso2.FragmentExpEjercicios.FragmentDetallesEjercicio;

import java.io.InputStream;
import java.util.ArrayList;

public class AdapterRutina extends RecyclerView.Adapter<AdapterRutina.ViewHolder> {


    private FragmentManager fragmentManager;
    private ArrayList<Rutina> rutinaArrayList;
    private Context context;


    public AdapterRutina(ArrayList<Rutina> rutinas, FragmentManager fragmentManager,Context context) {
        this.rutinaArrayList = rutinas;
        this.fragmentManager = fragmentManager;
        this.context= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rutina, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nombre.setText(rutinaArrayList.get(holder.getAdapterPosition()).getNombre());
        //    rutina.get(holder.getAdapterPosition()).getRutinaDia();
        holder.descripcion.setText(rutinaArrayList.get(holder.getAdapterPosition()).getDescripcion());
        holder.categoria.setText(rutinaArrayList.get(holder.getAdapterPosition()).getCategoria());

        new DownloadImageTask((ImageView) holder.image)
                .execute(rutinaArrayList.get(holder.getAdapterPosition()).getImageURL());

        holder.detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.image.buildDrawingCache();
                //Bitmap image= holder.image.getDrawingCache();


                Intent intent = new Intent(context,ActivityDetalleRutina.class);
                intent.putExtra("rutina", rutinaArrayList.get(holder.getAdapterPosition()));
               // intent.putExtra("image",image);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rutinaArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView categoria;
        TextView descripcion;
        ImageView image;
        RelativeLayout mDetail;
        Button detalles;


        ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.item_rutina_nombre);
            descripcion = v.findViewById(R.id.item_rutina_descripción);
            image = v.findViewById(R.id.item_rutina_image);
            categoria = v.findViewById(R.id.item_rutina_categoria);
            mDetail = v.findViewById(R.id.item_rutina_layout);
            image= v.findViewById(R.id.item_rutina_image);
            detalles= v.findViewById(R.id.item_rutina_verdescripcion);

        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        public ImageView getBmImage() {
            return bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

            }
            // mIcon11= resizeBitmap(mIcon11,400,600);
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
        }
    }
}
