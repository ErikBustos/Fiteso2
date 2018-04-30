package com.erikbustosm.fiteso2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.erikbustosm.fiteso2.Beans.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentProfile extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_SECTION_TITLE = "section_number";


    FirebaseAuth mAuth;
    CircleImageView profilepic;
    TextView nombre,deporte,edad;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 name 1.
     *
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance() {
        FragmentProfile fragment = new FragmentProfile();
        //Bundle args = new Bundle();
       // args.putString(ARG_SECTION_TITLE, title);
       // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth=FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        profilepic= view.findViewById(R.id.activity_profile_profilePic);
        nombre= view.findViewById(R.id.activity_profile_name_textview);
        deporte= view.findViewById(R.id.activity_profile_deporte_textview);
        edad=view.findViewById(R.id.activity_profile_edad_textview);


        loadUserInformation();
        return view;
    }

    private void loadUserInformation(){
        FirebaseUser user=mAuth.getCurrentUser();

        if(user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(profilepic);
            }
            if (user.getDisplayName() != null) {
                nombre.setText(user.getDisplayName());
            }
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersref= databaseReference.child("Users");


            DatabaseReference currentuserref=usersref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            currentuserref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user1= dataSnapshot.getValue(User.class);
                    //String deportestr =dataSnapshot.getValue(String.class);
                    deporte.setText(user1.getDeporte());
                    edad.setText(user1.getAge()+" años");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        //String photoUrl= user.getPhotoUrl().toString();
        //String name= user.getDisplayName();
    }
}
