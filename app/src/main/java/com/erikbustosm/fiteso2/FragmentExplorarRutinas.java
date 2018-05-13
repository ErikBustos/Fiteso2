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

import com.erikbustosm.fiteso2.Beans.Rutina;
import com.erikbustosm.fiteso2.Beans.RutinaDia;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.erikbustosm.fiteso2.FragmentExpEjercicios.AdapterEjercicios;
import com.erikbustosm.fiteso2.Tools.Constants;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class FragmentExplorarRutinas extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

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
        setHasOptionsMenu(true);

        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Query rutinaDatabase= FirebaseDatabase.getInstance().getReference().child("Rutinas").orderByChild("categoria");

        rutinaDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Rutina> rutinas= new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Rutina rutina= dataSnapshot1.getValue(Rutina.class);
                    rutina.setId(rutina.getId());
                    rutinas.add(rutina);
                }
                AdapterRutina adapterRutina= new AdapterRutina(rutinas,getFragmentManager(),getContext());
                recyclerView.setAdapter(adapterRutina);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ArrayList", "load:onCancelled",databaseError.toException());
            }
        });


    }
}
