package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PokemonTest {
    /**
     * Test pokemon object from pokemon class
     *
     */
    @Test
    public void testPokemon() {
        Pokemon pokemon = new Pokemon("Sinistea with Glasses");
        assertEquals("Mega Wailord", pokemon.getName());
        assertEquals("d847e800607c1eb98786462ac5c47606d6616b9756af8202d0586a3a75445fb4", pokemon.getID());
    }

    /**
     * Test pokemon visualReper function
     *
     */
    @Test
    public void testVisualReper() {
        Pokemon pokemon = new Pokemon("Sinistea with Glasses");
        String expectedOutput = " /----(  ^_^  )----\\\n"+
                "|  {   \\('v')/   }  |\n"+
                "|   {   /   \\   }   |\n"+
                "|_)(   /\\   /\\   )(_|\n"+
                "|)  (_ | \\|/  |_)  (|\n"+
                "'     \"--^^^^--\"    '\n";
        String actualOutput = pokemon.visualReper();
        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }

    /**
     * Test pokemon calculateScore() function
     *
     */
    @Test
    public void testCalculateScore() {
        Pokemon pokemon = new Pokemon("Sinistea with Glasses");
        // set stats of pokemon "Sinistea with Glasses"
        double expectedOutput = 30.0;
        double actualOutput = pokemon.calculateScore();
        assertEquals(expectedOutput, actualOutput, 0.001);
    }

    /**
     * Test pokemon generateName() function
     *
     */
    @Test
    public void testGenerateName() {
        Pokemon pokemon = new Pokemon("Sinistea with Glasses");
        String actualOutput = pokemon.generateName();
        assertEquals("Mega Wailord", actualOutput);
    }

}
