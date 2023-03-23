package com.example.qr_go_gotta_scan_em_all;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

/**
 *
 * {@link MainActivity} is the main activity for the Pokemon app. It handles the
 * navigation bar, pokeball, and launching
 * other activities to scan QR codes, add new Pokemon, view the player's Pokemon
 * and leaderboards, and view the map.
 * The activity contains two ActivityResultLaunchers, one for scanning QR codes
 * and one for adding new Pokemon. It also
 * initializes the database and retrieves the player object from the previous
 * activity.
 * The handleNavBar method listens for clicks on the navigation bar and launches
 * the appropriate fragment or activity
 * based on the clicked item. The checkIfPokemonAdded method checks if a new
 * Pokemon was added and adds it to the player's
 * array of Pokemon if it was.
 * This class is part of the Pokemon app, which is a mobile game that allows
 * users to catch Pokemon by scanning QR codes,
 * view their collection of Pokemon, view leaderboards, and view a map of their
 * location and nearby Pokemon.
 */
public class MainActivity extends AppCompatActivity {
    // https://github.com/hamidsaid/Modern-Bottom-Navigation/tree/main/app/src
    private BottomNavigationView btmNavView;
    private FloatingActionButton pokeBall;
    private String qrResult;

    private Player player;

    private Intent switchLoginIntent;
    private FragmentManager fragmentManager;
    private Database db;
    private boolean cameraPermissionGranted = false;
    private boolean locationPermissionGranted = false;



    /**
     * ActivityResultLauncher used for starting the QR scanner
     */
    ActivityResultLauncher<Intent> startQrScanner = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            String pokemonCaught;
            if (result != null && result.getResultCode() == RESULT_OK) {
                pokemonCaught = result.getData().getStringExtra("PokemonCaught");
                Intent switchToPokemonAdd = new Intent(MainActivity.this, PokemonAddActivity.class);
                switchToPokemonAdd.putExtra("PokemonCaught", pokemonCaught);
                startPokemonAdd.launch(switchToPokemonAdd);
                }
            }

    });
    ActivityResultLauncher<Intent> startPokemonAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Pokemon pokemonAdded;
            if (result.getData() != null && result.getResultCode() == RESULT_OK) {
                pokemonAdded = (Pokemon) result.getData().getSerializableExtra("pokemon");
                player.addPokemon(pokemonAdded);
                goToOverview();

            }
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchLoginIntent = new Intent(MainActivity.this, LoginActivity.class);

        // initialize the database
        db = new Database(this);
        player = (Player) getIntent().getSerializableExtra("player");

        if (player == null) {
            System.out.println("BAD PLAYER");
        }

        btmNavView = findViewById(R.id.btmNavView);
        pokeBall = findViewById(R.id.poke_ball);
        fragmentManager = getSupportFragmentManager();
        goToOverview();
        handleNavBar();
        handlePokeBall();

        player.addPokemon(new Pokemon("test1"));


    }

    @Override
    public void onResume(){
        super.onResume();


    }

    private void handleNavBar() {

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
                        goToLeaderboard();
                        break;
                    case R.id.map:
                        // Do something for menu item 3
                        locationPermissionGranted = checkLocationPermission();
                        goToMap();
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

    private void checkIfPokemonAdded(){
        System.out.println(getIntent());
        Serializable pkRaw = getIntent().getSerializableExtra("pokemon");
        System.out.println(pkRaw);
        if (pkRaw != null){
            Pokemon pk = (Pokemon)pkRaw;
            player.addPokemon(pk);
            System.out.println("NICEEEEE");
        }

    }

    private void handlePokeBall() {
        pokeBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                // For example, you can show a Toast message:
                cameraPermissionGranted = checkCameraPermission();
                goToQrScanner();
            }
        });
    }


    private void goToOverview() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, new OverviewFragment(player), null);
        transaction.commit();
    }

    private void goToLeaderboard() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, new LeaderboardFragment(), null);
        transaction.commit();
    }

    private void goToPerson() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, new ProfilePageFragment(player), null);
        transaction.commit();
    }

    private void goToQrScanner() {

        if (cameraPermissionGranted) {
            Intent switchScannerIntent = new Intent(MainActivity.this, QrScannerActivity.class);
            startQrScanner.launch(switchScannerIntent);
        } else {
            Toast.makeText(this, "Please grant camera permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMap() {

        if (locationPermissionGranted) {
            Intent switchMapsIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(switchMapsIntent);
            //add other things
        } else {
            Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
/*
    private boolean checkNotRegistered(){
        // Implement based on if it is decided to use the text file, or the phone ID
        isUserRegisteredQuery();
        return isRegistered;
    }
*/

/*
    private void isUserRegisteredQuery(){
        db.getPlayerCol().document(new PlayerIDGenerator(this).getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document exists
                                isRegistered = true;
                                System.out.println("registed alrady");
                            } else {
                                // Document doesn't exist
                                isRegistered = false;
                                System.out.println("registed not");
                            }
                        } else {
                            // Error getting document
                            Log.d(TAG, "get failed with ", task.getException());
                            // go to network not found activity
                            switchToNetworkFail();
                        }
                    }
                });
    }
*/

    private void switchToNetworkFail() {
        startActivity(new Intent(MainActivity.this, ConnectionErrorActivity.class));
        finish();
    }


//    public String getMyData() {
//        return qrResult;
//    }
}

