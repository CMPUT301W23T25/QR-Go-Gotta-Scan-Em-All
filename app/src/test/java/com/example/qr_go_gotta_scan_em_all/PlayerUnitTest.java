package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerUnitTest {

    @Test
    public void testSetName() {
        Player player = new Player();
        player.setUserName("Ash Ketchum");
        assertEquals("Ash Ketchum", player.getUserName());
    }

    @Test
    public void testAddPokemon() {
        Player player = new Player();
        Pokemon pokemon = new Pokemon("Pikachu");
        player.addPokemonToArray(pokemon);
        assertEquals(1, player.getPokemonArray().size());
        assertTrue(player.getPokemonArray().contains(pokemon));
    }

    @Test
    public void testRemovePokemon() {
        Player player = new Player();
        Pokemon pokemon = new Pokemon("Pikachu");
        player.addPokemonToArray(pokemon);
        player.removePokemon(0);
        assertEquals(0, player.getPokemonArray().size());
        assertFalse(player.getPokemonArray().contains(pokemon));
    }

    @Test
    public void testGetScore() {
        Player player = new Player();
        Pokemon pokemon1 = new Pokemon("Charmander");
        Pokemon pokemon2 = new Pokemon("Squirtle");
        Pokemon pokemon3 = new Pokemon("Bulbasaur");
        player.addPokemonToArray(pokemon1);
        player.addPokemonToArray(pokemon2);
        player.addPokemonToArray(pokemon3);
        double expectedScore = pokemon1.getScore() + pokemon2.getScore() + pokemon3.getScore();
        System.out.println();
    }

}
