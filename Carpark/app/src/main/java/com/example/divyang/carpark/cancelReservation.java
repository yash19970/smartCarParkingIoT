package com.example.divyang.carpark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by gauri on 08/04/18.
 */

public class cancelReservation extends AppCompatActivity{
    FirebaseAuth auth;
    private DatabaseReference reference,parkingSlotReference;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_reservation);
       final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("active_parking");
        //Toast.makeText(getApplicationContext(),reference.,Toast.LENGTH_SHORT);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class)){
                    TextView cancel = (TextView)findViewById(R.id.cancelText);
                    cancel.setText("Car already in Parking. Kindly remove your car from parking slot.");

                }

                else{
                    FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("active_booking").setValue(false);
                    FirebaseDatabase.getInstance().getReference().child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren())
                            {
                                if(ds.getKey().equals(uid)){
                                    User user = ds.getValue(User.class);
                                    String parkingSlot = user.getAllocatedSlot();
                                    String parkingLocation = user.getSelectedLocation();
                                    Toast.makeText(getApplicationContext(),parkingLocation+parkingSlot,Toast.LENGTH_LONG).show();
                                   // parkingSlotReference = FirebaseDatabase.getInstance().getReference().child("Location").child(parkingLocation).child("Sensor").child(parkingSlot);
                                    parkingSlotReference.child("status").setValue("no");
                                    FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("allocated slot").setValue("");
                                    FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("SelectedLocation").setValue("");


                                    break;
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    TextView canceled = (TextView)findViewById(R.id.cancelText);
                    canceled.setText("Reservation Cancelled. Please go back to reserve another slot.");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
