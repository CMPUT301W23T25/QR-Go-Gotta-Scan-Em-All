package com.example.qr_go_gotta_scan_em_all;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    ArrayList<Pokemon> PokemonArray;
    private String userName;
    private String userId;

    public Player(String userName, String userId, ArrayList<Pokemon> PokemonArray) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.PokemonArray = PokemonArray;
    }

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.PokemonArray = new ArrayList<Pokemon>();
    }

    public Player() {
        this.userId = null;
        this.userName = null;
        this.PokemonArray = null;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Pokemon> getQRCode(){
        return new ArrayList<>(this.PokemonArray);
    }

    public void setPokemonArray(ArrayList<Pokemon> pokemonArray) {
        PokemonArray = pokemonArray;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
