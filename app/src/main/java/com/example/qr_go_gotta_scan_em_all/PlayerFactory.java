package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * A class for generating unique player IDs.
 */
public class PlayerFactory implements Serializable {
     // The AppUser has a userName and userId
     String userId;

     /**
      * 
      * Constructor for generating a Player based on the device's Android ID.
      * 
      * @param context The context used to get the Android ID.
      * @return A Player with the generated ID based on the Android ID
      */
     public PlayerFactory(Context context) {

          this.userId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
     }

     /**
      * Generates a new Player instance using the unique user ID.
      *
      * @return A Player instance with the generated ID based on the Android ID.
      */
     public Player generatePlayer() {
          Player player = new Player(userId);
          return player;
     }

     /**
      * 
      * Returns the generated player ID.
      * 
      * @return A string representing the player ID.
      */
     public String getUserId() {
          return userId;
     }
}
