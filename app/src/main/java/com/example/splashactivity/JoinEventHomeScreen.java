package com.example.splashactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class JoinEventHomeScreen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event_home_screen);

        toolbar = findViewById(R.id.join_event_toolbar_id);
        drawerLayout = findViewById(R.id.drawerLayoutKiID);
        navigationView = findViewById(R.id.navKiID);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.navy_blue)); // Change to your desired color
        }


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id==R.id.allEvents){
                    loadFragment(new JoinAllEventsHomeScreen());
                    Toast.makeText(JoinEventHomeScreen.this, "All Events" , Toast.LENGTH_SHORT).show();
                } else if (id==R.id.invitedEvents) {
                    Toast.makeText(JoinEventHomeScreen.this, "Invited Events" , Toast.LENGTH_SHORT).show();
                    loadFragment(new JoinInviteEventsFragment());
                }else if (id==R.id.joinedEvents) {
                    Toast.makeText(JoinEventHomeScreen.this, "Events joined by you" , Toast.LENGTH_SHORT).show();
                    loadFragment(new JoinJoinedEventsFragment());
                } else{
                    SharedPreferences preferences = getSharedPreferences("checkBox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "false");
                    editor.apply();

                    FirebaseAuth.getInstance().signOut();
                    // Navigate back to the login screen
                    Intent intent = new Intent(JoinEventHomeScreen.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(JoinEventHomeScreen.this, "LOGGED OUT", Toast.LENGTH_SHORT).show();

                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        loadFragment(new JoinAllEventsHomeScreen());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // Replace the existing content with the new fragment
        ft.replace(R.id.JoinEventFrameLayout, fragment);
        ft.addToBackStack(null); // Add this line to handle back navigation
        ft.commit();
    }

}