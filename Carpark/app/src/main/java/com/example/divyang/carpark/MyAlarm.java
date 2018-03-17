package com.example.divyang.carpark;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    String[] str = new String[2];
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "time up", Toast.LENGTH_LONG).show();
        Intent i = intent;
        Bundle bundle = new Bundle();
      bundle = i.getBundleExtra("abc");
           str = bundle.getStringArray("data");
        mainReference = FirebaseDatabase.getInstance().getReference().child("Location").child(str[0]);
        Toast.makeText(context, str[0], Toast.LENGTH_LONG).show();

        mainReference.child("Sensor").child(str[1]).child("booked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(context, dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                if( dataSnapshot.getValue().toString().equals("no"))
                {   Toast.makeText(context, "in if", Toast.LENGTH_LONG).show();
                    mainReference.child("Sensor").child(str[1]).child("status").setValue("no");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
