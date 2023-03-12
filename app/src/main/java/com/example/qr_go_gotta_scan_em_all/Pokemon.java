package com.example.qr_go_gotta_scan_em_all;

import android.graphics.Bitmap;
import android.util.Pair;

import java.io.Serializable;
import java.util.UUID;

public class Pokemon implements Serializable {
    private String ID;
    private String name;


    // Implement later
    private Bitmap image;

    private String location;

    private int hash = 7;


    public Pokemon(String ID, String name, Bitmap image, String location) {
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.location = location;
    }

    public Pokemon(String name, Bitmap image, String location) {
        this.ID = UUID.randomUUID().toString();
        this.image = image;
        this.location = location;


        for (int i = 0; i < name.length(); i++) {
            hash = hash*31 + name.charAt(i);
        }
    }


    public Pokemon(String name) {
        this.ID = UUID.randomUUID().toString();
        this.image = null;
        this.location = null;


        for (int i = 0; i < name.length(); i++) {
            hash = hash*31 + name.charAt(i);
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String visualReper(){
        String visual = "";

        return visual;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public int returnScore(){
        return 0;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHash() {
        return hash;
    }
}
