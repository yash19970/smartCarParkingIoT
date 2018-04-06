package com.example.divyang.carpark;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by gauri on 06/04/18.
 */

public class Profile extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String uid;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);

        uid = auth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                User user = dataSnapshot.getValue(User.class);
                String email = user.getEmail();
                String name = user.getUsername();
                    TextView name1 = (TextView)view.findViewById(R.id.name);
                    TextView email1 = (TextView)view.findViewById(R.id.email);
                    name1.setText(name);
                    email1.setText(email);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    return view;
    }
}
