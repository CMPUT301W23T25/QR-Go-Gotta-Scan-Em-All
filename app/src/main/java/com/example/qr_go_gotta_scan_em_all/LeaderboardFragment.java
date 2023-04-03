package com.example.qr_go_gotta_scan_em_all;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A fragment representing a list of Items.
 */
public class LeaderboardFragment extends Fragment {

    private ListView leaderboardListView;
    private LeaderboardArrayAdapter adapter;
    private Database db;
    private ImageView changeLeaderboardButton;
    private int state;
    private TextView leaderboardCriteriaText;
    private ConstraintLayout regionSearchLayout;
    private EditText regionSearchInput;
    private Button regionSearchButton;
    private String region;
    private ConstraintLayout rankEstimateLayout;
    private ArrayList<Player> data;
    private TextView rankEstimateText;
    private ArrayList<Double> scoresList;
    private Player currentPlayer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeaderboardFragment() {
    }

    public LeaderboardFragment(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**

     Inflates the layout for the fragment and initializes the ListView.
     @param inflater The LayoutInflater to inflate the layout.
     @param container The ViewGroup that the fragment is contained in.
     @param savedInstanceState The Bundle containing any saved state information.
     @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        leaderboardListView = view.findViewById(R.id.leaderboard_list_view);
        changeLeaderboardButton = view.findViewById(R.id.change_leaderboard_button);
        leaderboardCriteriaText = view.findViewById(R.id.leaderboard_criteria_text);
        regionSearchLayout = view.findViewById(R.id.region_select_layout);
        regionSearchInput = view.findViewById(R.id.city_search_edit_text);
        regionSearchButton = view.findViewById(R.id.city_search_button);
        rankEstimateLayout = view.findViewById(R.id.rank_estimate_layout);
        rankEstimateText = view.findViewById(R.id.rank_estimate_text_view);

        return view;
    }

    /**

     Called after the view has been created. Populates the ListView with data from the database.

     @param view The View returned by onCreateView.

     @param savedInstanceState The Bundle containing any saved state information.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state = 0;
        region = "";

        // Initialize the database and the adapter
        db = new Database(getContext());
        data = new ArrayList<>();
        scoresList = new ArrayList<>();
        adapter = new LeaderboardArrayAdapter(getContext(), data);
        leaderboardListView.setAdapter(adapter);

        // Initialize the leaderboard
        updateLeaderboard();

        // Set on click listener for the region search button
        regionSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the region name from the input
                region = regionSearchInput.getText().toString();

                // Notify the adapter that the data has changed
                adapter.setRegion(region);
                adapter.notifyDataSetChanged();
            }
        });

        // Set on click listener for the change leaderboard button
        changeLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = (state + 1) % 4;
                updateLeaderboard();
            }
        });

        // Get all the player collection from the database
        db.getPlayerCol()
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // For each player in the database, add it to the data array
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userName = (String) document.get("username");
                                String userId = document.getId();

                                // Create player object
                                Player player = new Player(userName, userId);

                                // Add player-owned QR codes to player object
                                for (Map pokemonMap : (ArrayList<Map>) Objects.requireNonNull(document.get("pokemon_owned"))) {
                                    // create pokemon object
                                    Pokemon pokemon = new Pokemon();

                                    // set pokemon attributes
                                    pokemon.setID((String) pokemonMap.get("ID"));

                                    // convert pokemon to pokemonInformation object
                                    PokemonInformation pokemonInfo = new PokemonInformation(pokemon);
                                    pokemonInfo.setCityName((String) pokemonMap.get("city"));

                                    // add pokemonInfo to player
                                    player.addPokemon(pokemonInfo);

                                    // add pokemon score to scoresList
                                    scoresList.add(pokemon.getScore());
                                }

                                // Add player to data array
                                data.add(player);

                                // Update currentPlayer
                                if (currentPlayer.getUserId().equals(player.getUserId())) {
                                    Log.d("LEADERBOARD_FRAGMENT", "Current player found: " + currentPlayer.getUserName());
                                    currentPlayer = player;
                                }
                            }

                            updateDataOrder();
                            Log.d("LEADERBOARD_FRAGMENT", "Cached get succeeded.");
                        } else {
                            Log.d("LEADERBOARD_FRAGMENT", "Cached get failed: ", task.getException());
                        }
                    }
                });

        // Set onItemClickListener for the ListView
        leaderboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the player that was clicked
                Player player = (Player) adapterView.getItemAtPosition(i);

                // Create an intent to open the OtherProfileActivity
                Intent intent = new Intent(getContext(), OtherProfileActivity.class);

                // Pass the player's data to the OtherProfileActivity
                intent.putExtra("player", player);

                // Start the OtherProfileActivity
                startActivity(intent);
            }
        });
    }

    private void updateLeaderboard() {
        // Hide the conditional layouts
        rankEstimateLayout.setVisibility(View.GONE);
        regionSearchLayout.setVisibility(View.GONE);

        // Change the leaderboard criteria text based on the state
        switch (state) {
            case 0:
                leaderboardCriteriaText.setText("Total Score");
                break;
            case 1:
                leaderboardCriteriaText.setText("Pokemons Caught");
                break;
            case 2:
                leaderboardCriteriaText.setText("Global High");
                rankEstimateLayout.setVisibility(View.VISIBLE);
                updateRankEstimate();
                break;
            case 3:
                leaderboardCriteriaText.setText("Regional High");
                regionSearchLayout.setVisibility(View.VISIBLE);
                break;
        }

        updateDataOrder();

        // Notify the adapter that the data has changed
        adapter.setState(state);
        adapter.notifyDataSetChanged();
    }

    private void updateDataOrder() {
        // Sort the data array based on the state
        data.sort((player1, player2) -> {
            switch (state) {
                case 0:
                    return (int) Math.round(player2.getTotalScore() - player1.getTotalScore());
                case 1:
                    return player2.getPokemonArray().size() - player1.getPokemonArray().size();
                case 2:
                    return (int) Math.round(player2.getBestPokemon().getScore() - player1.getBestPokemon().getScore());
                case 3:
                    double score1 = 0.0;
                    double score2 = 0.0;

                    if (player1.getBestPokemonAtCity(region) != null)
                        score1 = player1.getBestPokemonAtCity(region).getScore();
                    if (player2.getBestPokemonAtCity(region) != null)
                        score2 = player2.getBestPokemonAtCity(region).getScore();

                    return (int) Math.round(score2 - score1);
                default:
                    return 0;
            }
        });

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    private void updateRankEstimate() {
        // Get the current player's score
        Double score = currentPlayer.getBestPokemon().getScore();

        // Remove duplicate scores from the scoresList
        Set<Double> set = new HashSet<>(scoresList);
        scoresList.clear();
        scoresList.addAll(set);

        // Sort the scoresList in descending order
        scoresList.sort((score1, score2) -> (int) Math.round(score2 - score1));

        // Get the rank of the current player
        int rank = scoresList.indexOf(score) + 1;

        // Update the rank estimate text
        rankEstimateText.setText(String.valueOf(rank));
    }
}