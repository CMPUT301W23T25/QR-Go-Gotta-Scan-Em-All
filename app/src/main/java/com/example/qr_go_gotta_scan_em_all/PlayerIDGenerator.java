package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.UUID;

/**

 A class for generating unique player IDs.
 */
public class PlayerIDGenerator implements Serializable {
    // The AppUser has a userName and userId

    private String userId;
    /**

     Constructor for generating a random UUID-based player ID.
     */
    public PlayerIDGenerator() {
        this.userId = UUID.randomUUID().toString();
    }


    /**

     Constructor for generating a player ID based on the device's Android ID.
     @param context The context used to get the Android ID.
     */
    public PlayerIDGenerator(Context context) {
        this.userId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    /**

     Returns the generated player ID.
     @return A string representing the player ID.
     */
    public String getUserId() {
        return userId;
    }
}
