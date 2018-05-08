package com.erikbustosm.fiteso2.FragmentExpEjercicios;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erikbustosm.fiteso2.FragmentExplorarEjercicios;
import com.erikbustosm.fiteso2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetallesEjercicio extends android.support.v4.app.Fragment {
    String id;
    TextView nombre;

    public FragmentDetallesEjercicio() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_ejercicio, container, false);
        nombre = view.findViewById(R.id.ejercicio_detalle_nombre);
        // Inflate the layout for this fragment
        id = getArguments().getString("id");

        nombre.setText(id);

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



}
