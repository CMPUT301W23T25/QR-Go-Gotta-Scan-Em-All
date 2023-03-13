package com.example.qr_go_gotta_scan_em_all;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

    private ArrayList<Pokemon> pokemonArray;
    private String userName;
    private String userId;

    // example leaderboardStats:
    // {
    //      "username": "User1",
    //      "totalScore": "10000",
    // }
    private Map<String, String> leaderboardStats;

    private ArrayList<Player> friends;

/*    private ArrayList<Map<Pokemon,Pair<Object,Object>>>pokemonImageLoc;*/

    private String emailAddress;

    public Player(ArrayList<Pokemon> pokemonArray, String userName, String userId, Map<String, String> leaderboardStats, ArrayList<Player> friends, String emailAddress) {
        this.pokemonArray = pokemonArray;
        this.userName = userName;
        this.userId = userId;
        this.leaderboardStats = leaderboardStats;
        this.friends = friends;
        this.emailAddress = emailAddress;
    }

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.pokemonArray = new ArrayList<Pokemon>();
        this.leaderboardStats = new HashMap<>();
        this.friends = new ArrayList<Player>();
/*        this.pokemonImageLoc = new ArrayList<Map<Pokemon,Pair<Object,Object>>>();*/
    }


    public Player() {
        this.userId = null;
        this.userName = null;
        this.pokemonArray = new ArrayList<Pokemon>();
        this.friends = null;
/*        this.pokemonImageLoc = null;*/
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Pokemon> getPokemonArray(){
        return new ArrayList<>(this.pokemonArray);
    }

    public void setPokemonArray(ArrayList<Pokemon> pokemonArray) {
        this.pokemonArray = pokemonArray;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addPokemonToArray(Pokemon pokemon){
        this.pokemonArray.add(pokemon);
    }

    public Map<String, String> getLeaderboardStats() {
        return leaderboardStats;
    }
}
