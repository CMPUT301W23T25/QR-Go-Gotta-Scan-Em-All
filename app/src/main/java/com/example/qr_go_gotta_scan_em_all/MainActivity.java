package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    // https://github.com/hamidsaid/Modern-Bottom-Navigation/tree/main/app/src
    private BottomNavigationView btmNavView;
    private FloatingActionButton pokeBall;
    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switchToLoginActivity();

        setContentView(R.layout.activity_main);

        handleLogin();

        btmNavView = findViewById(R.id.btmNavView);
        pokeBall = findViewById(R.id.poke_ball);

        handleNavBar();


    }

    private void handleLogin(){
        // if not logged in, disable the back button

        if (!loggedIn){
            // Go to the login activity
            switchToLoginActivity();

        }else{

        }
    }

    
    private void handleNavBar(){

        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle the menu item click here
                switch (item.getItemId()) {
                    case R.id.home:
                        // Do something for menu item 1
                        break;
                    case R.id.leaderboard:
                        // Do something for menu item 2
                        break;
                    case R.id.map:
                        // Do something for menu item 3
                        break;
                    case R.id.person:
                        // Do something for menu item 4
                        break;
                }

                // Return true to indicate that the item click has been handled
                return true;
            }
        });
    }

    private void switchToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}