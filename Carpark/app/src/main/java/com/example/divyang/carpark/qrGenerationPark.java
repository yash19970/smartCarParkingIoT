package com.example.divyang.carpark;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class qrGenerationPark extends AppCompatActivity {

    ImageView qrcode;
    private DatabaseReference reference,mainreference;
    String locationName;
    private FirebaseAuth auth;
    TextView locations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_park);
        qrcode = (ImageView) findViewById(R.id.qrcodepark);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Intent i = getIntent();
        locationName = i.getStringExtra("locationName");
        final String[] scannedString = locationName.split("/");
        reference = FirebaseDatabase.getInstance().getReference().child("Location").child(scannedString[0]);
        mainreference = FirebaseDatabase.getInstance().getReference().child("User");
        locations = (TextView) findViewById(R.id.locations);














        reference.child("Sensor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Sensor sensor = ds.getValue(Sensor.class);
                    if(ds.getKey().equals(scannedString[1]) ){
                        Long tsLong = System.currentTimeMillis() / 1000;
                        String ts = tsLong.toString();
                        reference.child("Sensor").child(ds.getKey()).child("status").setValue("yes");
                        locations.setText("Location: "+scannedString[0]+"\n\n"+"Slot no: "+ds.getKey());
                        reference.child("TotalSlots").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String slots = dataSnapshot.getValue().toString();
                                int slot = Integer.parseInt(slots);
                                slot--;
                                reference.child("TotalSlots").setValue(String.valueOf(slot));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        mainreference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot ds : dataSnapshot.getChildren())
                                {
                                    if(ds.getKey().equals(auth.getCurrentUser().getUid())){
                                        mainreference.child(ds.getKey()).child("active_booking").setValue(true);
                                        break;
                                    }

                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        loadQrCode(qrcode, currentFirebaseUser.getUid()+"/"+scannedString[0]+"/"+ds.getKey());
                        Toast.makeText(getApplicationContext(), "booked", Toast.LENGTH_SHORT).show();
                        break;

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void loadQrCode(ImageView qrcode, String randomstring) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(randomstring, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

























}
