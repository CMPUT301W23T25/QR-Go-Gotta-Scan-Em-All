package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Database {
    FirebaseFirestore fireStore;
    DatabaseReference dbRef;

    DatabaseReference playersRef;

    DatabaseReference pokemonsRef;
    /**
     * Initialize the Database
     */
    public Database(Context context){
        // Start the database for this Activity
        FirebaseApp.initializeApp(context);
        this.fireStore = FirebaseFirestore.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference();
        playersRef = dbRef.child("Players");
        pokemonsRef = dbRef.child("Pokemons");

        // ASK: Will we ever have a scenario where the collections won't exist and
        // we will need to create them?
/*        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Players collection does not exist, create it
                    playersRef.setValue(new HashMap<String, Object>());
                } else {
                    // Players collection exists, assign it to a variable
                    Iterable<DataSnapshot> playerSnapshots = dataSnapshot.getChildren();
                    List<Player> players = new ArrayList<>();
                    for (DataSnapshot playerSnapshot : playerSnapshots) {
                        Player player = playerSnapshot.getValue(Player.class);
                        players.add(player);
                    }
                    // Do something with the players collection
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });*/
    }

    public void addPlayer(Player player){
        // Add the player to the database
        // NOTE: A player object that has an ID and username must be passed into the database
        String ID = player.getUserId();
        HashMap<String, Object> playerMap = new HashMap<>();
        playerMap.put("username",player.getUserName());
        playerMap.put("pokemon_owned",new ArrayList<String>());

    }

    public Player loadData(String ID){
        // gets the specific player from the database and puts it into Player object
        // returns the player object
        Player p = new Player();

        // load player data from database
        return p;
    }

    public void savePlayerData(Player player){
        // save the player data to server
        // get ID of player
        String ID = player.getUserId();
        // get the IDs of the QR codes that the player has in the Database
        // put all those IDs into a String arraylist

    }

    public void addPokemon(Pokemon pokemon){
        // Add a pokemon to the database

        // generate a random ID
        String ID = UUID.randomUUID().toString();

    }

    public void getPokemon(String ID){
        // Add a pokemon to the database
    }

    public boolean isPokemonExistInDB(Pokemon pokemon){
        // Checks if the specific pokemon exists in the database
        // If not, then the function will return false
        return true;
    }

    public boolean isPlayerIDExist(String ID){
        // Checks if the player ID already exists in database
        return true;
    }








}
