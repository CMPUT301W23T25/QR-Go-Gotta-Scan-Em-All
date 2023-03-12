package com.example.qr_go_gotta_scan_em_all;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

    private ArrayList<Pokemon> PokemonArray;
    private String userName;
    private String userId;

    private Map<String,Integer> leaderboardStats;

    private ArrayList<Player> friends;


    public Player(String userName, String userId, ArrayList<Pokemon> PokemonArray, Map<String,Integer> leaderboardStats,ArrayList<Player> friends) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.PokemonArray = PokemonArray;
        this.leaderboardStats = leaderboardStats;
        this.friends = friends;
    }

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.PokemonArray = new ArrayList<Pokemon>();
        this.leaderboardStats = new HashMap<>();
        this.friends = new ArrayList<Player>();
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

    public ArrayList<Pokemon> getPokemonArray(){
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
