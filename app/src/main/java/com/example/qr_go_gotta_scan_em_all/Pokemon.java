package com.example.qr_go_gotta_scan_em_all;

import static java.lang.Math.pow;

import android.graphics.Bitmap;
import android.util.Pair;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**

 A class representing a Pokemon object.
 Contains information about the Pokemon's image, location, and ID.
 */
public class Pokemon implements Serializable {
    // Implement later
    private Bitmap image;


    private double locationLat;

    private double locationLong;

    private String ID;



    /** Constructor for making a new Pokemon when a QR code is scanned
     *
     * @param rawName (the Raw value of the QR code)
     */
    public Pokemon(String rawName) {
        this.image = null;
        this.ID = calculateHash(rawName);
        this.locationLong = 0.0;
        this.locationLat = 0.0;
    }
    /**
     * Constructor for creating an empty Pokemon object.
     */
    public Pokemon() {
        this.image = null;
        this.ID = null;
        this.locationLong = 0.0;
        this.locationLat = 0.0;

    }
    /**
     * Initializes the Pokemon's ID using a given raw name.
     * @param rawName The raw name to be hashed to generate the ID.
     */
    public void initHash(String rawName){
        this.ID = calculateHash(rawName);
    }
    /**
     * Returns the generated name of the Pokemon based on its ID.
     * @return The name of the Pokemon.
     */
    public String getName() {

        return generateName();
    }

    /**
     * Returns the ID of the Pokemon.
     * @return The ID of the Pokemon.
     */
    public String getID() {
        return ID;
    }
    /**
     * Sets the ID of the Pokemon to a given value.
     * @param ID The new ID of the Pokemon.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the visual representation of the Pokemon.
     * @return The visual representation of the Pokemon.
     */
    public String visualReper(){
        String visual = "";

        return visual;
    }
    /**
     * Returns the image of the Pokemon.
     * @return The image of the Pokemon.
     */
    public Bitmap getImage() {
        return image;
    }
    /**
     * Returns the location of the Pokemon.
     * @return The location of the Pokemon.
     */
    public Pair<Double,Double> getLocation() {
        return new Pair<Double,Double>(this.locationLat,this.locationLong);
    }

    /** Simple Scoring function that calculates the score based on the number of characters.
     *  The score changes based on the hexadecimal representation of the SHA-256 encrypted string
     *  The score is calculated by finding consecutive repeating characters, converting the repeated
     *  char into a string and put it to the power of the number of the repeats of that char - 1
     * @return the long of the score

     **/
    public double getScore(){
        return calculateScore();
    }
    /**
     * Sets the image of the Pokemon to a given value.
     * @param image The new image of the Pokemon.
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Sets the location of the Pokemon to a given value.
     * @param lat double of latitude
     *  @param lon double of longitude
     */
    public void setLocation(double lat, double lon) {
        this.locationLong = lat;
        this.locationLat = lon;
    }

    /**
     * Calculates the SHA-256 hash of a given name.
     * @param name The name to be hashed.
     * @return The SHA-256 hash of the name.
     */
    private String calculateHash(String name){
        // https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha-256-in-java
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(name.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    /**
     * Converts a byte array to a hexadecimal string.
     * @param bytes The byte array to be converted.
     * @return The hexadecimal string representation of the byte array.
     */
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

    /**

     Calculates a score for a given ID based on the consecutive characters in the ID.

     @return A double value representing the calculated score.
     */
    private double calculateScore(){

        System.out.println(ID);
        char previousChar = 'G';
        char c;
        String consecutiveChar = "";
        double score = 0;
        for (int i = 0; i < ID.length(); i++){
            c = ID.charAt(i);
            if (i > 0){
                previousChar = ID.charAt(i - 1);
            }
            // Check if c is equal to the previous char if it is, then add it to the consecutiveChar
            if (c == previousChar){
                consecutiveChar += c;
            }

            else{
                if (!consecutiveChar.isEmpty()){
                    // calculate the score of the consecutive char
                    char x = consecutiveChar.charAt(0);
                    int base = 0;

                    // if the character is 0 then convert it to 20
                    if (x == '0'){
                        base = 20;
                    } else{
                        base = Integer.parseInt(String.valueOf(x),16);
                    }
                    score += pow(base,consecutiveChar.length());

                }
                consecutiveChar = "";
            }


        }
        return score;
    }

    /**

     Converts a hexadecimal string to a binary string.
     @param hex The hexadecimal string to be converted.
     @return A binary string representing the input hexadecimal string.
     */
    private String hexToBinary(String hex) {
        BigInteger i = new BigInteger(hex, 16);
        String bin = i.toString(2);
        return bin;
    }

    /**

     Generates a name based on the binary representation of the ID.

     @return A string representing the generated name.
     */
    private String generateName(){
        String binary = hexToBinary(this.ID);
        List<List<String>> nameList = new ArrayList<List<String>>();
        nameList.add(Arrays.asList("cool","hot"));
        nameList.add(Arrays.asList("Fro","Glo"));
        nameList.add(Arrays.asList("Mo","Lo"));
        nameList.add(Arrays.asList("Spectral","Sonic"));
        nameList.add(Arrays.asList("Crab","Shark"));

        String name = "";
        for (int i = 0; i < nameList.size(); i++){
            String temp = "";
            char c = binary.charAt(i);
            temp+=c;
            int index = Integer.parseInt(temp);
            name += nameList.get(i).get(index);

        }
        return name;
    }

}
