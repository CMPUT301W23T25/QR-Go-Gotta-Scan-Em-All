package com.example.qr_go_gotta_scan_em_all;

import androidx.core.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**

 A class representing a player in the game.
 */
public class Player implements Serializable {

    private ArrayList<Pokemon> pokemonArray;
    private String userName;
    private String userId;
    // leaderboardStats schema
    // {
    //      "userName": userName,
    //      "highScore": highScore,
    // }
    private Map<String, Object> leaderboardStats;

    private ArrayList<Player> friends;

/*    private ArrayList<Map<Pokemon,Pair<Object,Object>>>pokemonImageLoc;*/

    private String emailAddress;

    /**
     * Constructs a Player object with the given parameters.
     *
     * @param pokemonArray      an array list of the player's Pokemon
     * @param userName          the username of the player
     * @param userId            the unique ID of the player
     * @param leaderboardStats  a map of the player's leaderboard stats
     * @param friends           an array list of the player's friends
     * @param emailAddress     the email address of the player
     */

    public Player(ArrayList<Pokemon> pokemonArray, String userName, String userId, Map<String, Object> leaderboardStats, ArrayList<Player> friends, String emailAddress) {
        this.pokemonArray = pokemonArray;
        this.userName = userName;
        this.userId = userId;
        this.leaderboardStats = leaderboardStats;
        this.friends = friends;
        this.emailAddress = emailAddress;
    }

    /**
     * Constructs a Player object with the given username and unique ID.
     *
     * @param userName  the username of the player
     * @param userId    the unique ID of the player
     */

    public Player(String userName, String userId) {

        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = userName;
        this.pokemonArray = new ArrayList<Pokemon>();
        this.leaderboardStats = new HashMap<String, Object>() {{
           put("userName", userName);
           put("highScore", 0.0);
        }};
        this.friends = new ArrayList<Player>();
/*        this.pokemonImageLoc = new ArrayList<Map<Pokemon,Pair<Object,Object>>>();*/
    }

    /**
     * Returns the username of the player.
     *
     * @return the username of the player
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the unique ID of the player.
     *
     * @return the unique ID of the player
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns an array list of the player's Pokemon.
     *
     * @return an array list of the player's Pokemon
     */
    public ArrayList<Pokemon> getPokemonArray(){
        return new ArrayList<>(this.pokemonArray);
    }

    /**
     * Sets the player's Pokemon array list to the given array list.
     *
     * @param pokemonArray  the new array list of the player's Pokemon
     */
    public void setPokemonArray(ArrayList<Pokemon> pokemonArray) {
        this.pokemonArray = pokemonArray;
    }

    /**
     Adds a Pokemon object to the player's array of Pokemon.
     @param pokemon the Pokemon object to add to the array
     */
    public void addPokemon(Pokemon pokemon) {
        this.pokemonArray.add(pokemon);
    }

    /**
     Removes a Pokemon object from the player's array of Pokemon.
     @param pokemon the Pokemon object to remove from the array
     */
    public void removePokemon(Pokemon pokemon) {
        this.pokemonArray.remove(pokemon);
    }

    /**
     Returns a Map object containing the leaderboard statistics of the player.
     @return the leaderboard statistics of the player as a Map object
     */
    public Map<String, Object> getLeaderboardStats() {
        return leaderboardStats;
    }

    /**
     Removes a Pokemon object from the player's array of Pokemon.
     @param pos the position of the pokemon object to delete
     */
    public void removePokemonAtIndex(int pos) {
        this.pokemonArray.remove(pos);
    }

    /**
     Gets a Pokemon object to the player's array of Pokemon.
     @param pos the position of the pokemon object to get
     */
    public Pokemon getPokemonAtIndex(int pos) {
        return this.pokemonArray.get(pos);
    }
}
