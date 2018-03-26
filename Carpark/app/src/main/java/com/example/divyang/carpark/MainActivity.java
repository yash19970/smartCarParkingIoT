package com.example.divyang.carpark;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Fragment {
    private Button enter,exit,book,parked,logout;
    private DatabaseReference locationreference,mainReference,sensorlocationreference;
    private FirebaseAuth auth;
    private TextView number,status;
    View view;
    String locationName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        enter = (Button) view.findViewById(R.id.enter);
        book = (Button) view.findViewById(R.id.book);
        logout = (Button) view.findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        parked = (Button) view.findViewById(R.id.parked);
        exit = (Button) view.findViewById(R.id.exit);
        number = (TextView) view.findViewById(R.id.number1);
        status = (TextView) view.findViewById(R.id.status);
        locationName = getArguments().getString("locationName");

        locationreference = FirebaseDatabase.getInstance().getReference().child("Location").child(locationName).child("TotalSlots");
        sensorlocationreference = FirebaseDatabase.getInstance().getReference().child("Location").child(locationName);
        mainReference = FirebaseDatabase.getInstance().getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                startActivity(new Intent(getActivity(), loginActivity.class));
                getActivity().finish();
            }
        });




        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carParked(locationreference);

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationreference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String slots = dataSnapshot.getValue().toString();
                        int slot;
                        slot = Integer.parseInt(slots);
                        if (slot == 0) {
                            Toast.makeText(getActivity(), "Empty", Toast.LENGTH_LONG).show();
                        } else {
                            slot--;
                            slots = String.valueOf(slot);
                            locationreference.setValue(slots);
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        locationreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String slots = dataSnapshot.getValue().toString();
                int slot;
                slot = Integer.parseInt(slots);
                number.setText(slots);
                if (slot == 0) {
                    status.setText("FULL");
                    number.setTextColor(Color.RED);
                    status.setTextColor(Color.RED);
                } else {
                    status.setText("Available");
                    number.setTextColor(Color.GREEN);
                    status.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] str = new String[2];
                str[0] = locationName;
                Bundle bundle = new Bundle();
                bundle.putStringArray("abc",str);
                Intent intent = new Intent(getActivity(),ConfirmationPage.class);
                intent.putExtra("data",bundle);
                startActivity(intent);

            }
        });



        return view;
    }



        void carParked(final DatabaseReference references){

            references.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String slots = dataSnapshot.getValue().toString();
                    int slot;
                    slot = Integer.parseInt(slots);
                    if (slot == 60) {
                        Toast.makeText(getActivity(), "Already FULL", Toast.LENGTH_LONG).show();
                    } else {
                        slot++;
                        slots = String.valueOf(slot);
                        references.setValue(slots);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}





