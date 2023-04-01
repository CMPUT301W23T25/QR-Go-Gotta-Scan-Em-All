package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PokemonUnitTest {
    @Test
    public void testGetName() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals("Pikachu", pokemon.getName());
    }

    @Test
    public void testGetPokemonId() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals("Pikachu", pokemon.getID());
    }

    @Test
    public void testGetScore() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals(0, pokemon.getScore(), 0);
    }

    @Test
    public void testSetID() {
        Pokemon pokemon = new Pokemon();
        pokemon.setID("Pikachu");
        assertEquals("Pikachu", pokemon.getID());
    }

    @Test
    public void testVisualReper() {
        Pokemon pokemon = new Pokemon();
        assertEquals("Pikachu", pokemon.visualReper());
    }

    @Test
    public void testInitHash() {
        Pokemon pokemon = new Pokemon();
        pokemon.initHash("Pikachu");
        assertEquals("Pikachu", pokemon.getID());
    }
}
