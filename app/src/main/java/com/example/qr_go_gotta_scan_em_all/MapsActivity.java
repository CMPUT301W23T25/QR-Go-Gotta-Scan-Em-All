package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *
 * The MapsActivity class displays a Google Map and allows the user to interact
 * with it.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView back_btn;
    private GoogleMap mMap;
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
        back_btn = findViewById(R.id.maps_back_btn);


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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


    }
}