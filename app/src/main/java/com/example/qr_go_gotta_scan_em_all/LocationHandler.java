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

/**
 * LocationHandler class is responsible for obtaining the device's
 * current location and providing city and country name based on
 * the location.
 */
public class LocationHandler implements LocationListener {

    private final Context mContext;
    private LocationManager locationManager;
    private Location currentLocation;

    /**
     * Constructor for the LocationHandler class.
     *
     * @param context The context in which the LocationHandler is used.
     */
    public LocationHandler(Context context) {
        mContext = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Get the current location of the device.
     *
     * @return The current Location object or null if permission is not granted.
     */
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

    /**
     * Get the city and country name based on the provided location.
     *
     * @param location The Location object containing the latitude and longitude.
     * @return A Pair<String, String> containing the country name and city name.
     */
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

    /**
     * Callback method when the device's location changes.
     *
     * @param location The updated Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    /**
     * Start requesting location updates.
     */
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, handle it here
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
    }

    /**
     * Stop requesting location updates.
     */
    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    // Other methods omitted for brevity
}