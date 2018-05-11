package com.erikbustosm.fiteso2.FragmentExpEjercicios;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikbustosm.fiteso2.FragmentExplorarEjercicios;
import com.erikbustosm.fiteso2.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetallesEjercicio extends android.support.v4.app.Fragment {
    String id;
    TextView nombre,categoriaText;
    TextView decripcionText;
    DatabaseReference databaseReference;
    String name,decripcion,categoria;
    String urlPic;
    ImageView ejercicioPic;

    public FragmentDetallesEjercicio() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_ejercicio, container, false);

        id = getArguments().getString("id");




        nombre = view.findViewById(R.id.ejercicio_detalle_nombre);
        decripcionText= view.findViewById(R.id.ejercicio_detalle_descripcion);
        categoriaText= view.findViewById(R.id.ejercicio_detalle_categoria);
        ejercicioPic= view.findViewById(R.id.ejercicio_detalle_imageview);
        // Inflate the layout for this fragment




        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment fragmentExplorarEjercicios= new FragmentExplorarEjercicios();
                FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction().replace(R.id.fragment_ejercicios_relative_layout,fragmentExplorarEjercicios);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Ejercicio").child(id);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name= dataSnapshot.child("nombre").getValue(String.class);
                decripcion=dataSnapshot.child("descripcion").getValue(String.class);
                categoria= dataSnapshot.child("categoria").getValue(String.class);
                urlPic= dataSnapshot.child("photoURL").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();

        nombre.setText(name);
        decripcionText.setText(decripcion);
        categoriaText.setText(categoria);

        new DownloadImageTask((ImageView) ejercicioPic)
                .execute(urlPic);


    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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



    private Bitmap resizeBitmap(Bitmap bitmap, float scaleHeight, float scaleWidth)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = (int)(width * scaleWidth);
        int newHeight = (int)(height * scaleHeight);

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }


}

