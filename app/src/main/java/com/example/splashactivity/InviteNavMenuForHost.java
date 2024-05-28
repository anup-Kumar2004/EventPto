package com.example.splashactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InviteNavMenuForHost extends AppCompatActivity {

    EditText m1;

    Spinner spinner;
    ArrayList<String> spinnerList;
    DatabaseReference spinnerRef;
    ArrayAdapter<String> adapterSpinner;

    Button b;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invite_nav_menu_for_host);

        mAuth = FirebaseAuth.getInstance();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Change the alpha value to make it slightly transparent, for example, 80% opacity
            int statusBarColor = ContextCompat.getColor(this, R.color.orange2);
            statusBarColor = adjustAlpha(statusBarColor, 0.8f); // 80% opacity
            window.setStatusBarColor(statusBarColor); // Apply the adjusted color
        }

        b = findViewById(R.id.inviteKroButton);
        spinner = findViewById(R.id.spinnerId);
        spinnerRef = FirebaseDatabase.getInstance().getReference("eventDetailsForHost");

        spinnerList = new ArrayList<>();
        adapterSpinner = new ArrayAdapter<>(InviteNavMenuForHost.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });

        spinner.setAdapter(adapterSpinner);
        showData();

    }

    private void showData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User not logged in, handle accordingly
            return;
        }
        String userEmail = currentUser.getEmail();

        spinnerRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    // Assuming "eventName" is the child node for event name
                    String eventName = eventSnapshot.child("eventName").getValue(String.class);
                    if (eventName != null) {
                        spinnerList.add(eventName);
                    }
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation
            }
        });
    }


    public void process(){
        m1 = findViewById(R.id.emailForInviteScreen);

        String emailOfInvitees = m1.getText().toString().trim();

        String selectedEvent = spinner.getSelectedItem().toString();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User not logged in, handle accordingly
            return;
        }
        String uid = currentUser.getUid();
        String emailOfHost = currentUser.getEmail();
        String encodedEmailOfInvitees = encodeEmail(emailOfInvitees);

        // Get reference to the node with the user's UID
        DatabaseReference node = FirebaseDatabase.getInstance().getReference("HostNeJinkoInviteKraHaiUnkaData").child(encodedEmailOfInvitees).child(selectedEvent);

        // Set the values directly under the UID node without using push()

        node.child("emailOfInvitees").setValue(emailOfInvitees);
        node.child("eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData").setValue(selectedEvent);
        node.child("uid").setValue(uid);
        node.child("emailOfHost").setValue(emailOfHost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Clear the EditText field
                        m1.setText("");

                        // Display a toast message
                        Toast.makeText(getApplicationContext(), "Invite Sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure
                        Toast.makeText(getApplicationContext(), "Failed to invite", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private String encodeEmail(String email) {
        return email.replace(".", ",");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Navigate back to HostEventHomeScreen
        startActivity(new Intent(this, HostEventHomeScreen.class));
        finish(); // Finish this activity to prevent it from appearing on back press again
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(android.graphics.Color.alpha(color) * factor);
        int red = android.graphics.Color.red(color);
        int green = android.graphics.Color.green(color);
        int blue = android.graphics.Color.blue(color);
        return android.graphics.Color.argb(alpha, red, green, blue);
    }

}