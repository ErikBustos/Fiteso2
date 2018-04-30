package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.Tools.Constants;
import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**

 */
public class FragmentExplorarEjercicios extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Ejercicio> ejercicioArrayList;
    AdapterEjercicioRow adapterEjercicioRow;


    public FragmentExplorarEjercicios() {

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

        ejercicioArrayList= new ArrayList<>();
       // ejercicioArrayList.add(new Ejercicio("id1","hola","no se","Pecho","f"));
       // ejercicioArrayList.add(new Ejercicio("id2","hoadda","nasfo se","Pefcho","f"));
        ejercicioArrayList=getEjercicios();

        adapterEjercicioRow= new AdapterEjercicioRow(Constants.FRAGMENT_EXPLORAR_EJERCICIOS,getActivity(),ejercicioArrayList);
        recyclerView.setAdapter(adapterEjercicioRow);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Ejercicio ejercicio= data.getParcelableExtra(Constants.EXTRA_Ejercicio);
        Iterator<Ejercicio> iterator= ejercicioArrayList.iterator();
        int position=0;
        while(iterator.hasNext()){
            Ejercicio ejercicio1= iterator.next();
            if(ejercicio1.getId().equals(ejercicio.getId())){
                ejercicioArrayList.set(position,ejercicio);
                break;
            }
            position++;
        }
        adapterEjercicioRow.notifyDataSetChanged();

    }



    public ArrayList<Ejercicio> getEjercicios(){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ejercicioref= databaseReference.child("Ejercicio");

        final ArrayList<Ejercicio> ejercicioArrayList= new ArrayList<>();


        ejercicioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot childDataSnapshot: dataSnapshot.getChildren()){
                    Ejercicio e= childDataSnapshot.getValue(Ejercicio.class);
                    ejercicioArrayList.add(e);

                }


               // String categoria = dataSnapshot.child("categoria").getValue(String.class);
                //String descripcion = dataSnapshot.child("descripcion").getValue(String.class);
                //String id=  dataSnapshot.child("id").getValue(String.class);
                //String nombre=  dataSnapshot.child("nombre").getValue(String.class);
                //String photoURL=  dataSnapshot.child("photoURL").getValue(String.class);

                //Ejercicio ejercicio= new Ejercicio(id,nombre,descripcion,categoria,photoURL);
                //ejercicioArrayList.add(ejercicio);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return ejercicioArrayList ;
    }


}
