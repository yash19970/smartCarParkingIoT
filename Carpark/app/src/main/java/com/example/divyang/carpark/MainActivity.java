package com.example.divyang.carpark;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private DatabaseReference reference;
    private EditText value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        value = (EditText)findViewById(R.id.value);
        reference = FirebaseDatabase.getInstance().getReference();//pointing to null
        //check internet connection
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create a child in root object
                // assign some value to that child
                String getVal=null;
                getVal = value.getText().toString();
                if(getVal!=null) {
                    reference.child("Name").setValue(getVal);
                    Toast.makeText(getApplicationContext(), "child created", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
