package com.example.qr_go_gotta_scan_em_all;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OtherProfileActivity extends AppCompatActivity {
    private ListView pokemonOwnedListView;
    private ImageView backButton;
    private TextView usernameTextView;
    private TextView highscoreTextView;
    private TextView totalscoreTextView;
    private TextView emailTextView;
    private PokemonArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        // Get the views from the layout
        pokemonOwnedListView = findViewById(R.id.pokemon_owned_list_view);
        backButton = findViewById(R.id.other_profile_back_button);
        usernameTextView = findViewById(R.id.usernameView_show);
        highscoreTextView = findViewById(R.id.highScoreView_show);
        totalscoreTextView = findViewById(R.id.totalScoreView_show);

        // Get the player data from the intent
        Player player = (Player) getIntent().getSerializableExtra("player");

        // Set the views to the player data
        usernameTextView.setText(player.getUserName());
        highscoreTextView.setText(String.valueOf(player.getBestPokemon().getScore()));
        totalscoreTextView.setText(String.valueOf(player.getTotalScore()));

        // Set the adapter for the list view
        adapter = new PokemonArrayAdapter(this, player.getPokemonArray());
        pokemonOwnedListView.setAdapter(adapter);

        // Set the back button to finish the activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}