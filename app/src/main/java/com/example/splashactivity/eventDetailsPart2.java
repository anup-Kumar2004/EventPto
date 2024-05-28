package com.example.splashactivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class eventDetailsPart2 extends AppCompatActivity {

    TextView dateformat ;
    TextView textView44443;
    TextView timeformatID2;
    TextView timeformat ;

    int year;
    int month;
    int day;
    int hour;
    int minute;

    DatabaseReference eventDetailsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_details_part2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Change the alpha value to make it slightly transparent, for example, 80% opacity
            int statusBarColor = ContextCompat.getColor(this, R.color.orange2);
            statusBarColor = adjustAlpha(statusBarColor, 0.8f); // 80% opacity
            window.setStatusBarColor(statusBarColor); // Apply the adjusted color
        }

        dateformat = findViewById(R.id.dateformatID);
        textView44443 = findViewById(R.id.textView44443);
        timeformatID2 = findViewById(R.id.timeformatID2);
        timeformat = findViewById(R.id.timeformatID);

        eventDetailsRef = FirebaseDatabase.getInstance().getReference().child("eventDetailsForHost");


        Calendar calendar = Calendar.getInstance();

        // Set OnClickListener for dateformat TextView
        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(eventDetailsPart2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use 'view' parameter to get the selected date
                        calendar.set(year, month, dayOfMonth);
                        dateformat.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });




        // Set OnClickListener for timeformat TextView
        timeformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(eventDetailsPart2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        timeformat.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }

                }, hour, minute, true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });

        textView44443.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(eventDetailsPart2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Use 'view' parameter to get the selected date
                        calendar.set(year, month, dayOfMonth);
                        textView44443.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        // Set OnClickListener for timeformatID2 TextView
        timeformatID2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(eventDetailsPart2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        timeformatID2.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }

                }, hour, minute, true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });
    }

    public void process2(View view) {
        String fromDate = dateformat.getText().toString();
        String fromTime = timeformat.getText().toString();
        String toDate = textView44443.getText().toString();
        String toTime = timeformatID2.getText().toString();

        // Get the eventName passed from the previous activity
        String eventName = getIntent().getStringExtra("eventName");

        // Check if eventName is null or empty
        if (eventName == null || eventName.isEmpty()) {
            // Handle the case where eventName is not available
            Toast.makeText(this, "Event name not available.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Push data to Firebase under the eventName node
        DatabaseReference eventRef = eventDetailsRef.child(eventName);
        eventRef.child("fromDate").setValue(fromDate);
        eventRef.child("fromTime").setValue(fromTime);
        eventRef.child("toDate").setValue(toDate);
        eventRef.child("toTime").setValue(toTime)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show a Toast message to confirm data insertion
                        Toast.makeText(eventDetailsPart2.this, "Date and time details saved to Firebase for " + eventName, Toast.LENGTH_SHORT).show();

                        // Start Part3 activity
                        Intent intent = new Intent(eventDetailsPart2.this, HostEventHomeScreen.class);
                        startActivity(intent);

                        // Finish the current activity
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure
                        Toast.makeText(eventDetailsPart2.this, "Failed to save date and time details", Toast.LENGTH_SHORT).show();
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
