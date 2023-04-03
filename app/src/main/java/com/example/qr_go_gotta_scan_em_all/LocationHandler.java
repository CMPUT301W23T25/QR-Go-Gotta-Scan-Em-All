package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.util.Pair;

public class LocationHandler implements LocationListener {

    private final Context mContext;
    private LocationManager locationManager;
    private Location currentLocation;

    public LocationHandler(Context context) {
        mContext = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, handle it here
            return null;
        }

        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currentLocation == null) {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return currentLocation;
    }

    public Pair<String,String> getCityAndCountry(Location location) {

        String city = "";
        String country = "";
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getLocality() != null) {
                    city = address.getLocality();
                }
                if (address.getCountryName() != null) {
                    country += address.getCountryName();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<String,String>(country,city);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, handle it here
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    // Other methods omitted for brevity
}