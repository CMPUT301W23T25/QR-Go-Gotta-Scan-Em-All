package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.UUID;

/**

 A utility class for generating a unique ID for the player.

 The ID can be generated using a UUID or the Android device's unique ID.
 */
public class PlayerIDGenerator implements Serializable {
    // The AppUser has a userName and userId
    /**
     The player ID generated using UUID.
     */

    private String userId;
    /**

     Creates a new PlayerIDGenerator instance and generates a unique ID using UUID.
     */
    public PlayerIDGenerator() {
        this.userId = UUID.randomUUID().toString();
    }

    /**

     Creates a new PlayerIDGenerator instance and generates a unique ID using the Android device's ID.
     @param context The context of the calling activity.
     */

    public PlayerIDGenerator(Context context) {
        this.userId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    /**

     Returns the player ID.
     @return The player ID.
     */
    public String getUserId() {
        return userId;
    }
}
