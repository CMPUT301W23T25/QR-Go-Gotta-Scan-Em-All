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

 The {@link: LoginActivity} class is responsible for handling the user login process and creating a user session.

 It contains methods to check if a username is already taken, add a new player to the database, and switch to the main activity or network failure activity.

 The class also contains variables such as TextView, String, Intent, ImageView, Database, Intent, and Player, which are used to store and manipulate user data.
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

     Called when the activity is starting.
     Initializes the UI elements and database object and checks if the user is already registered or not.
     If the user is not registered, sets a click listener on the login button to create a new user session.
     If the user is already registered, switches to MainActivity with the user data.
     If there is a network failure, switches to ConnectionErrorActivity.
     @param savedInstanceState Bundle
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

     Checks if the entered username is already taken.
     If the username is not taken, creates a new Player object and adds it to the database.
     If the username is taken, shows a Toast message to inform the user.
     If there is a network failure, switches to ConnectionErrorActivity.
     */

    // Create user session function
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
     Switches to MainActivity with the user data.
     */

    private void switchToMainActivity() {
        startActivity(intent);
        finish();
    }

    /**
     Switches to ConnectionErrorActivity if there is a network failure.
     */

    private void switchToNetworkFail() {
        startActivity(networkFailed);
        finish();
    }
    /**
     Adds a new player to the database.
     Creates a HashMap with the player data and sets the data of the document with the playerMap.
     If there is a network failure, switches to ConnectionErrorActivity.

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

     This method retrieves the player data from the database based on their login information,
     creates a Player object, and sets the "isRegistered" flag to true if the player is found in
     the database. It also switches to the main activity and passes the player object as an extra
     to the intent.
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
     This method checks if a given username already exists in the database.
     @param userName The username to check for in the database
     @return true if the username is already taken, false otherwise
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