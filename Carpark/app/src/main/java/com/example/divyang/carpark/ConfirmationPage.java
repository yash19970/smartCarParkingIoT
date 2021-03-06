package com.example.divyang.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by gauri on 04/03/18.
 */

public class ConfirmationPage extends AppCompatActivity {
    TextView location,slot;
    Button confirm;
    String[] str = new String[2];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_page);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getBundleExtra("data");

        str = bundle.getStringArray("abc");
        location = (TextView)findViewById(R.id.location);
        slot = (TextView) findViewById(R.id.slot);
        confirm = (Button)  findViewById(R.id.confirm);
        location.setText(str[0]);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ConfirmationPage.this,qrGeneration.class );
                i.putExtra("locationName",str[0]);
                startActivity(i);
                finish();

                
            }
        });


    }
}
