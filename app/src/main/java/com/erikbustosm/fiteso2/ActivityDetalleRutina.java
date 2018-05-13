package com.erikbustosm.fiteso2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikbustosm.fiteso2.AdapterEjerciciosRutina.AdapterEjerciciosRutina;
import com.erikbustosm.fiteso2.Beans.DetallesEjercicio;
import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.Beans.Rutina;
import com.erikbustosm.fiteso2.FragmentExpEjercicios.AdapterEjercicios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityDetalleRutina extends AppCompatActivity {
    DatabaseReference firebaseDatabase;
    DatabaseReference databaseReference;
    Rutina rutina;
    private ArrayList<DetallesEjercicio> detallesEjercicioArrayList= new ArrayList<DetallesEjercicio>();
    private ArrayList<Ejercicio> ejerciciosArrayList= new ArrayList<Ejercicio>();


    ImageView image;
    TextView title,categoria;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_rutina);

        image= findViewById(R.id.activity_detalle_rutina_imageview);
        title=findViewById(R.id.activity_detalle_rutina_title);
        categoria= findViewById(R.id.activity_detalle_rutina_categoria);

        recyclerView=findViewById(R.id.activity_detalle_rutina_recyclerview);

        rutina= getIntent().getExtras().getParcelable("rutina");

        title.setText(rutina.getNombre());
        categoria.setText(rutina.getCategoria());

        new ActivityDetalleRutina.DownloadImageTask((ImageView) image)
                .execute(rutina.getImageURL());



        firebaseDatabase= FirebaseDatabase.getInstance().getReference();
        databaseReference= firebaseDatabase.child("Rutinas").child(rutina.getId()).child("detallesEjercicio");

        getDetallesEjercicioArrayList();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        Button but= findViewById(R.id.activity_detalle_rutina_bt);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] ejerciciosid= new String[detallesEjercicioArrayList.size()];
                for(int j=0;j<detallesEjercicioArrayList.size();j++){
                    ejerciciosid[j]=detallesEjercicioArrayList.get(j).getIdEjercicio();
                }

                getEjercicios(ejerciciosid);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AdapterEjerciciosRutina adapterEjercicios= new AdapterEjerciciosRutina(ejerciciosArrayList, getSupportFragmentManager());
                recyclerView.setAdapter(adapterEjercicios);

            }
        });




    }

    private void getDetallesEjercicioArrayList(){
        Query query=firebaseDatabase.child("Rutinas").child(rutina.getId()).child("detallesEjercicio");

        final ArrayList<DetallesEjercicio> list;



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> detalleseje= dataSnapshot.getChildren();

                for(final DataSnapshot detalles: detalleseje){
                    DetallesEjercicio d= detalles.getValue(DetallesEjercicio.class);
                    Log.e("detalle", d.getIdDetallesEjercicio());

                    detallesEjercicioArrayList.add(d);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getEjercicios(final String[] idejercicios){
        Query ejercicios= firebaseDatabase.child("Ejercicio");
        ejercicios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot>detalleseje=dataSnapshot.getChildren();
                for(DataSnapshot eje: detalleseje){
                    for(int i=0;i<idejercicios.length;i++) {

                        Ejercicio d = eje.getValue(Ejercicio.class);
                        if(d.getId().equals(idejercicios[i])){
                            ejerciciosArrayList.add(d);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
