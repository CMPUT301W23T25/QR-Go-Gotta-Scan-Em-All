package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.UUID;

public class PlayerIDGenerator implements Serializable {
    // The AppUser has a userName and userId

    private String userId;

    public PlayerIDGenerator() {
        this.userId = UUID.randomUUID().toString();
    }



    public PlayerIDGenerator(Context context) {
        this.userId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }


    public String getUserId() {
        return userId;
    }
}
