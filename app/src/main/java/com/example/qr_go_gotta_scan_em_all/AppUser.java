package com.example.qr_go_gotta_scan_em_all;

import android.telephony.TelephonyManager;

public class AppUser {
    // The AppUser has a user ID and Device ID
    private String userName;
    private String androidId;

    public AppUser(String userName,String androidId) {
        this.userName = userName;
        this.androidId = androidId;
    }
}
