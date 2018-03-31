package com.example.divyang.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gauri on 04/03/18.
 */

public class SearchPage extends AppCompatActivity {

    private DatabaseReference reference, reference2;
    private AutoCompleteTextView actv;
    String  locationName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        reference = FirebaseDatabase.getInstance().getReference().child("Location");
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllSuggestions(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getAllSuggestions(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void getAllSuggestions(DataSnapshot dataSnapshot) {

        final ArrayList<String> locations = new ArrayList<>();
        int var =0;

        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            locations.add(ds.getKey());

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.simple_list_item_1,locations);
        actv.setAdapter(adapter);
        actv.getDropDownAnchor();
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId)
            {
                locationName= (String) parent.getItemAtPosition(position);//locationName
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reference2 = FirebaseDatabase.getInstance().getReference("User").child(userId).child("SelectedLocation");
                reference2.setValue(locationName);
                bookingHistoryObject bookingHistoryObject = new bookingHistoryObject();
            //    bookingHistoryObject.locationValue = locationName;

                SimpleDateFormat sdfDestination = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");


                Date dateToBeFormatted = new Date(System.currentTimeMillis());
                String date = sdfDestination.format(dateToBeFormatted);
                bookingHistoryObject obj = new bookingHistoryObject(date,date,locationName);
                FirebaseDatabase.getInstance().getReference("User").child(userId).child("booking history").push().setValue(obj);





                Intent i = new Intent(SearchPage.this,NavMain.class);
                i.putExtra("locationName", locationName);
                startActivity(i);

            }
        });


    }


}
