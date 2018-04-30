package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth=FirebaseAuth.getInstance();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent intent;
                if(mAuth.getCurrentUser() != null){
                    intent = new Intent(ActivitySplash.this, ActivityMain.class);
                }else{
                    intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                }
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }





}
