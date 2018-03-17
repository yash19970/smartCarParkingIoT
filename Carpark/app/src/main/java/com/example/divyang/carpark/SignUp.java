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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Button  signupbutton;
    private EditText confirmpassword1,password1,emails,username;
    private DatabaseReference reference;
    private Boolean active_booking,active_parking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        auth = FirebaseAuth.getInstance();
        signupbutton = (Button) findViewById(R.id.signupbutton);
        emails = (EditText) findViewById(R.id.emails);
        username = (EditText)findViewById(R.id.username);
        password1 = (EditText) findViewById(R.id.password1);
        confirmpassword1 = (EditText) findViewById(R.id.confirmpassword1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String checkemail = emails.getText().toString().trim();
                String password = password1.getText().toString().trim();
                String confrimpassword = confirmpassword1.getText().toString().trim();
                final String checkUsername = username.getText().toString().trim();
                if (TextUtils.isEmpty(checkemail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confrimpassword)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confrimpassword.equals(password)) {
                    Toast.makeText(getApplicationContext(), "password doesnt match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(checkemail, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    reference = FirebaseDatabase.getInstance().getReference("User");
                                    String userId = reference.push().getKey();
                                    User user = new User(auth.getCurrentUser().getUid(),checkUsername,checkemail,false,false);

                                    reference.child(auth.getCurrentUser().getUid()).setValue(user);
                                    startActivity(new Intent(SignUp.this, loginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
