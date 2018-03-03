package com.example.divyang.carpark;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by divya on 25-02-2018.
 */

public class MyAlarm extends BroadcastReceiver {

    private DatabaseReference reference,mainReference;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "time up", Toast.LENGTH_LONG).show();
        mainReference = FirebaseDatabase.getInstance().getReference();

        mainReference.child("sensor").child("1").child("booked").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(context, dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                if( dataSnapshot.getValue().toString().equals("no"))
                {   Toast.makeText(context, "in if", Toast.LENGTH_LONG).show();
                    mainReference.child("sensor").child("1").child("status").setValue("no");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
