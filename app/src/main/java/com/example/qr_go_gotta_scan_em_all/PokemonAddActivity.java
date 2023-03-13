package com.example.qr_go_gotta_scan_em_all;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**

 {@link PokemonAddActivity} allows the user to add a new Pokemon to their collection.

 The user can capture a photo, add geolocation and save the captured Pokemon.

 If the user does not add a photo or location, the respective fields will be null in the resulting Pokemon object.
 */
public class PokemonAddActivity extends AppCompatActivity {

    private ImageView photo_btn;
    private ImageView add_location;
    private ImageView save_btn;
    private ImageView release_btn;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Bitmap locationImgRaw;
    private String pokemonCaught;
//    private byte[] locationImgCompressed;

    boolean locationAdded = false;

    boolean photoAdded = false;
    private boolean locationPermissionGranted;
    private Database db;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double longitude;
    private double latitude;

    /**

     Sets up the layout and initializes the UI elements.

     Sets up the click listeners for the buttons.

     Sets up the activity result launcher for the camera intent.

     @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_add);
        db = new Database(this);
        release_btn = findViewById(R.id.release_pokemon_button);
        photo_btn = findViewById(R.id.add_photo_button);
        save_btn = findViewById(R.id.capture_pokemon_button);
        add_location = findViewById(R.id.add_location_button);

        pokemonCaught = (String) getIntent().getSerializableExtra("PokemonCaught");
        TextView title = findViewById(R.id.captured_pokemon_name);
        title.setText("It's " + pokemonCaught);

        //referenced from -https://developer.android.com/training/camera/camera-intents
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundleImage = result.getData().getExtras();
                    locationImgRaw = (Bitmap) bundleImage.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    locationImgRaw.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                    locationImgCompressed = stream.toByteArray();
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
                    Toast.makeText(PokemonAddActivity.this, "Geolocation Added", Toast.LENGTH_SHORT).show();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(PokemonAddActivity.this);
                    locationAdded = true;
                    AddLocation();
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
                Intent intent = new Intent();
                intent.putExtra("PokemonCaught", pokemonCaught);
                Pokemon pokemon = new Pokemon(pokemonCaught);
                if (photoAdded) {
                    // NOTE: Null is temporary
                    pokemon.setImage(locationImgRaw);
                }
                if (locationAdded) {
                    pokemon.setLocation(latitude, longitude);
                }

                intent.putExtra("pokemon", pokemon);

                // Update the database with the new pokemon as well as the Players' lists of pokemons
                // Also add the Image in the Images collection, assign the Pokemon field to the ID of the pokemon, the
                // player field to the id of the player, and the visual field to the bytearray of the image.
                // The key of the image besides the ID will be the player ID and the Pokemon ID.
                setResult(RESULT_OK, intent);
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

    /**
     location permissions, getting the current device location, and handling the user's response to the location permission request.
     The checkLocationPermission() method checks if the app has been granted location permission by the user. If not, it displays
     a Toast message asking the user to grant the permission and requests it from the user. It returns true if the permission is
     granted, and false otherwise.
     */
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(PokemonAddActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     *The AddLocation() method gets the current device location using
     * the FusedLocationProviderClient. If the app has been granted
     */
    //referenced from - https://www.youtube.com/watch?v=I5ektSfv4lw&ab_channel=Foxandroid
    private void AddLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(PokemonAddActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    latitude = addresses.get(0).getLatitude();
                                    longitude = addresses.get(0).getLongitude();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            checkLocationPermission();
        }
    }
}
