package com.example.divyang.carpark;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button enter,exit,book,parked;
    private DatabaseReference reference,mainReference;
    private TextView number,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enter = (Button) findViewById(R.id.enter);
        book = (Button) findViewById(R.id.book);
        parked = (Button) findViewById(R.id.parked);
        exit = (Button) findViewById(R.id.exit);
        number = (TextView) findViewById(R.id.number);
        status = (TextView) findViewById(R.id.status);
        reference = FirebaseDatabase.getInstance().getReference().child("parking");//pointing to null
        mainReference = FirebaseDatabase.getInstance().getReference();


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carParked(reference);

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String slots = dataSnapshot.getValue().toString();
                        int slot;
                        slot = Integer.parseInt(slots);
                        if (slot == 0) {
                            Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_LONG).show();
                        } else {
                            slot--;
                            slots = String.valueOf(slot);
                            reference.setValue(slots);
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String slots = dataSnapshot.getValue().toString();
                int slot;
                slot = Integer.parseInt(slots);
                number.setText(slots);
                if (slot == 60) {
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

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                mainReference.child("timestamp").setValue(ServerValue.TIMESTAMP);

                Toast.makeText(MainActivity.this, "booked", Toast.LENGTH_SHORT).show();
                carParked(reference);
            }
        });
    }
        //check internet connection


        void carParked(final DatabaseReference references){

            references.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String slots = dataSnapshot.getValue().toString();
                    int slot;
                    slot = Integer.parseInt(slots);
                    if (slot == 60) {
                        Toast.makeText(MainActivity.this, "Already FULL", Toast.LENGTH_LONG).show();
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





