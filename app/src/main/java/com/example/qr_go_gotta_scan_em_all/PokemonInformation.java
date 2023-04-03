package com.example.qr_go_gotta_scan_em_all;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.core.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
/**
 PokemonInformation class is a Serializable class that stores information about a specific Pokemon,
 including its image, location (latitude and longitude), city name, country name, and Pokemon object.
 */
public class PokemonInformation implements Serializable {
    private byte[] imageByteArray;
    private double locationLat;
    private double locationLong;

    private String cityName;
    private String countryName;
    private Pokemon pokemon;

    /**
     * Constructor that initializes the PokemonInformation object with a Pokemon instance.
     *
     * @param pokemon The Pokemon object.
     */
    public PokemonInformation(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.imageByteArray = null;
        this.locationLat = Double.POSITIVE_INFINITY;
        this.locationLong = Double.POSITIVE_INFINITY;
        this.cityName = null;
        this.countryName = null;
    }

    /**
     * Constructor that initializes the PokemonInformation object with a Pokemon instance and additional information.
     *
     * @param pokemon       The Pokemon object.
     * @param imageByteArray The byte array of the Pokemon image.
     * @param locationLat    The latitude of the Pokemon's location.
     * @param locationLong   The longitude of the Pokemon's location.
     * @param cityName       The name of the city where the Pokemon is located.
     * @param countryName    The name of the country where the Pokemon is located.
     */
    public PokemonInformation(Pokemon pokemon, byte[] imageByteArray, double locationLat, double locationLong, String cityName, String countryName) {
        this.imageByteArray = imageByteArray;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.cityName = cityName;
        this.pokemon = pokemon;
        this.countryName = countryName;
    }

    /**
     * @return The byte of the Pokemon location image.
     */
    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    /**
     * Returns the LocationLat of the Pokemon.
     * @return The LocationLat of the Pokemon.
     */
    public double getLocationLat() {
        return locationLat;
    }
    /**
     * Returns the LocationLong of the Pokemon.
     * @return The LocationLong of the Pokemon.
     */
    public double getLocationLong() {
        return locationLong;
    }

    public String getCityName() {
        return cityName;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }
    /**
     * Sets the image of the Pokemon to a given value.
     * @param imageByteArray The new image of the Pokemon.
     */
    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    /**
     * Sets the location of the Pokemon to a given value.
     * @param lat double of latitude
     *  @param lon double of longitude
     */
    public void setLocation(double lat, double lon) {
        this.locationLong = lon;
        this.locationLat = lat;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    /**
     * Returns a Pair object containing the latitude and longitude of the Pokemon's location.
     *
     * @return A Pair object with the Pokemon's location (latitude, longitude) if available, otherwise null.
     */
    public Pair<Double,Double> getPairedLocation(){
        if (Double.compare(locationLat, Double.POSITIVE_INFINITY) != 0 && Double.compare(locationLong, Double.POSITIVE_INFINITY) != 0){
            return new Pair<>(locationLat,locationLong);
        }
        return null;
    }

    /**
     * Returns the decoded Bitmap image of the Pokemon.
     *
     * @return The decoded Bitmap image of the Pokemon.
     */
    public Bitmap getDecodedImage(){
        Bitmap bmp = null;
        // https://stackoverflow.com/questions/7620401/how-to-convert-image-file-data-in-a-byte-array-to-a-bitmap
        bmp =  BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return bmp;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
