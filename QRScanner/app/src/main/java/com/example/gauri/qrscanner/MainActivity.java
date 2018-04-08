package com.example.gauri.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button button;
    DatabaseReference firebaseDatabase,mainreference,reference,bookingHistoryReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button) findViewById (R.id.button);
        final Activity activity = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null ){
                Toast.makeText(this,"Scan cancelled", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                final String[] scannedString = result.getContents().toString().split("/");
                String uid = scannedString[0];
                firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("User");
                mainreference = FirebaseDatabase.getInstance().getReference().child("Location").child(scannedString[1]).child("Sensor");
                reference = FirebaseDatabase.getInstance().getReference().child("Location").child(scannedString[1]).child("TotalSlots");
                bookingHistoryReference = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("booking History");


                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Toast.makeText(getApplicationContext(),"inside datasnapshot",Toast.LENGTH_LONG).show();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            if(ds.getKey().equals(scannedString[0])){
                                User user = ds.getValue(User.class);
                                if(user.isActive_booking() && !user.isActive_parking()) {
                                    firebaseDatabase.child(ds.getKey()).child("active_parking").setValue(true);
                                    Date date = new Date(System.currentTimeMillis());
                                    bookingHistoryReference.child(ds.getKey()).child("inTime").setValue(date.toString());
                                }

                                if(user.isActive_booking() && user.isActive_parking()){
                                    firebaseDatabase.child(ds.getKey()).child("active_parking").setValue(false);
                                    firebaseDatabase.child(ds.getKey()).child("active_booking").setValue(false);
                                    firebaseDatabase.child(ds.getKey()).child("SelectedLocation").setValue("");
                                    firebaseDatabase.child(ds.getKey()).child("allocated slot").setValue("");
                                    Date date = new Date(System.currentTimeMillis());
                                    bookingHistoryReference.child(ds.getKey()).child("outTime").setValue(date.toString());
                                }
break;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mainreference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.getKey().equals(scannedString[2])){
                                Sensor sensor = ds.getValue(Sensor.class);
                            if (sensor.getStatus().equals("yes") && sensor.getBooked().equals("no")) {
                                mainreference.child(scannedString[2]).child("booked").setValue("yes");
                            } else if (sensor.getStatus().equals("yes") && sensor.getBooked().equals("yes")) {
                                mainreference.child(scannedString[2]).child("booked").setValue("no");
                                mainreference.child(scannedString[2]).child("status").setValue("no");
                            }
                           
                            break;
                        }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        }
        else {


            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
