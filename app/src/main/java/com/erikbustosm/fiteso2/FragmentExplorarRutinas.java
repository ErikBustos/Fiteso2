package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erikbustosm.fiteso2.Beans.RutinaDia;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.erikbustosm.fiteso2.Tools.Constants;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class FragmentExplorarRutinas extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    ArrayList<RutinaSemanal> rutinaSemanalArrayList;
    AdapterRutina adapterRutina;


    public FragmentExplorarRutinas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_explorar_rutinas,container,false);
        recyclerView= rootView.findViewById(R.id.fragment_recycler);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView.setHasFixedSize(true);
        // Use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        rutinaSemanalArrayList=new ArrayList<>();
        rutinaSemanalArrayList=getRutinas();
        //Log.e("TAG",rutinaSemanalArrayList.get(0).getNombre());
       adapterRutina= new AdapterRutina(Constants.FRAGMENT_EXPLORAR_RUTINA, getActivity(),rutinaSemanalArrayList);
       recyclerView.setAdapter(adapterRutina);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RutinaSemanal rutinaSemanal = data.getParcelableExtra(Constants.EXTRA_RUTINA);
        Iterator<RutinaSemanal> iterator = rutinaSemanalArrayList.iterator();
        int position = 0;
        while(iterator.hasNext()){
            RutinaSemanal rutina = iterator.next();
            if(rutina.getId() == rutinaSemanal.getId()){
                rutinaSemanalArrayList.set(position, rutinaSemanal);
                break;
            }
            position++;
        }
        adapterRutina.notifyDataSetChanged();
    }

    public ArrayList<RutinaSemanal> getRutinas(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rutinasref= databaseReference.child("RutinaSemanal");

        final ArrayList<RutinaSemanal> rSemanalList= new ArrayList<>();

        rutinasref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id= dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("id").getValue(String.class);
                String image= dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("imageURL").getValue(String.class);
                String publica= dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("publica").getValue(String.class);
                String nombre= dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("nombre").getValue(String.class);
                String descripcion= dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("descripción").getValue(String.class);

                //GenericTypeIndicator<List<RutinaSemanal>> t= new GenericTypeIndicator<List<RutinaSemanal>>(){};
                //List<RutinaSemanal> rutinadia1=dataSnapshot.child("-LAskpXBBVQwj6iieUOq").getValue(t);
                //String S=dataSnapshot.child("-LAskpXBBVQwj6iieUOq").child("rutinaDia").child("0").child("id").getValue(String.class);
                rSemanalList.add(new RutinaSemanal(id,nombre,descripcion,publica,new LinkedList<RutinaDia>(),image));
                // for(DataSnapshot childDataSnapshot: dataSnapshot.getChildren()){
                    //RutinaSemanal e= childDataSnapshot.getValue(RutinaSemanal.class);

                   // Log.e("TAG",rutinadia1.get(0).getId());


                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("tag","Failed to read value.",databaseError.toException());
            }
        });

        return rSemanalList;
    }

}
