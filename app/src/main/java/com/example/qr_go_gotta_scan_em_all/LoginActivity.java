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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    // Declare method to switch to LoginActivity
    private TextView userText;
    private String userName;
    private Intent intent;
    private ImageView loginButton;

    private Database db;

    private Intent networkFailed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText = findViewById(R.id.editTextTextPersonName);
        networkFailed = new Intent(LoginActivity.this, ConnectionErrorActivity.class);
        loginButton = findViewById(R.id.enter_now_button);
        intent = new Intent(this, MainActivity.class);
        db = new Database(this);

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


    // Create user session function
    private void createUserSession(){
        userName = userText.getText().toString();
        PlayerIDGenerator playerIDGenerator;


        // Firstly check the the database if the userName is taken or not.
        boolean isTaken = isUserTaken();

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

            Player tempPlayer = new Player(userName,playerIDGenerator.getUserId());

            // add the player to DB
            addPlayer(tempPlayer);

            switchToMainActivity();

        } else{
            // Warn the user about the username being taken through a toast
        }

    }

    private void switchToMainActivity() {
        startActivity(intent);
        finish();
    }

    private void switchToNetworkFail() {
        startActivity(networkFailed);
        finish();
    }

    private boolean isUserTaken(){

        return false;
    }

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


}