package com.example.qr_go_gotta_scan_em_all;

import androidx.core.util.Pair;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**

 A class representing a player in the game.
 */
public class Player implements Serializable {
    private String userName;
    private String userId;
    private ArrayList<Pokemon> pokemonArray;
    private Pokemon bestPokemon;
    private ArrayList<Player> friends;
    private String emailAddress;
/*    private ArrayList<Map<Pokemon,Pair<Object,Object>>>pokemonImageLoc;*/

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
        this.bestPokemon = null;
        this.friends = new ArrayList<Player>();
/*        this.pokemonImageLoc = new ArrayList<Map<Pokemon,Pair<Object,Object>>>();*/
    }

    public Player(String userId) {
        // The login contains the unique ID of the player
        this.userId = userId;
        this.userName = null;
        this.pokemonArray = new ArrayList<Pokemon>();
        this.bestPokemon = null;
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
        return this.pokemonArray;
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

        // update bestPokemon if required
        if (bestPokemon == null || (bestPokemon.getScore() < pokemon.getScore())) {
            bestPokemon = pokemon;
        }
    }

    /**
     Removes a Pokemon object from the player's array of Pokemon.
     @param pokemon the Pokemon object to remove from the array
     */
    public void removePokemon(Pokemon pokemon) {
        this.pokemonArray.remove(pokemon);

        // update bestPokemon if required
        if (bestPokemon == pokemon) {
            Pokemon newBest = null;

            // find the new best pokemon
            for (Pokemon p : pokemonArray) {
                if (newBest == null || p.getScore() > newBest.getScore()) {
                    newBest = p;
                }
            }

            // update bestPokemon
            bestPokemon = newBest;
        }
    }

    /**
     Removes a Pokemon object from the player's array of Pokemon at a specific index.
     @param pos the position of the pokemon object to delete
     */
    public void removePokemonAtIndex(int pos) {
        Pokemon pokemon = pokemonArray.get(pos);
        this.removePokemon(pokemon);
    }

    /**
     Gets a Pokemon object to the player's array of Pokemon.
     @param pos the position of the pokemon object to get
     */
    public Pokemon getPokemonAtIndex(int pos) {
        return this.pokemonArray.get(pos);
    }

    /**
     * Returns the player's best Pokemon.
     *
     * @return the player's best Pokemon
     */
    public Pokemon getBestPokemon() {
        return bestPokemon;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
