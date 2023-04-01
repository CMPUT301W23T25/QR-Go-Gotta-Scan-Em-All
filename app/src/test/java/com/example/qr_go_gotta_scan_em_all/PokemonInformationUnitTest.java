package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.util.Pair;

import org.junit.Before;
import org.junit.Test;

public class PokemonInformationUnitTest {

    private PokemonInformation pI1;
    private PokemonInformation pI2;
    private Pokemon pokemon;

    @Before
    public void setUp() throws Exception {
        pokemon = new Pokemon("Pikachu");
        pI1 = new PokemonInformation(pokemon);
        pI2 = new PokemonInformation(pokemon, null, 45.12, 72.34, "Montreal", "Canada");
    }

    @Test
    public void testGetImageByteArray() {
        assertNull(pI1.getImageByteArray());
        assertNull(pI2.getImageByteArray());
        byte[] image = {1, 2, 3};
        pI1.setImageByteArray(image);
        assertArrayEquals(image, pI1.getImageByteArray());
    }

    @Test
    public void testGetLocationLat() {
        assertEquals(Double.POSITIVE_INFINITY, pI1.getLocationLat(), 0.0);
        assertEquals(45.12, pI2.getLocationLat(), 0.0);
    }

    @Test
    public void testGetLocationLong() {
        assertEquals(Double.POSITIVE_INFINITY, pI1.getLocationLong(), 0.0);
        assertEquals(72.34, pI2.getLocationLong(), 0.0);
    }

    @Test
    public void testGetCityName() {
        assertNull(pI1.getCityName());
        assertEquals("Montreal", pI2.getCityName());
    }

    @Test
    public void testGetPokemon() {
        assertEquals(pokemon, pI1.getPokemon());
        assertEquals(pokemon, pI2.getPokemon());
    }

    @Test
    public void testSetImageByteArray() {
        assertNull(pI1.getImageByteArray());
        byte[] image = {1, 2, 3};
        pI1.setImageByteArray(image);
        assertArrayEquals(image, pI1.getImageByteArray());
        pI1.setImageByteArray(null);
        assertNull(pI1.getImageByteArray());
    }

    @Test
    public void testSetLocation() {
        assertEquals(Double.POSITIVE_INFINITY, pI1.getLocationLat(), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, pI1.getLocationLong(), 0.0);
        pI1.setLocation(12.34, 56.78);
        assertEquals(12.34, pI1.getLocationLat(), 0.0);
        assertEquals(56.78, pI1.getLocationLong(), 0.0);
    }

    @Test
    public void testSetCityName() {
        assertNull(pI1.getCityName());
        pI1.setCityName("Toronto");
        assertEquals("Toronto", pI1.getCityName());
    }

    @Test
    public void testSetPokemon() {
        assertEquals(pokemon, pI1.getPokemon());
        Pokemon newPokemon = new Pokemon("Bulbasaur");
        pI1.setPokemon(newPokemon);
        assertEquals(newPokemon, pI1.getPokemon());
    }
}
