package com.example.divyang.carpark;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by divya on 02-04-2018.
 */

public class ServicePage extends android.support.v4.app.Fragment {
    View view;
    Button carwash,repair,staylonger;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.service_layout, container, false);
        carwash = (Button)view.findViewById(R.id.carwash);
        repair = (Button)view.findViewById(R.id.repair);
        staylonger = (Button)view.findViewById(R.id.staylonger);
        uid = auth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("isPrime");
        carwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue(Boolean.class)){

                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"divyang.duhsn07@gmail.com"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "Car Wash");
                            email.putExtra(Intent.EXTRA_TEXT, "Car wash has been requested");

//need this to prompts email client only
                            email.setType("message/rfc822");

                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                        else {
                            Toast.makeText(getActivity(),"sorry this service is only allowed for prime users",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue(Boolean.class)){

                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"divyang.duhsn07@gmail.com"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "Car Wash");
                            email.putExtra(Intent.EXTRA_TEXT, "Car wash has been requested AIzaSyCgRIf-_d3PypP1H-3gU0JDdoVffkiItuo");

//need this to prompts email client only
                            email.setType("message/rfc822");

                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                        else {
                            Toast.makeText(getActivity(),"sorry this service is only allowed for prime users",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;

    }

}

