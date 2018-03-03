package com.example.divyang.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by divya on 21-01-2018.
 */

public class loginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button loginButton,signup1;
    private EditText username1,password1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(loginActivity.this, NavMain.class));
            finish();
        }
        setContentView(R.layout.login_layout);
        username1 = (EditText)findViewById(R.id.username1);
        password1 = (EditText)findViewById(R.id.password1);
        loginButton = (Button)findViewById(R.id.loginButton);
        signup1 = (Button)findViewById(R.id.signup1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user;
             final   String pass;
                user = username1.getText().toString();
                pass = password1.getText().toString();

                    if (TextUtils.isEmpty(user)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    auth.signInWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (pass.length() < 6) {
                                        //   pass.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(loginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(loginActivity.this, NavMain.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });



            }
        });


















    }
}
