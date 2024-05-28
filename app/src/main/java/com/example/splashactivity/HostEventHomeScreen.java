package com.example.splashactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HostEventHomeScreen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_host_event_home_screen);

        FragmentManager fragmentManager = getSupportFragmentManager();



        Button btnlive = findViewById(R.id.btnLive);
        btnlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,liveEventsFragment.class ,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        Button btnpast = findViewById(R.id.btnPast);
        btnpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,pastEventsFragment.class ,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        Button btnUpcoming = findViewById(R.id.btnUpcoming);
        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,upcomingEventsFragment.class ,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolbar);
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HostEventHomeScreen.this, drawerLayout, materialToolbar,R.string.drawer_close ,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.search){

                    Toast.makeText(HostEventHomeScreen.this, "search", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                if (id == R.id.eventsNavMenu) {
                    // No need to do anything, stay on the same screen
                } else if (id == R.id.participantsNavMenu) {
                    // Redirect to HostParticipantsNavMenu activity
                    startActivity(new Intent(HostEventHomeScreen.this, ParticipantsNavMenuForHost.class));
                } else if (id == R.id.inviteNavMenu) {
                    // Redirect to HostInviteNavMenu activity
                    startActivity(new Intent(HostEventHomeScreen.this, InviteNavMenuForHost.class));
                } else if (id == R.id.joinAEventNavMenu) {
                    // Redirect to ChoiceScreen activity
                    startActivity(new Intent(HostEventHomeScreen.this, ChoiceScreen.class));
                } else if (id == R.id.logoutNavMenu) {
                    // Sign out and redirect to the login screen
                    SharedPreferences preferences = getSharedPreferences("checkBox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "false");
                    editor.apply();


                    Intent intent = new Intent(HostEventHomeScreen.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(HostEventHomeScreen.this, "LOGGED OUT", Toast.LENGTH_SHORT).show();
                    finish();
                }
                // Close the drawer after handling the click
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
}



