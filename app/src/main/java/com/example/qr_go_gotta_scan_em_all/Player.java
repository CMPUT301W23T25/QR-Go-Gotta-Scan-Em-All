package com.example.qr_go_gotta_scan_em_all;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    ArrayList<Pokemon> QRArray;
    private String userName;
    private String userId;

    public Player(String userName, String userId, ArrayList<Pokemon> QRArray) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.QRArray = QRArray;
    }

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.QRArray = new ArrayList<Pokemon>();
    }


    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Pokemon> getQRCode(){
        return new ArrayList<>(QRArray);
    }
}
