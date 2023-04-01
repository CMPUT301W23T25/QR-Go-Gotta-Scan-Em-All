package com.example.qr_go_gotta_scan_em_all;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeaderboardFragment() {
    }
    /**

     Creates the view hierarchy associated with the fragment.
     @param inflater - the LayoutInflater object that can be used to inflate any views in the fragment
     @param container - the parent view that the fragment UI should be attached to
     @param savedInstanceState - saved state information about the fragment, can be null
     @return - the inflated View object for the fragment UI
     */

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

        return view;
    }
    /**

     Called after the fragment's view has been created and makes sure that the ListView adapter is set with the

     appropriate data. In this case, the data is a mock list of Players, but should be replaced with actual data from

     the database.

     @param view - the view hierarchy returned by onCreateView

     @param savedInstanceState - saved state information about the fragment, can be null
     */

    /**

     Called after the view has been created. Populates the ListView with data from the database.

     @param view The View returned by onCreateView.

     @param savedInstanceState The Bundle containing any saved state information.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state = 0;

        // Initialize the database and the adapter
        db = new Database(getContext());
        ArrayList<Player> data = new ArrayList<>();
        adapter = new LeaderboardArrayAdapter(getContext(), data);
        leaderboardListView.setAdapter(adapter);

        // Set on click listener for the change leaderboard button
        changeLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = (state + 1) % 4;

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
                        break;
                    case 3:
                        leaderboardCriteriaText.setText("Regional High");
                        break;
                }
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

                                    // add pokemonInfo to player
                                    player.addPokemon(pokemonInfo);
                                }

                                // Add player to data array
                                data.add(player);
                            }

                            // Sort the data array by the total score of each player
                            data.sort((player1, player2) -> (int) Math.round(player2.getTotalScore() - player1.getTotalScore()));

                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();
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
}