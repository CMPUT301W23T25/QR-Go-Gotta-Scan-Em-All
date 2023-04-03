package com.example.qr_go_gotta_scan_em_all;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
/**
 * PlayerSearchFragment is a Fragment that allows users to search for other players.
 * It displays a search bar where users can input the player's username and see the search results in a list.
 */
public class PlayerSearchFragment extends Fragment {
    private FragmentManager fragmentManager;
    private ImageView playerSearchButton;
    private EditText playerSearchBar;
    private PlayerSearchAdapter adapter;
    private ArrayList<Player> data;
    private ListView playerSearchList;
    private Database db;

    public PlayerSearchFragment() {
        // Required empty public constructor
    }
    /**
     * Constructs a new PlayerSearchFragment with the provided FragmentManager.
     * @param fragmentManager FragmentManager to manage this fragment.
     */
    public PlayerSearchFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return View Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_search, container, false);

        return view;
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view.
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the database
        db = new Database(getContext());

        // Get the views
        playerSearchButton = view.findViewById(R.id.search_button_icon);
        playerSearchBar = view.findViewById(R.id.player_search_input);
        playerSearchList = view.findViewById(R.id.player_searches);

        // Set the adapter
        data = new ArrayList<Player>();
        adapter = new PlayerSearchAdapter(getContext(), data);
        playerSearchList.setAdapter(adapter);

        // Set the click listener for the search button
        playerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        // Show all players initially
        showSearchResults();

        // Set the text change listener
        playerSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showSearchResults();
            }
        });

        // Set the click listener for the list
        playerSearchList.setOnItemClickListener((parent, view1, position, id) -> {
            // Get the player
            Player player = (Player) adapter.getItem(position);

            // Create the intent
            Intent intent = new Intent(getContext(), OtherProfileActivity.class);

            // Put the player in the intent
            intent.putExtra("player", player);

            // Start the activity
            startActivity(intent);
        });
    }
    /**
     * Fetches the search results based on the text entered in the search bar and updates the ListView accordingly.
     */
    private void showSearchResults() {
        // Get the text from the search bar
        String text = playerSearchBar.getText().toString();

        // Get the players from the database
        db.getPlayerCol()
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Clear the data
                        data.clear();

                        if (task.isSuccessful()) {
                            // For each player in the database, add it to the data array only if it starts with the text in the search bar
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Get the player's username and id
                                String userName = (String) document.get("username");
                                String userId = document.getId();

                                // If the username starts with the text in the search bar, add it to the data array
                                if (userName.startsWith(text)) {
                                    Log.d("PLAYER_SEARCH", userName);

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
                                        if (pokemonMap.get("city") != null) {
                                            pokemonInfo.setCityName((String) pokemonMap.get("city"));
                                        }
                                        if (pokemonMap.get("country") != null) {
                                            pokemonInfo.setCountryName((String) pokemonMap.get("country"));
                                        }
                                        if (pokemonMap.get("lat") != null && pokemonMap.get("long") != null) {
                                            pokemonInfo.setLocationLat((double) pokemonMap.get("lat"));
                                            pokemonInfo.setLocationLong((double) pokemonMap.get("long"));
                                        }
                                        if (pokemonMap.get("image") != null) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                pokemonInfo.setImageByteArray(Base64.getDecoder().decode((String) pokemonMap.get("image")));
                                            }
                                        }

                                        // add pokemonInfo to player
                                        player.addPokemon(pokemonInfo);
                                    }

                                    // Add player to data array
                                    data.add(player);
                                }
                            }
                            // Notify the adapter
                            adapter.notifyDataSetChanged();
                            Log.d("PLAYER_SEARCH_FRAGMENT", "Cached get succeeded.");
                        } else {
                            Log.d("PLAYER_SEARCH_FRAGMENT", "Cached get failed: ", task.getException());
                        }
                    }
                });
    }
}