package com.erikbustosm.fiteso2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentConfiguracion extends android.app.Fragment {

    private static final String ARG_SECTION_TITLE = "Configuración";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentConfiguracion() {
    }

    public static FragmentConfiguracion newInstance(String param1) {
        FragmentConfiguracion fragment = new FragmentConfiguracion();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_configuracion, container, false);

       Button guardar = view.findViewById(R.id.fragment_configuracion_guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        return view;
    }


}
