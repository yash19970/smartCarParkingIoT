package com.example.divyang.carpark;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by divya on 22-01-2018.
 */
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;

public class bookingHistory extends Fragment {
    View view;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String uid;
    private String locationValue;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_history, container, false);

    uid = auth.getInstance().getCurrentUser().getUid();

    locationValue = user.getSelectedLocation();


    databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("bookingHistories");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            bookingHistoryObject bookingHistoryObject = dataSnapshot.getValue(bookingHistoryObject.class);
            User user = dataSnapshot.getValue(User.class);
            bookingHistoryObject.locationValue = user.getSelectedLocation();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    String bookingId = databaseReference.push().getKey();
    bookingHistoryObject obj = new bookingHistoryObject(new Date(),new Date(),locationValue);



return view;
    }
}