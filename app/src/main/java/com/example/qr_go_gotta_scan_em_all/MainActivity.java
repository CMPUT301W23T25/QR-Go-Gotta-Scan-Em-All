package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private String qrResult;

    Player player;

    Intent switchIntent;
    FragmentManager fragmentManager;
    LoginInfo login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchIntent = new Intent(MainActivity.this, LoginActivity.class);

        // handle the login (i.e if the user is not registered)
        if (checkNotRegistered()){
            // Go to the login activity
            switchToLoginActivity();
            // Get the user


        } else{
            player = new Player(login.getUserName(), login.getUserId());
            System.out.println(login.getUserName());
            btmNavView = findViewById(R.id.btmNavView);
            pokeBall = findViewById(R.id.poke_ball);
            fragmentManager = getSupportFragmentManager();
            goToOverview();
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
                        // Credits: Android Studio Website
                        // https://developer.android.com/reference/android/view/View.OnClickListener
                        // FragmentManager manages all the fragments within an activity
                        // beginTransaction will access the fragment manager and listen to what the
                        // transaction will be
                        // Create new fragment and transaction
                        goToOverview();
                        break;
                    case R.id.leaderboard:
                        // Do something for menu item 2
                        goToQrScanner();
                        break;
                    case R.id.map:
                        // Do something for menu item 3
                        break;
                    case R.id.person:
                        // Do something for menu item 4
                        goToPerson();
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

    private void goToOverview(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, OverviewFragment.class, null);
        transaction.commit();
    }

    private void goToPerson(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, new profilePageFragment(player), null);
        transaction.commit();
    }

    private void goToQrScanner(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, ScannerFragment.class, null);
        transaction.commit();

    }
    private boolean checkNotRegistered(){
        // Implement based on if it is decided to use the text file, or the phone ID

        if ((LoginInfo)getIntent().getSerializableExtra("loginInfo") != null){
            login = (LoginInfo)getIntent().getSerializableExtra("loginInfo");
        }

        return  (LoginInfo)getIntent().getSerializableExtra("loginInfo") == null;
    }

    public String getMyData() {
        return qrResult;
    }
}