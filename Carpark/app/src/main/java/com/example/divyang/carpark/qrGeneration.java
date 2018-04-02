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

/**
 * Created by divya on 03-03-2018.
 */

public class qrGeneration extends AppCompatActivity {
    ImageView qrcode;
    private DatabaseReference reference,mainreference;
    String locationName;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        auth = FirebaseAuth.getInstance();
       final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Intent i = getIntent();
        locationName = i.getStringExtra("locationName");
        reference = FirebaseDatabase.getInstance().getReference().child("Location").child(locationName);
        mainreference = FirebaseDatabase.getInstance().getReference().child("User");

        reference.child("Sensor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Sensor sensor = ds.getValue(Sensor.class);
                    if(sensor.getBooked().equals("no") && sensor.getStatus().equals("no")){
                         Long tsLong = System.currentTimeMillis() / 1000;
                        String ts = tsLong.toString();
                        reference.child("Sensor").child(ds.getKey()).child("status").setValue("yes");
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

                setAlarm(60,locationName,ds.getKey());
                loadQrCode(qrcode, currentFirebaseUser.getUid()+"/"+locationName+"/"+ds.getKey());
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
    private void loadQrCode(ImageView qrcode, String randomstring) {
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
    private void setAlarm(int i,String locationName,String key) {
        AlarmManager manager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(qrGeneration.this,MyAlarm.class);
        String[] str =  new String[2];
        str[0] = locationName;
        str[1]= key;
        Bundle bundle = new Bundle();
        bundle.putStringArray("data",str);
        intent.putExtra("abc",bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(qrGeneration.this,0,intent,0);
        manager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()
                + (i * 1000),pendingIntent);

    }
}

