package com.example.divyang.carpark;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by divya on 22-01-2018.
 */
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class bookingHistory extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String uid;
    private String locationValue;
    private User user;
    private ListView listView;
    private ArrayList<String> locations;
    private ArrayList<String> inTime;
    private ArrayList<String> outTime;
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_history, container, false);
        CustomListAdapter whatever = new CustomListAdapter(getActivity(), locations, inTime, outTime);
        listView = (ListView)view.findViewById(R.id.listViewId);
        listView = (ListView)view.findViewById(R.id.listViewId);
        listView.setAdapter(whatever);
        uid = auth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(uid).child("bookingHistories");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // bookingHistoryObject bookingHistoryObject = dataSnapshot.getValue(bookingHistoryObject.class);

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    bookingHistoryObject obj = ds.getValue(bookingHistoryObject.class);
                    locations.add(obj.getLocationValue());
                    inTime.add(obj.getInTime());
                    outTime.add(obj.getOutTime());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;

    }

}

