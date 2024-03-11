package com.example.onboardingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button goToRegisterBtn, loginBtn;
    TextInputEditText loginEmail,loginPassword;
    ProgressBar loginProgressBar;
    TextView resetBtn;
    FirebaseAuth auth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_btn);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginProgressBar = findViewById(R.id.login_progeressBar);
        goToRegisterBtn = findViewById(R.id.goToRegister_btn);
        resetBtn = findViewById(R.id.reset_password_btn);
        auth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginProgressBar.setVisibility(View.VISIBLE);
                String email,password;
                //REMEMBER getText()!!!
                email = String.valueOf(loginEmail.getText());
                password = String.valueOf(loginPassword.getText());

                //Email Validation



                if(TextUtils.isEmpty(email)){

                    Toast.makeText(Login.this,"Please enter your email address",Toast.LENGTH_SHORT).show();
                    loginProgressBar.setVisibility(View.GONE);
                    return;

                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(Login.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    loginProgressBar.setVisibility(View.GONE);
                    return;

                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {



                                    Toast.makeText(Login.this, "Succeeded.",
                                            Toast.LENGTH_SHORT).show();
                                    loginProgressBar.setVisibility(View.GONE);

                                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(i);
                                    finish();
                                    // Sign in success, update UI with the signed-in user's information
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i2 = new Intent(getApplicationContext(), Login.class);
                                    loginProgressBar.setVisibility(View.GONE);
                                    startActivity(i2);
                                    finish();
                                }
                            }
                        });
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email address to receive the reset link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Reset password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email from the field
                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Link sent to your email",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error! Reset link not sent " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("Never mind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Closes the dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);

                startActivity(i);
            }
        });
    }

}