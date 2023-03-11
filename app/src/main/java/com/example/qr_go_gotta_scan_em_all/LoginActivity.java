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

import com.google.android.gms.common.util.ScopeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

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

    private boolean isRegistered = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText = findViewById(R.id.editTextTextPersonName);
        networkFailed = new Intent(LoginActivity.this, ConnectionErrorActivity.class);
        loginButton = findViewById(R.id.enter_now_button);
        intent = new Intent(LoginActivity.this, MainActivity.class);
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
        LoginInfo loginInfo;


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
            loginInfo = new LoginInfo(userName,this);


                addPlayer(loginInfo);


            // Pass this onto the MainActivity
            intent.putExtra("loginInfo", loginInfo);

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

    private void addPlayer(LoginInfo login){
        // Add the player to the database
        // NOTE: A player object that has an ID and username must be passed into the database
        String ID = login.getUserId();
        HashMap<String, Object> playerMap = new HashMap<>();
        playerMap.put("username",login.getUserName());
        // order of hash map: {ID of Pokemon in Database:(The image, the Latitude and longitude)}
        playerMap.put("pokemon_owned",new ArrayList<Map<String, Pair<Object,Object>>>());

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