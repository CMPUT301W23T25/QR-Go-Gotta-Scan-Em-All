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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 *
 * The MapsActivity class displays a Google Map and allows the user to interact
 * with it.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double currentLatitude;
    private double currentLongitude;
    private UiSettings mUiSettings;

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
        getCurrentLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //referenced from - https://developers.google.com/maps/documentation/android-sdk/controls
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(MapsActivity.this);
        mMap.setOnMyLocationClickListener(MapsActivity.this);
        mUiSettings.setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        postMapReady();
    }
    private double[] getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    currentLatitude = addresses.get(0).getLatitude();
                                    currentLongitude = addresses.get(0).getLongitude();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            checkLocationPermission();
        }

        return new double[]{currentLatitude, currentLongitude};
    }
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void postMapReady(){
        double[] userCoordinates = getCurrentLocation();
//        LatLng userLocation = new LatLng(userCoordinates[0],userCoordinates[1]);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        }

    private void putMarker(double lat, double lan){
        LatLng markLocation = new LatLng(lat,lan);
        mMap.addMarker(new MarkerOptions()
                .position(markLocation)
//                .icon(BitmapDescriptor(BitmapDescriptorFactory.))
                .title("pokemon"));

    }

}