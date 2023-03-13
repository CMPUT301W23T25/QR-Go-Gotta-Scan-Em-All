package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
        /**
        * A simple {@link Database} class to create firebase database and creates objects
        * like the players info and the pokemon info to be stored in the database
        */
public class Database {
    FirebaseFirestore fireStore;
    DatabaseReference dbRef;
    DatabaseReference playersRef;
    DatabaseReference pokemonRef;

    CollectionReference playerCol;

    CollectionReference pokemonCol;


    /**
     * Constructor for the Database class.
     * Initializes Firebase and sets up database references.
     *
     * @param context The context of the current activity.
     */
    public Database(Context context){
        // Start the database for this Activity
        FirebaseApp.initializeApp(context);
        this.fireStore = FirebaseFirestore.getInstance();
        this.dbRef = FirebaseDatabase.getInstance().getReference();
        this.playersRef = dbRef.child("players");
        this.pokemonRef = dbRef.child("pokemon");
        this.playerCol = fireStore.collection("players");
        this.pokemonCol = fireStore.collection("pokemon");
    }

    /**
     * Load a specific player's data from the database and return it as a Player object.
     *
     * @param ID The ID of the player to load.
     * @return A Player object containing the loaded data.
     */
    public Player loadData(String ID){
        // gets the specific player from the database and puts it into Player object
        // returns the player object
        Player p = new Player();

        // load player data from database
        return p;
    }

    /**
     * Save a player's data to the database.
     *
     * @param player The Player object containing the data to save.
     */
    public void savePlayerData(Player player){
        // save the player data to server
        // get ID of player
        String ID = player.getUserId();
        // get the IDs of the QR codes that the player has in the Database

        // put all those IDs into a String arraylist

    }

    /**
     * Add a new pokemon to the database.
     *
     * @param pokemon The Pokemon object to add to the database.
     */
    public void addPokemon(Pokemon pokemon){
        // Add a pokemon to the database

        // generate a random ID
        String ID = UUID.randomUUID().toString();

    }
    /**
     * Load a specific pokemon's data from the database and return it as a Pokemon object.
     *
     * @param ID The ID of the pokemon to load.
     * @return A Pokemon object containing the loaded data.
     */
    public void getPokemon(String ID){
        // Add a pokemon to the database
    }

    /**
     * Some getters and setters to get and set information on the firebase database
     */

    public FirebaseFirestore getFireStore() {
        return fireStore;
    }

    public DatabaseReference getDbRef() {
        return dbRef;
    }

    public DatabaseReference getPlayersRef() {
        return playersRef;
    }

    public DatabaseReference getPokemonRef() {
        return pokemonRef;
    }

    public CollectionReference getPlayerCol() {
        return playerCol;
    }

    public CollectionReference getPokemonCol() {
        return pokemonCol;
    }
}
