package com.example.divyang.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by divya on 21-01-2018.
 */

public class loginActivity extends AppCompatActivity {

    private Button loginButton,signup1;
    private EditText username1,password1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        username1 = (EditText)findViewById(R.id.username1);
        password1 = (EditText)findViewById(R.id.password1);
        loginButton = (Button)findViewById(R.id.loginButton);
        signup1 = (Button)findViewById(R.id.signup1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user;
                String pass;
                user = username1.getText().toString();
                pass = password1.getText().toString();

                if (user != "" && pass != "") {
                    //    UserDetails obj = new UserDetails();
                    //  obj = db.checkLogin(user, pass);

                    Intent i = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(i);
                    //    if (obj == null) {


                    //     Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                    //      new GetDataTask().execute();


                    //   } else {
                    //     Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    //     Intent i = new Intent(MainActivity.this, Home.class);
                    //     i.putExtra("key", obj);
                    //    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

                }


            }
        });


















    }
}
