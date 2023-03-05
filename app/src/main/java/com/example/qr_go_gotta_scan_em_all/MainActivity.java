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
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    // https://github.com/hamidsaid/Modern-Bottom-Navigation/tree/main/app/src
    private BottomNavigationView btmNavView;
    private FloatingActionButton pokeBall;


    Player player;

    Intent switchIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchIntent = new Intent(MainActivity.this, LoginActivity.class);

        // handle the login (i.e if the user is not registered)
        if (!CheckIfUserRegistered()){
            // Go to the login activity
            switchToLoginActivity();
            // Get the user
            LoginInfo login = (LoginInfo)getIntent().getSerializableExtra("loginInfo");

            if (login != null){
                player = new Player(login.getUserName(), login.getUserId());
            }

        } else{
            btmNavView = findViewById(R.id.btmNavView);
            pokeBall = findViewById(R.id.poke_ball);
            handleNavBar();
        }
    }

    private void handleNavBar(){

        btmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

        startActivity(switchIntent);
        finish();
    }

    private boolean CheckIfUserRegistered(){
        // Implement based on if it is decided to use the text file, or the phone ID
        System.out.println(((LoginInfo)getIntent().getSerializableExtra("loginInfo")));
        return  (LoginInfo)getIntent().getSerializableExtra("loginInfo") != null;
    }
}