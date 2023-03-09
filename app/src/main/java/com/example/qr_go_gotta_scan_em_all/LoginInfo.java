package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.provider.Settings;

import java.io.Serializable;
import java.util.UUID;

public class LoginInfo implements Serializable {
    // The AppUser has a userName and userId
    private String userName;
    private String userId;

    public LoginInfo(String userName) {
        this.userName = userName;
        this.userId = UUID.randomUUID().toString();
    }

    public LoginInfo(String userName, Context context) {
        this.userName = userName;
        this.userId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public LoginInfo(Context context) {
        this.userId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }


    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }
}
