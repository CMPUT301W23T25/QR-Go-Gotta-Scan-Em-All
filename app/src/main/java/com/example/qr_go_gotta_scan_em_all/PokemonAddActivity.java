package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PokemonAddActivity extends AppCompatActivity {

    ImageView photo_btn;
    ImageView add_location;
    ImageView save_btn;
    ImageView release_btn;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Bitmap locationImgRaw;
    String pokemonCaught;
    private byte[] locationImgCompressed;

    boolean locationAdded = false;

    boolean photoAdded = false;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_add);
        db = new Database(this);
        release_btn = findViewById(R.id.release_pokemon_button);
        photo_btn = findViewById(R.id.add_photo_button);
        save_btn = findViewById(R.id.capture_pokemon_button);
        add_location =findViewById(R.id.add_location_button);

        pokemonCaught = (String) getIntent().getSerializableExtra("PokemonCaught");
        TextView title = findViewById(R.id.captured_pokemon_name);
        title.setText("It's "+pokemonCaught);

        //referenced from -https://developer.android.com/training/camera/camera-intents
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundleImage = result.getData().getExtras();
                    locationImgRaw = (Bitmap) bundleImage.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    locationImgRaw.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    locationImgCompressed = stream.toByteArray();
                    // Convert the raw image into a JPEG so it doesn't take storage too much

                }
            }
        });

        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(cameraIntent);
                photoAdded = true;
            }
        });

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to implement
                //add this pokemon to class
                locationPermissionGranted = checkLocationPermission();
                if (locationPermissionGranted) {
                    AddLocation();
                    Toast.makeText(PokemonAddActivity.this, "Geolocation Added", Toast.LENGTH_SHORT).show();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(PokemonAddActivity.this);
                    locationAdded = true;
                } else {
                    locationAdded = false;
                }

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save to db
                Toast.makeText(PokemonAddActivity.this, "Pokemon was added to your collection", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PokemonAddActivity.this,MainActivity.class);
                intent.putExtra("PokemonCaught", pokemonCaught);
                Pokemon pokemon = new Pokemon(pokemonCaught);
                if (photoAdded){
                    // NOTE: Null is temporary
                    pokemon.setLocation(null);
                }

                if (locationAdded){
                    pokemon.setImage(locationImgRaw);
                }

                intent.putExtra("pokemon",pokemon);

                // Update the database with the new pokemon as well as the Players' lists of pokemons
                // Also add the Image in the Images collection, assign the Pokemon field to the ID of the pokemon, the
                // player field to the id of the player, and the visual field to the bytearray of the image.
                // The key of the image besides the ID will be the player ID and the Pokemon ID.
                setResult(RESULT_OK,intent);

                finish();
            }
        });
        release_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PokemonAddActivity.this, "Pokemon released to the wild", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

/*    private FileOutputStream bmpToJpeg(Bitmap bmp){
        try {
            FileOutputStream out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void addPokemon(Pokemon p){
        // Add the player to the database
        // NOTE: A player object that has an ID and username must be passed into the database
        String ID = p.getID();
        HashMap<String, Object> pokeMap = new HashMap<>();
/*        pokeMap.put("ID",p.getID());*/
        

        // make sure the specific ID of the player is used
        DocumentReference docRef = db.getPlayerCol().document(ID);

        // Set the data of the document with the playerMap
        docRef.set(pokeMap)
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

                    }
                });
    }


}