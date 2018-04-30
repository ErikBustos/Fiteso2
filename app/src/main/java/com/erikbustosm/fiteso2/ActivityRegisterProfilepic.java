package com.erikbustosm.fiteso2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erikbustosm.fiteso2.Beans.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ActivityRegisterProfilepic extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 1111;
    ImageView cameraImageView;
    EditText editText,ageEdittext;
    Button saveButton;
    Uri profileimage;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth mAuth;
    DatabaseReference databaseuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profilepic);

        mAuth=FirebaseAuth.getInstance();

        editText= findViewById(R.id.activity_register_profilepic_editTextDisplayName);
        cameraImageView= findViewById(R.id.activity_register_profilepic_cameraImageView);
        saveButton=findViewById(R.id.activity_register_profilepic_buttonSave);
        progressBar=findViewById(R.id.activity_register_profilepic_progressbar);
        ageEdittext=findViewById(R.id.activity_register_profilepic_edadEditText);

        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
                finish();
                startActivity(new Intent(ActivityRegisterProfilepic.this, ActivityRegisterSeleccionDeporte.class));

            }
        });

    }
    private void saveUserInformation() {

        String displayName = editText.getText().toString().trim();
        String ageString= ageEdittext.getText().toString().trim();
        int age=Integer.parseInt(ageString);

        if (displayName.isEmpty()) {
            editText.setError("Ingresa el nombre");
            editText.requestFocus();
            return;
        }
        if(ageString.isEmpty()){
            ageEdittext.setError("Ingresa la edad");
            ageEdittext.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();



        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ActivityRegisterProfilepic.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            User user1 = new User();
            user1.setName(displayName);
            user1.setAge(age);
            user1.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        finish();
                        startActivity(new Intent(ActivityRegisterProfilepic.this, ActivityRegisterSeleccionDeporte.class));


                    } else {

                    }
                }
            });
        }
    }

    private void showImageChooser(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona foto de perfil"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode== CHOOSE_IMAGE && resultCode == RESULT_OK && data!= null && data.getData()!= null){
            profileimage= data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profileimage);
                cameraImageView.setImageBitmap(bitmap);

                uploadImagetoFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void uploadImagetoFireBaseStorage(){
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");


        if(profileimage!=null) {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(profileimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);

                    profileImageUrl= taskSnapshot.getDownloadUrl().toString();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ActivityRegisterProfilepic.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
