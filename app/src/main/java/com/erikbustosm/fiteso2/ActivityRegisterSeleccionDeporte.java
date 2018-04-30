package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegisterSeleccionDeporte extends AppCompatActivity {
    FirebaseAuth mAuth;
    Spinner spinnerdeporte;
    Button buttonsiguiente;

   // static String deportespiner=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_seleccion_deporte);
        mAuth = FirebaseAuth.getInstance();

        spinnerdeporte = findViewById(R.id.activity_register_seleccion_deporte_spinner);
        buttonsiguiente = findViewById(R.id.activity_register_seleccion_deporte_buttonSiguente);






        buttonsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deportespiner=spinnerdeporte.getSelectedItem().toString();
                if (deportespiner == null) {
                    Toast.makeText(ActivityRegisterSeleccionDeporte.this, "Selecciona un deporte", Toast.LENGTH_SHORT).show();

                } else {
                    //User user = new User();
                   // user.setDeporte(spinnerdeporte.getSelectedItem().toString());
                    //user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReference.child("deporte").setValue(spinnerdeporte.getSelectedItem().toString());
                    finish();
                    startActivity(new Intent(ActivityRegisterSeleccionDeporte.this, ActivityMain.class));
                    //updating user

                }
            }
        });



    }





}
