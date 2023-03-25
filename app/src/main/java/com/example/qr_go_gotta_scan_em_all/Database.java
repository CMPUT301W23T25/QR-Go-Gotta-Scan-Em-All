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

/**
 * 
 * A class representing a database for managing player and pokemon data using
 * Firebase
 */
public class Database {
    private FirebaseFirestore fireStore;
    private DatabaseReference dbRef;
    private DatabaseReference playersRef;
    private DatabaseReference pokemonRef;

    private CollectionReference playerCol;

    private CollectionReference pokemonCol;

    /**
     * Initialize the Database
     */
    public Database(Context context) {
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
     * Returns the instance of the Firebase Cloud Firestore database used by this
     * instance of the database.
     * 
     * @return The instance of the Firebase Cloud Firestore database used by this
     *         instance of the database.
     */
    public FirebaseFirestore getFireStore() {
        return fireStore;
    }

    /**
     * Returns the instance of the Firebase Realtime Database used by this instance
     * of the database.
     * 
     * @return The instance of the Firebase Realtime Database used by this instance
     *         of the database.
     */
    public DatabaseReference getDbRef() {
        return dbRef;
    }

    /**
     * Returns the reference to the "players" node in the Firebase Realtime Database
     * used by this instance of the database.
     * 
     * @return The reference to the "players" node in the Firebase Realtime Database
     *         used by this instance of the database.
     */
    public DatabaseReference getPlayersRef() {
        return playersRef;
    }

    /**
     * Returns the reference to the "pokemon" node in the Firebase Realtime Database
     * used by this instance of the database.
     * 
     * @return The reference to the "pokemon" node in the Firebase Realtime Database
     *         used by this instance of the database.
     */
    public DatabaseReference getPokemonRef() {
        return pokemonRef;
    }

    /**
     * Returns the instance of the Firebase Cloud Firestore collection containing
     * the player data used by this instance of the database.
     * 
     * @return The instance of the Firebase Cloud Firestore collection containing
     *         the player data used by this instance of the database.
     */
    public CollectionReference getPlayerCol() {
        return playerCol;
    }

    /**
     * Returns the instance of the Firebase Cloud Firestore collection containing
     * the pokemon data used by this instance of the database.
     * 
     * @return The instance of the Firebase Cloud Firestore collection containing
     *         the pokemon data used by this instance of the database.
     */
    public CollectionReference getPokemonCol() {
        return pokemonCol;
    }
}
