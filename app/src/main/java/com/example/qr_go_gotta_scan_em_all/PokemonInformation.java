package com.example.qr_go_gotta_scan_em_all;

public class PokemonInformation {
    private byte[] imageByteArray;
    private double locationLat;
    private double locationLong;
    private String cityName;
    private Pokemon pokemon;

    public PokemonInformation(byte[] imageByteArray, double locationLat, double locationLong, String cityName, Pokemon pokemon) {
        this.imageByteArray = imageByteArray;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.cityName = cityName;
        this.pokemon = pokemon;
    }

    /**
     * @return The byte of the Pokemon location image.
     */
    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public String getCityName() {
        return cityName;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
