package com.example.divyang.carpark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gauri on 08/04/18.
 */

public class cancelReservation extends AppCompatActivity{
    FirebaseAuth auth;
    private DatabaseReference reference,parkingSlotReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("active_parking");

        if (reference.equals(true)) {
            Toast.makeText(getApplicationContext(),"Car already in Parking. Kindly remove your car from parking slot.",Toast.LENGTH_LONG).show();
        }
        else{
            FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("active_booking").setValue(false);
            String parkingSlot = FirebaseDatabase.getInstance().getReference().child("User").child("allocated slot").getKey();
            parkingSlotReference = FirebaseDatabase.getInstance().getReference().child("Sensor").child(parkingSlot);
            parkingSlotReference.child("status").setValue(false);

        }
    }
}
