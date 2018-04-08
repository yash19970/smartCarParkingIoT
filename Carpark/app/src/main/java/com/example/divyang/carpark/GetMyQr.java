package com.example.divyang.carpark;

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

/**
 * Created by divya on 08-04-2018.
 */

public class GetMyQr extends AppCompatActivity {


    ImageView qrcode;
    private DatabaseReference reference,mainreference;
    String locationName;
    private FirebaseAuth auth;
    TextView locations;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_my_qr);
        qrcode = (ImageView) findViewById(R.id.getmyqr);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        mainreference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        locations = (TextView) findViewById(R.id.locations);

        mainreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(),user.getUsername()+user.getSelectedLocation(),Toast.LENGTH_LONG).show();
                if(!user.getActive_booking()){
                    locations.setText("SORRY NO ACTIVE QR AVAILABLE");
                }
                else{
                    loadQrCode(qrcode,uid+"/"+user.getSelectedLocation()+"/"+user.getAllocatedslot());
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
