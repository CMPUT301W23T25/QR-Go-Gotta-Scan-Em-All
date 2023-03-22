package com.example.qr_go_gotta_scan_em_all;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerUnitTest {

    @Test
    public void testGetUserName() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        assertEquals(userName, player.getUserName());
    }

    @Test
    public void testGetUserId() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        assertEquals(userId, player.getUserId());
    }

    @Test
    public void testAddPokemon() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        Pokemon pokemon = new Pokemon("Pikachu");
        player.addPokemon(pokemon);
        assertEquals(pokemon, player.getPokemonArray().get(0));
    }

    @Test
    public void testRemovePokemon() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        Pokemon pokemon = new Pokemon("Pikachu");
        player.addPokemon(pokemon);
        player.removePokemon(pokemon);
        assertTrue(player.getPokemonArray().isEmpty());
    }

    @Test
    public void testGetLeaderboardStats() {
        String userName = "TestUser";
        String userId = "12345";
        Map<String, Object> leaderboardStats = new HashMap<>();
        leaderboardStats.put("userName", userName);
        leaderboardStats.put("highScore", 100.0);
        Player player = new Player(new ArrayList<>(), userName, userId, leaderboardStats, new ArrayList<>(), "");
        assertEquals(leaderboardStats, player.getLeaderboardStats());
    }

    @Test
    public void testRemovePokemonAtIndex() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Bulbasaur");
        player.addPokemon(pokemon1);
        player.addPokemon(pokemon2);
        player.removePokemonAtIndex(0);
        assertEquals(pokemon2, player.getPokemonArray().get(0));
    }

    @Test
    public void testGetPokemonAtIndex() {
        String userName = "TestUser";
        String userId = "12345";
        Player player = new Player(userName, userId);
        Pokemon pokemon1 = new Pokemon("Pikachu");
        Pokemon pokemon2 = new Pokemon("Bulbasaur");
        player.addPokemon(pokemon1);
        player.addPokemon(pokemon2);
        assertEquals(pokemon2, player.getPokemonAtIndex(1));
    }
}
