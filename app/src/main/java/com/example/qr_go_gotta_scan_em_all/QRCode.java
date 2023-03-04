package com.example.qr_go_gotta_scan_em_all;

import android.location.Location;
import android.media.Image;

public class QRCode {
    private int code;
    private int score;
    private Location geoLocation;
    private Image image;

    public QRCode(String src) {
        code = encrypt(src);
    }

    private int encrypt(String src) {
        return 0;
    }
}
