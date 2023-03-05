package com.example.qr_go_gotta_scan_em_all;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    // Declare method to switch to LoginActivity
    TextView userText;
    String phoneID;
    String userName;
    DeviceIdHelper dIDMan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dIDMan = new DeviceIdHelper(this);
        phoneID = dIDMan.getDeviceId();

    }


    // Create user session function
    private void createUserSession(){
        // Firstly check the the database if the phone ID is registered or not.

    }

    private void switchToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}