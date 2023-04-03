package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PokemonUnitTest {
    @Test
    public void testGetName() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals("Sinistea with Glasses", pokemon.getName());
    }

    @Test
    public void testGetPokemonId() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals("a7c280e773d1d2e4f243d88f2e1a5665aff97694f741cbd78ee9edf62954612c", pokemon.getID());
    }

    @Test
    public void testGetScore() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals(50.0, pokemon.getScore(), 0);
    }

    @Test
    public void testSetID() {
        Pokemon pokemon = new Pokemon();
        pokemon.setID("Pikachu");
        assertEquals("Pikachu", pokemon.getID());
    }

    @Test
    public void testVisualReper() {
        Pokemon pokemon = new Pokemon("Pikachu");
        assertEquals(
                "  ^~^  ,\n"+
                        " ◪-◪ \n"+
                        " /   \\/ \n"+
                        "(\\|||/) \n",

                pokemon.visualReper());
    }

    @Test
    public void testInitHash() {
        Pokemon pokemon = new Pokemon();
        pokemon.initHash("Pikachu");
        assertEquals("a7c280e773d1d2e4f243d88f2e1a5665aff97694f741cbd78ee9edf62954612c", pokemon.getID());
    }
}
