package com.example.qr_go_gotta_scan_em_all;

public class Pokemon {
    private String ID;
    private String name;
    private String hash;

    public Pokemon(String ID, String name, String hash) {
        this.ID = ID;
        this.name = name;
        this.hash = hash;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String visualReper(){
        String visual = "";

        return visual;
    }
}
