package com.example.qr_go_gotta_scan_em_all;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PokemonAdd extends AppCompatActivity {

    Button photo_btn;
    Button add_location;
    Button save_btn;
    Button release_btn;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_add);

        release_btn = findViewById(R.id.release_btn);
        photo_btn = findViewById(R.id.add_photo_btn);
        save_btn = findViewById(R.id.add_pokemon_btn);
        add_location =findViewById(R.id.add_location_btn);

        String pokemonCaught = (String) getIntent().getSerializableExtra("pokemonCaught");
        TextView title = findViewById(R.id.pokemon_name);
        title.setText("You caught "+pokemonCaught);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundleImage = result.getData().getExtras();
                    Bitmap bitmapImage = (Bitmap) bundleImage.get("data");

                }
            }
        });

        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(cameraIntent);

            }
        });

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to implement
                Toast.makeText(PokemonAdd.this, "Geolocation Added", Toast.LENGTH_SHORT).show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save to db
                Toast.makeText(PokemonAdd.this, "Pokemon was added to your collection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        release_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PokemonAdd.this, "Pokemon released to the wilde", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


}