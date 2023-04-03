package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

    private double longitude = - 113.4937;
    private double lattitude = 53.5461;
    private String cityName;
    private String countryName;

    private Database db;
    private ClusterManager<MapIconCluster> mClusterManager;
    private MapIconClusterManager mClusterManagerRenderer;

    private List<Map<String, Object>> pokemonInLocationRaw = new ArrayList<>();

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
//        mMap.setMinZoomPreference(16.0f);
//        mMap.setMaxZoomPreference(21.0f);
        mMap.setMyLocationEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true);
//        mUiSettings.setScrollGesturesEnabled(false);
        zoomOnUser();
        getAllPokemonScanned();

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
                                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                lattitude = addresses.get(0).getLatitude();
                                longitude = addresses.get(0).getLongitude();
                                cityName = addresses.get(0).getLocality();
                                countryName= addresses.get(0).getCountryName();
                            }
                        } else{
                            switchToNetworkFail();
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

    private void getAllPokemonScanned(){

        CollectionReference pokemonRef = db.getPokemonCol();

        // Retrieve all documents in the pokemon collection
        pokemonRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Iterate over each document in the collection
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the pokemon_location key for the current document
                    List<Map<String, Object>> locationArray = (List<Map<String, Object>>) document.getData().get("pokemon_locations");
                    // if the pokemon is close to the player add it on the map (do later)

                    for(Map<String, Object> m: locationArray){
                        addMapMarkers((double)m.get("longitude"), (double) m.get("latitude"));
                    }

                }
            } else {
                System.out.println("Error getting documents: " + task.getException());
            }
        });

    }

//    private void addPokemonIntoArrayList(Map<String, Object> p, List<Map<String, Object>> pIList){
//
//        for (Map<String, Object> pI:pIList){
//            if (pI.get("ID") == p.get("ID")){
//                // Check if the Pokemon is already in the list
//                return;
//            }
//        }
//
//        pIList.add(p);
//    }

    //referenced from
    //CodingWithMitch - https://youtu.be/U6Z8FkjGEb4 and https://github.com/mitchtabian/Google-Maps-2018/tree/creating-custom-google-map-markers-end
    private void addMapMarkers(double longitude, double latitude){

        if(mMap != null){
            if(mClusterManager == null){
                mClusterManager = new ClusterManager<MapIconCluster>(this, mMap);
            }
            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new MapIconClusterManager(
                        this,
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }




            String snippet = "Pokemon found here";
            int avatar = R.drawable.png_clipart_pokeball_pokeball_thumbnail_removebg_preview_1;
            MapIconCluster newClusterMarker = new MapIconCluster(
                        new LatLng(latitude, longitude), "Pokemon was found here",
                        snippet,
                        avatar
            );
            mClusterManager.addItem(newClusterMarker);
            mClusterManager.cluster();
        }
    }

    /**

     Starts the NetworkFailActivity and finishes the current activity.
     */
    private void switchToNetworkFail() {
        startActivity(new Intent(MapsActivity.this, ConnectionErrorActivity.class));
        finish();
    }






}