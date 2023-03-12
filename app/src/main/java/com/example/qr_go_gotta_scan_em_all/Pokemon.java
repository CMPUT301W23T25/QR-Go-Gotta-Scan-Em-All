package com.example.qr_go_gotta_scan_em_all;

import static java.lang.Math.pow;

import android.graphics.Bitmap;
import android.util.Pair;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Pokemon implements Serializable {
    private String ID;
    private String name;


    // Implement later
    private Bitmap image;

    private String location;

    private String hexHash;


    /** Constructor for making a new Pokemon when a QR code is scanned
     *
     * @param Rawname (the Raw value of the QR code)
     */
    public Pokemon(String Rawname) {
        this.ID = UUID.randomUUID().toString();
        this.image = null;
        this.location = null;
        calculateHash(name);
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

    /** Simple Scoring function that calculates the score based on the number of characters.
     *  The score changes based on the hexadecimal representation of the SHA-256 encrypted string
     *  The score is calculated by finding consecutive repeating characters, converting the repeated
     *  char into a string and put it to the power of the number of the repeats of that char - 1
     * @return the long of the score

     **/
    public long returnScore(){


        return 0;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHash() {
        return this.hexHash;
    }
    private void calculateHash(String name){
        // https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(name.getBytes(StandardCharsets.UTF_8));
        this.hexHash = bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        // Source https://stackoverflow.com/questions/2817752/java-code-to-convert-byte-to-hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private long calculateScore(){
        char previousChar = 'G';
        char c;
        String consecutiveChar = "";
        long score = 0;
        for (int i = 0; i < hexHash.length(); i++){
            c = hexHash.charAt(i);
            // Check if c is equal to the previous char if it is, then add it to the consecutiveChar
            if (c == previousChar){
                consecutiveChar += c;
            }

            else{
                if (!consecutiveChar.isEmpty()){
                    // calculate the score of the consecutive char
                    char x = consecutiveChar.charAt(0);
                    Integer base = 0;

                    // if the character is 0 then convert it to 20
                    if (x == '0'){
                        base = 20;
                    } else{
                        base = Integer.parseInt(String.valueOf(x),16);
                    }
                    score += pow()

                }
                consecutiveChar = "";
            }


        }
    }

}
