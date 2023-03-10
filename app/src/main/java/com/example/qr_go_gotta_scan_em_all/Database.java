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

public class Database {
    FirebaseFirestore fireStore;
    DatabaseReference dbRef;
    DatabaseReference playersRef;
    DatabaseReference pokemonRef;

    CollectionReference playerCol;

    CollectionReference pokemonCol;
    /**
     * Initialize the Database
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

    public void addPlayer(Player player) {
        // Add the player to the database
        // NOTE: A player object that has an ID and username must be passed into the database
        String ID = player.getUserId();
        HashMap<String, Object> playerMap = new HashMap<>();
        playerMap.put("username",player.getUserName());
        // order of hash map: {ID of Pokemon in Database:(The image, the Latitude and longitude)}
        playerMap.put("pokemon_owned",new ArrayList<Map<String, Pair<Object,Object>>>());

        // make sure the specific ID of the player is used
        DocumentReference docRef = playerCol.document(ID);

        // Set the data of the document with the playerMap
        docRef.set(playerMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Player data added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding player data", e);
                        throw new RuntimeException("Failed to add player data to Firestore");
                    }
                });

/*        // Code with exception
        // Set the data of the document with the playerMap
        docRef.set(playerMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Player data added successfully");

                        // Throw an exception if the task fails
                        if (!docRef.get().isSuccessful()) {
                            throw new RuntimeException("Failed to add player data to Firestore");
                        }
                    }
                });*/
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
