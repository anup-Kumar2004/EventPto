package com.example.splashactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ParticipantsNavMenuForHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_participants_nav_menu_for_host);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Navigate back to HostEventHomeScreen
        startActivity(new Intent(this, HostEventHomeScreen.class));
        finish(); // Finish this activity to prevent it from appearing on back press again
    }

}