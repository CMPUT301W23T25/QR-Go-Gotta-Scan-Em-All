package com.example.qr_go_gotta_scan_em_all;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    // Declare method to switch to LoginActivity
    TextView userText;
    String userName;
    Intent intent;
    ImageView loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText = findViewById(R.id.editTextTextPersonName);
        userName = userText.getText().toString();
        loginButton = findViewById(R.id.enter_now_button);
        intent = new Intent(LoginActivity.this, MainActivity.class);

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

    private boolean isUserTaken(){

        return false;
    }


}