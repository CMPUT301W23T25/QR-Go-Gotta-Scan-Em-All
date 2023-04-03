package com.example.qr_go_gotta_scan_em_all;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerUnitTest {
    @Test
    public void testGetUserName() {
        Player player = new Player("Ash Ketchum", "123456789");
        assertEquals("Ash Ketchum", player.getUserName());
    }

    @Test
    public void testGetUserId() {
        Player player = new Player("Ash Ketchum", "123456789");
        assertEquals("123456789", player.getUserId());
    }

    @Test
    public void testGetPokemonArray() {
        Player player = new Player("Ash Ketchum", "123456789");
        ArrayList<Pokemon> pokemonArray = new ArrayList<Pokemon>();
        assertEquals(pokemonArray, player.getPokemonArray());

        // add pokemon
        Pokemon pokemon = new Pokemon("Pikachu");
        pokemonArray.add(pokemon);
        PokemonInformation pokemonInformation = new PokemonInformation(pokemon);
        player.addPokemon(pokemonInformation);
        assertEquals(pokemonArray.size(), player.getPokemonArray().size());

        // remove pokemon
        pokemonArray.remove(pokemon);
        player.removePokemon(pokemonInformation);
        assertEquals(pokemonArray.size(), player.getPokemonArray().size());
    }

    @Test
    public void testSetPokemonArray() {
        Player player = new Player("Ash Ketchum", "123456789");
        ArrayList<PokemonInformation> pokemonArray = new ArrayList<>();
        player.setPokemonArray(pokemonArray);
        assertEquals(pokemonArray, player.getPokemonArray());

        // add pokemon
        Pokemon pokemon = new Pokemon("Pikachu");
        pokemonArray.add(new PokemonInformation(pokemon));
        player.setPokemonArray(pokemonArray);
        assertEquals(pokemonArray, player.getPokemonArray());
    }

    @Test
    public void testAddPokemon() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        player.addPokemon(pokemonInformation1);
        assertEquals(pokemonInformation1, player.getPokemonArray().get(0));
        assertEquals(1, player.getPokemonArray().size());

        // add another pokemon
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation2);
        assertEquals(pokemonInformation2, player.getPokemonArray().get(1));
        assertEquals(2, player.getPokemonArray().size());
    }

    @Test
    public void testRemovePokemon() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation1);
        player.addPokemon(pokemonInformation2);
        assertEquals(2, player.getPokemonArray().size());

        // remove pokemon
        player.removePokemon(pokemonInformation1);
        assertEquals(1, player.getPokemonArray().size());
        assertEquals(pokemonInformation2, player.getPokemonArray().get(0));
        assertFalse(player.getPokemonArray().contains(pokemonInformation1));
    }

    @Test
    public void testRemovePokemonAtIndex() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation1);
        player.addPokemon(pokemonInformation2);
        assertEquals(2, player.getPokemonArray().size());

        // remove pokemon
        player.removePokemonAtIndex(0);
        assertEquals(1, player.getPokemonArray().size());
        assertEquals(pokemonInformation2, player.getPokemonArray().get(0));
        assertFalse(player.getPokemonArray().contains(pokemonInformation1));
    }

    @Test
    public void testGetPokemonAtIndex() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation1);
        player.addPokemon(pokemonInformation2);
        assertEquals(2, player.getPokemonArray().size());

        // get pokemon
        assertEquals(pokemonInformation1, player.getPokemonAtIndex(0));
        assertEquals(pokemonInformation2, player.getPokemonAtIndex(1));
    }

    @Test
    public void testGetBestPokemon() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation1);
        player.addPokemon(pokemonInformation2);
        assertEquals(2, player.getPokemonArray().size());

        // get best pokemon
        assertEquals(pokemon1, player.getBestPokemon());
        assertEquals(50.0, player.getBestPokemon().getScore(), 0.0);

        // remove best pokemon
        player.removePokemon(pokemonInformation1);
        assertEquals(pokemon2, player.getBestPokemon());
        assertEquals(10.0, player.getBestPokemon().getScore(), 0.0);

        // remove all pokemon
        player.removePokemon(pokemonInformation2);
        assertNull(player.getBestPokemon());
    }

    @Test
    public void testGetTotalScore() {
        Player player = new Player("Ash Ketchum", "123456789");
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Charmander");
        PokemonInformation pokemonInformation1 = new PokemonInformation(pokemon1);
        PokemonInformation pokemonInformation2 = new PokemonInformation(pokemon2);
        player.addPokemon(pokemonInformation1);
        player.addPokemon(pokemonInformation2);
        assertEquals(2, player.getPokemonArray().size());

        // get total score
        assertEquals(60.0, player.getTotalScore(), 0.0);

        // remove pokemon
        player.removePokemon(pokemonInformation1);
        assertEquals(10.0, player.getTotalScore(), 0.0);

        // remove all pokemon
        player.removePokemon(pokemonInformation2);
        assertEquals(0.0, player.getTotalScore(), 0.0);
    }
}