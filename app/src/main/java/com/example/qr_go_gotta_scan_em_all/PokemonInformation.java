package com.example.qr_go_gotta_scan_em_all;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class PokemonInformation implements Serializable {
    private byte[] imageByteArray;
    private double locationLat;
    private double locationLong;

    private String cityName;
    private String countryName;
    private Pokemon pokemon;

    public PokemonInformation(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.imageByteArray = null;
        this.locationLat = Double.POSITIVE_INFINITY;
        this.locationLong = Double.POSITIVE_INFINITY;
        this.cityName = null;
        this.countryName = null;
    }

    public PokemonInformation(Pokemon pokemon, byte[] imageByteArray, double locationLat, double locationLong, String cityName, String countryNam) {
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
        this.locationLong = lat;
        this.locationLat = lon;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Pair<Double,Double> getPairedLocation(){
        if (Double.compare(locationLat, Double.POSITIVE_INFINITY) != 0 && Double.compare(locationLong, Double.POSITIVE_INFINITY) != 0){
            return new Pair<Double,Double>(locationLat,locationLong);
        }
        return null;
    }

    public Bitmap getDecodedImage(){
        Bitmap bmp = null;
        // https://stackoverflow.com/questions/7620401/how-to-convert-image-file-data-in-a-byte-array-to-a-bitmap
        bmp =  BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return bmp;
    }

    public String getCountryName() {
        return countryName;
    }
}
