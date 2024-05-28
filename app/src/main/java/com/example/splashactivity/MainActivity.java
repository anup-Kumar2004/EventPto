package com.example.splashactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText loginEmail, loginPassword;
    private Button loginButton;

    CheckBox remember;

    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        remember = findViewById(R.id.checkBoxID);
        forgotPassword = findViewById(R.id.forgotPassword);

        SharedPreferences preferences = getSharedPreferences("checkBox" , MODE_PRIVATE);
        String checkbox  = preferences.getString("remember" , "");

        if(checkbox.equals("true")){
            Intent intent = new Intent(MainActivity.this , ChoiceScreen.class);
            startActivity(intent);

        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please Sign In" , Toast.LENGTH_SHORT).show();
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkBox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Checked" , Toast.LENGTH_SHORT).show();

                }else if(!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkBox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Unchecked" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();
                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(MainActivity.this, "Login Successfull" , Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this , ChoiceScreen.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Login Failed" , Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        loginPassword.setError("Password cannot be empty");
                    }
                }
                else if (email.isEmpty()){
                    loginEmail.setError("Email cannot be empty");
                }else{
                    loginEmail.setError("Please enter valid email");
                }
            }
        });

    }



    // Method to navigate to the SignupActivity when "Register Now" TextView is clicked
    public void navigateToSignupScreen(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


}