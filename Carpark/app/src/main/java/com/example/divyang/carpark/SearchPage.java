package com.example.divyang.carpark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gauri on 04/03/18.
 */

public class SearchPage extends AppCompatActivity {

    private DatabaseReference reference;
    private AutoCompleteTextView actv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        reference = FirebaseDatabase.getInstance().getReference().child("Location");
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       ArrayList<String> locations = new ArrayList<>();
                       int var =0;

                       for(DataSnapshot ds : dataSnapshot.getChildren())
                       {
                           locations.add(ds.getKey());


                       }



                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getApplicationContext(),android.R.layout.simple_list_item_1,locations);
                        actv.setAdapter(adapter);
                        actv.getDropDownAnchor();
                        if(actv.isPopupShowing()){
                            Log.d("TAG","displaying!");
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }



}
