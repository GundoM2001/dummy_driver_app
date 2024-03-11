package com.example.onboardingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity {

    Button verifyBtn;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        verifyBtn = findViewById(R.id.verification_btn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isEmailVerified()){
                    Toast.makeText(getApplicationContext(), "Email Verification successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(VerifyEmail.this, Dashboard.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Email is still unverified",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}