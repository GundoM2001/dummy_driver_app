package com.example.onboardingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateDetails extends AppCompatActivity {
    Button backBtn,updateBtn;
    EditText updateFirstName,updateLastName, updateEmail, updateCellNo;
    ProgressBar updateProgressBar;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        updateFirstName = findViewById(R.id.update_firstName);
        updateLastName = findViewById(R.id.update_lastName);
        updateCellNo = findViewById(R.id.update_phoneNo);
        updateEmail = findViewById(R.id.update_email);
        backBtn = findViewById(R.id.update_back_btn);
        updateBtn = findViewById(R.id.update_btn);
        updateProgressBar = findViewById(R.id.update_progressBar);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateDetails.this, Profile.class));
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgressBar.setVisibility(View.VISIBLE);
                if (updateFirstName.getText().toString().isEmpty()||
                        updateLastName.getText().toString().isEmpty()||
                        updateCellNo.getText().toString().isEmpty()||
                        updateEmail.getText().toString().isEmpty()){
                    Toast.makeText(UpdateDetails.this, "Please enter data on all fields",Toast.LENGTH_SHORT).show();
                }
                String email = updateEmail.getText().toString();

                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        updateProgressBar.setVisibility(View.GONE);
                        DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", updateEmail.getText().toString());
                        edited.put("firstName", updateFirstName.getText().toString());
                        edited.put("lastName", updateLastName.getText().toString());
                        edited.put("cellNo",updateCellNo.getText().toString());
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateDetails.this, "Details update successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateDetails.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(UpdateDetails.this,"Email successfully changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateProgressBar.setVisibility(View.GONE);
                        Toast.makeText(UpdateDetails.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}