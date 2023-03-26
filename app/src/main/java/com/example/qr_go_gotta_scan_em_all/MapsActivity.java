package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * 
 * The MapsActivity class displays a Google Map and allows the user to interact
 * with it.
 */
public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted = false;
    private Location lastKnownLocation;

    private Double longitude;
    private Double lattitude;
    private String cityName;
    private String countryName;

    private Database db;

    /**
     * 
     * Called when the activity is starting. Sets up the activity's layout and
     * initializes the map fragment.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, then this Bundle
     *                           contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ImageView back_btn = findViewById(R.id.maps_back_btn);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        checkLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        db = new Database(this);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addLocation();
        displayPokemonOnMap();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // referenced from -
        // https://developers.google.com/maps/documentation/android-sdk/controls
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Maps Failed to start", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(MapsActivity.this);
        mMap.setOnMyLocationClickListener(MapsActivity.this);
        mMap.setMinZoomPreference(16.0f);
        mMap.setMaxZoomPreference(21.0f);
        mMap.setMyLocationEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(false);
        zoomOnUser();

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void zoomOnUser() {
        // referenced from -
        // https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()),
                                        19.0f));
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    2);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            } else {
                locationPermissionGranted = false;
            }
        } else {
            locationPermissionGranted = true;
        }
    }

    private void putMarker(double lat, double lan) {
        LatLng markLocation = new LatLng(lat, lan);
        mMap.addMarker(new MarkerOptions()
                .position(markLocation)
                // .icon(BitmapDescriptor(BitmapDescriptorFactory.))
                .title("Pokemon was found here"));

    }

    private void addLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                try {
                                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lattitude = addresses.get(0).getLatitude();
                                    longitude = addresses.get(0).getLongitude();
                                    cityName = addresses.get(0).getLocality();
                                    countryName= addresses.get(0).getCountryName();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });
        }


    }

    private List<Map<String, Object>> getNearbyPokemon(){
        CollectionReference playersRef = db.getPlayerCol();
        // Query to get the player document for the given user ID
        // Function to get all player documents in the players collection
        // Query to get all player documents
        List<Map<String, Object>> pokemonInLocation = new ArrayList<>();
        Task<QuerySnapshot> querySnapshotTask = playersRef.get();
        querySnapshotTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Get the list of player documents
                List<DocumentSnapshot> playerDocs = task.getResult().getDocuments();

                // Initialize list to hold Pokemon in the same location as the player


                // Loop through each player document
                for (DocumentSnapshot playerDoc : playerDocs) {

                    // Get the list of Pokemon owned by the current player
                    List<Map<String, Object>> pokemonList = (List<Map<String, Object>>) playerDoc.get("pokemon_owned");

                    // Loop through each Pokemon owned by the current player
                    for (Map<String, Object> pokemon : pokemonList) {

                        // Check if the Pokemon is in the same location as the player
                        if (pokemon.get("city").equals(cityName) && pokemon.get("country").equals(countryName)) {

                            // Add the Pokemon to the list of Pokemon in the same location as the player
                            addPokemonIntoArrayList(pokemon,pokemonInLocation);
                        }
                    }
                }

                // Return the list of Pokemon in the same location as the player

            } else {

            }
        });

        return pokemonInLocation; // Return null for now, as the results will be retrieved asynchronously
    }

    private void addPokemonIntoArrayList(Map<String, Object> p, List<Map<String, Object>> pIList){

        for (Map<String, Object> pI:pIList){
            if (pI.get("ID") == p.get("ID")){
                return;
            }
        }

        pIList.add(p);
    }

    private void displayPokemonOnMap(){
        List<Map<String, Object>> pokemonHashMaps = getNearbyPokemon();

        for (Map<String, Object> m:pokemonHashMaps){
            putMarker((double)m.get("lat"), (double)m.get("long"));
            System.out.println("xhuweriowrhuiowehuiowehuiweuiwehui");
            System.out.println((double)m.get("lat"));
        }
    }


}