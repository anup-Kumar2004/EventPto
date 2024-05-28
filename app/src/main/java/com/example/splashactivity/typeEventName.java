package com.example.splashactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.splashactivity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class typeEventName extends AppCompatActivity {

    EditText t1, t2, t3;
    FloatingActionButton floatingActionButton9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_event_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Change the alpha value to make it slightly transparent, for example, 80% opacity
            int statusBarColor = ContextCompat.getColor(this, R.color.orange2);
            statusBarColor = adjustAlpha(statusBarColor, 0.8f); // 80% opacity
            window.setStatusBarColor(statusBarColor); // Apply the adjusted color
        }

        floatingActionButton9 = findViewById(R.id.floatingActionButton9);

        floatingActionButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();

            }
        });
    }

    public void process() {
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);

        String email = t1.getText().toString().trim();
        String eventName = t2.getText().toString().trim();
        String eventDetails = t3.getText().toString().trim();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser != null) {
            // Get the email of the currently logged-in user
            String currentUserEmail = currentUser.getEmail();

            // Check if the entered email matches the current user's email
            if (!email.equals(currentUserEmail)) {
                // Show toast message for 5 seconds
                Toast toast = Toast.makeText(getApplicationContext(), "Please enter your registered email", Toast.LENGTH_SHORT);
                toast.show();

                return;
            }
        } else {
            // User is not logged in
            // Handle this case according to your app's logic
            return;
        }


        // Check if eventName is empty
        if (eventName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        if (eventDetails.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }



        // Create an instance of your data class
        EventDataStoring obj1 = new EventDataStoring(email, eventName, eventDetails);

        // Get the Firebase database instance
        FirebaseDatabase db2 = FirebaseDatabase.getInstance();

        // Reference the "eventDetails" node in the database
        DatabaseReference node = db2.getReference("eventDetailsForHost");

        // Set the data using eventName as the parent node
        node.child(eventName).setValue(obj1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Clear the EditText fields
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");

                        // Display a toast message
                        Toast.makeText(getApplicationContext(), "Details inserted", Toast.LENGTH_SHORT).show();

                        // Start the new activity
                        Intent intent = new Intent(typeEventName.this, eventDetailsPart2.class);
                        intent.putExtra("eventName", eventName);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure
                        Toast.makeText(getApplicationContext(), "Failed to insert details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(android.graphics.Color.alpha(color) * factor);
        int red = android.graphics.Color.red(color);
        int green = android.graphics.Color.green(color);
        int blue = android.graphics.Color.blue(color);
        return android.graphics.Color.argb(alpha, red, green, blue);
    }
}
