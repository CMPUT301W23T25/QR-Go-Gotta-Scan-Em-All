package com.example.qr_go_gotta_scan_em_all;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**

 The LoginActivity class represents the login screen of the app where the user can create a new session or continue an existing one.
 The user can login by entering a username that will be saved in the database.
 */
public class LoginActivity extends AppCompatActivity {
    // Declare method to switch to LoginActivity
    private TextView userText;
    private String userName;
    private Intent intent;
    private ImageView loginButton;

    private Database db;

    private Intent networkFailed;

    private Player player;

    boolean isRegistered;


    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * @param savedInstanceState the Bundle containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText = findViewById(R.id.editTextTextPersonName);
        networkFailed = new Intent(LoginActivity.this, ConnectionErrorActivity.class);
        loginButton = findViewById(R.id.enter_now_button);
        intent = new Intent(this, MainActivity.class);
        db = new Database(this);

        getPlayerData();

        if(!isRegistered){

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent class will help to go to next activity using
                    // it's object named intent.
                    // SecondActivity is the name of new created EmptyActivity.
                    createUserSession();
                }
            });
        }

    }



    /**
     * Creates a new session for the user. The user enters their username and a new Player object is created and added to the database.
     * If the username is already taken, the user is informed and no new session is created.
     */
    private void createUserSession(){
        userName = userText.getText().toString();
        PlayerIDGenerator playerIDGenerator;


        // Firstly check the the database if the userName is taken or not.
        boolean isTaken = isUserNameTaken(userName);

        // - if it is taken then inform the user
        // - otherwise create login the user and add the entry to the database

        if (!isTaken){
            // Next create a AppUser class
            // NOTE: For now there isn't any distinction between player and owner.

            /*#TODO
            - Either use the PhoneID or generate a randomID through randomUUID
            - If PhoneID is used, check the database to find the phoneID of the user
            */
            playerIDGenerator = new PlayerIDGenerator(this);

            player = new Player(userName,playerIDGenerator.getUserId());

            // add the player to DB
            addPlayer(player);

            intent.putExtra("player",player);
            switchToMainActivity();



        } else{
            Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show();
        }

    }

    /**

     Starts MainActivity and finishes the current activity.
     */
    private void switchToMainActivity() {
        startActivity(intent);
        finish();
    }

    /**

     Starts the NetworkFailActivity and finishes the current activity.
     */
    private void switchToNetworkFail() {
        startActivity(networkFailed);
        finish();
    }

    /**

     Adds a player to the database with the given player object.
     @param p The Player object to be added to the database.
     */
    private void addPlayer(Player p){
        // Add the player to the database
        // NOTE: A player object that has an ID and username must be passed into the database
        String ID = p.getUserId();
        HashMap<String, Object> playerMap = new HashMap<>();
        playerMap.put("username",p.getUserName());
        playerMap.put("pokemon_owned",new ArrayList<String>());
        playerMap.put("leaderboard_stats",new HashMap<String,String>());
        playerMap.put("friends",new ArrayList<String>());


        // make sure the specific ID of the player is used
        DocumentReference docRef = db.getPlayerCol().document(ID);

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
                        switchToNetworkFail();
                    }
                });
    }
    /**

     Retrieves player data from the database and sets the player object and registration status accordingly.
     */
    private void getPlayerData() {
        Map<String,Object> playerMap = new HashMap<>();
        PlayerIDGenerator login = new PlayerIDGenerator(this);
        db.getPlayerCol().document(login.getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        player = new Player((String)document.get("username"), document.getId());
                        isRegistered = true;
                        System.out.println("registered");
                        intent.putExtra("player",player);
                        switchToMainActivity();

                    }else{
                        System.out.println("tre");
                        isRegistered = false;
                    }

                }else{
                    switchToNetworkFail();
                }
            }
        });

    }

    /**

     Checks whether a given username is already taken by another player in the database.
     @param userName The username to check.
     @return True if the username is already taken, false otherwise.
     */
    private boolean isUserNameTaken(String userName){
        boolean[] userTaken = new boolean[1];
        db.getPlayerCol().whereEqualTo("user_name",userName).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Error getting documents
                    Log.w(TAG, "Error getting documents.", e);
                    switchToNetworkFail();
                    return;
                }

                if (queryDocumentSnapshots.isEmpty()) {
                    // Document doesn't exist
                    userTaken[0] = false;
                } else {
                    // Document exists
                    userTaken[0] = true;
                }
            }
        });

        return userTaken[0];
    }

}