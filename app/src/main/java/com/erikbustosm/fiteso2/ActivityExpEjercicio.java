package com.erikbustosm.fiteso2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class ActivityExpEjercicio extends AppCompatActivity {

    String id;
    TextView nombre,categoriaText;
    TextView decripcionText;
    DatabaseReference databaseReference;
    String name,decripcion,categoria;
    String urlPic;
    ImageView ejercicioPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_ejercicio);
    }
}
