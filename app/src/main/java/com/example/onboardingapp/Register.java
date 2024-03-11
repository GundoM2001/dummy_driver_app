package com.example.onboardingapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    Button goToLoginBtn, registerBtn;
    TextInputEditText registerFirstName,registerLastName,registerCellNo,registerEmail, registerPassword, registerConfirmPassword;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    String userId;


    ProgressBar regProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        registerFirstName = findViewById(R.id.register_firstName);
        registerLastName = findViewById(R.id.register_lastName);
        registerCellNo = findViewById(R.id.register_phoneNo);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerConfirmPassword = findViewById(R.id.confirm_password);
        goToLoginBtn = findViewById(R.id.goToLogin_btn);
        registerBtn = findViewById(R.id.register_btn);
        regProgressBar = findViewById(R.id.reg_progeressBar);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regProgressBar.setVisibility(View.VISIBLE);
                String firstName,lastName,cellNo,email,password,confirmPassword;

                firstName = String.valueOf(registerFirstName.getText());
                lastName = String.valueOf(registerLastName.getText());
                cellNo = String.valueOf(registerCellNo.getText());
                email = String.valueOf(registerEmail.getText());
                password = String.valueOf(registerPassword.getText());
                confirmPassword = String.valueOf(registerConfirmPassword.getText());

                if (!isEmailValid(email)){
                    Toast.makeText(Register.this, "Please enter a valid email address",Toast.LENGTH_LONG).show();
                    regProgressBar.setVisibility(View.GONE);
                    return;
                }

                if(!isValidPassword(password)){
                    Toast.makeText(Register.this,"Invalid password",Toast.LENGTH_LONG).show();
                    regProgressBar.setVisibility(View.GONE);
                }


                if(TextUtils.isEmpty(email)){

                    Toast.makeText(Register.this,"Please enter your email address",Toast.LENGTH_SHORT).show();
                    regProgressBar.setVisibility(View.GONE);
                    return;

                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(Register.this,"Please choose your password",Toast.LENGTH_SHORT).show();
                    regProgressBar.setVisibility(View.GONE);
                    return;

                }
                if(TextUtils.isEmpty(confirmPassword)){

                    Toast.makeText(Register.this,"Please confirm your password",Toast.LENGTH_SHORT).show();
                    regProgressBar.setVisibility(View.GONE);
                    return;

                }
                if(!password.equals(confirmPassword)){
                    Toast.makeText(Register.this,"The two passwords do not match", Toast.LENGTH_SHORT).show();
                    regProgressBar.setVisibility(View.GONE);
                }
//                if(registerCellNo.length() != 10){
//                    Toast.makeText(Register.this,"Please enter a valid 10-digit SA cell number ", Toast.LENGTH_SHORT).show();
//                    regProgressBar.setVisibility(View.GONE);
//                }


                //registers the user in Firebase
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Send verification link
                                    FirebaseUser fuser = auth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),"Please click the link on the verification email",Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"Error: "+e.getMessage());
                                        }
                                    });

                                    Log.d(TAG, "createUserWithEmail:success");
                                    regProgressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Register.this, "Registration Complete",
                                            Toast.LENGTH_SHORT).show();
                                    userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                    DocumentReference documentReference = fstore.document("users/" + userId);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("firstName",firstName);
                                    user.put("lastName",lastName);
                                    user.put("cellNo",cellNo);
                                    user.put("email",email);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Intent i = new Intent(getApplicationContext(), Dashboard.class);

                                            startActivity(i);

                                            Log.d(TAG,"Your Account has been created"+userId);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("TAG_1","Error adding document", e);
                                        }
                                    });

                                } else {
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                    regProgressBar.setVisibility(View.GONE);


                                }
                            }
                        });



            }
        });

        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);

                startActivity(i);
            }
        });
    }

    private boolean isEmailValid(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    public static boolean validateSaPhoneNumber(String number){
        //Clears whitespace and special characters
        String cleanedNumber = number.replaceAll("\\s+|-","");

        Pattern pattern1 = Pattern.compile("^0\\d{9}$");
        Pattern pattern2 = Pattern.compile("^\\+27\\d{9}$");
        Pattern pattern3 = Pattern.compile("^27\\d{9}$");

        Matcher matcher1 = pattern1.matcher(cleanedNumber);
        Matcher matcher2 = pattern2.matcher(cleanedNumber);
        Matcher matcher3 = pattern3.matcher(cleanedNumber);

        return matcher1.matches()||matcher2.matches()||matcher3.matches();
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
    }
}