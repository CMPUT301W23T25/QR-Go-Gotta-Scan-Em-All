package com.example.qr_go_gotta_scan_em_all;

import java.util.ArrayList;

public class Player {

    ArrayList<QRCode> QRArray;
    private String userName;
    private String userId;

    public Player(String userName, String userId, ArrayList<QRCode> QRArray) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.QRArray = QRArray;
    }

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.QRArray = new ArrayList<QRCode>();
    }


    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<QRCode> getQRCode(){
        return new ArrayList<>(QRArray);
    }
}
