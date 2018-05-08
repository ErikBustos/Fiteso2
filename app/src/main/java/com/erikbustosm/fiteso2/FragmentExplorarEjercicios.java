package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.FragmentExpEjercicios.AdapterEjercicios;
import com.erikbustosm.fiteso2.Tools.Constants;
import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**

 */
public class FragmentExplorarEjercicios extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView ejerciciosList;

    public FragmentExplorarEjercicios() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_explorar_ejercicios,container,false);
        ejerciciosList=view.findViewById(R.id.fragment_explorar_recyclerview);
        setHasOptionsMenu(true);

        ejerciciosList.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getContext());
        ejerciciosList.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query ejerciciosDatabase= FirebaseDatabase.getInstance().getReference().child("Ejercicio").orderByChild("categoria");

        ejerciciosDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Ejercicio> ejercicios= new ArrayList<>();
                for( DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Ejercicio ejercicio=dataSnapshot1.getValue(Ejercicio.class);
                    ejercicio.setId(ejercicio.getId());
                    ejercicios.add(ejercicio);


                }
                AdapterEjercicios adapterEjercicios= new AdapterEjercicios(ejercicios,getFragmentManager());
                ejerciciosList.setAdapter(adapterEjercicios);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("EjerciciosList", "load:onCancelled", databaseError.toException());
            }
        });
    }
}
