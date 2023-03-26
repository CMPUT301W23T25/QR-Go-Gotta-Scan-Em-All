package com.example.qr_go_gotta_scan_em_all;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
        * A simple {@link ConnectionErrorActivity} subclass.
        * Use the {@link AppCompatActivity} factory method to
        * create an instance of this class.
        *
        */
public class ConnectionErrorActivity extends AppCompatActivity {

    private Intent try_again_attempt;
    private ImageView try_again_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);
        try_again_attempt = new Intent(ConnectionErrorActivity.this, LoginActivity.class);
        try_again_btn = findViewById(R.id.try_again_button);

        try_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(try_again_attempt);
            }
        });
    
    }


}