package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * 
 * An ArrayAdapter that is used to display the leaderboard of players in a
 * ListView.
 * Each item in the ListView corresponds to a Player object and contains the
 * player's
 * username and total score.
 */
public class LeaderboardArrayAdapter extends ArrayAdapter<Player> {
    private int state;
    private String region;

    /**
     * Constructor for the LeaderboardArrayAdapter class.
     * 
     * @param context The context in which the adapter is used.
     * @param objects The ArrayList of Player objects to be displayed in the
     *                ListView.
     */
    /**
     * Constructor for the LeaderboardArrayAdapter class.
     *
     * @param context The context of the current activity.
     * @param objects An ArrayList of Player objects to display in the list.
     */
    public LeaderboardArrayAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
        state = 0;
    }

    /**
     * Get the View for the list item at the specified position in the list.
     *
     * @param position    The position of the item in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent view group that the list item view will be
     *                    attached to.
     * @return The new view for the list item at the specified position.
     */
    /**
     * Gets the View for each item in the ListView.
     * 
     * @param position    The position of the item in the ListView.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent view group of the item.
     * @return The View for the item at the specified position in the ListView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_leaderboard_list, parent, false);
        } else {
            view = convertView;
        }

        // Get current Player
        Player player = getItem(position);

        // Get views in the layout
        TextView usernameView = view.findViewById(R.id.leaderboard_username);
        TextView valueView = view.findViewById(R.id.leaderboard_value);

        // Bind player data to views in the layout based on the state
        usernameView.setText(player.getUserName());
        usernameView.setVisibility(View.VISIBLE);
        valueView.setVisibility(View.VISIBLE);

        switch (state) {
            case 0:
                valueView.setText(String.valueOf(player.getTotalScore()));
                break;
            case 1:
                valueView.setText(String.valueOf(player.getPokemonArray().size()));
                break;
            case 2:
                valueView.setText(String.valueOf(player.getBestPokemon().getScore()));
                break;
            case 3:
                if (region != null) {
                    if (player.getBestPokemonAtCity(region) != null) {
                        valueView.setText(String.valueOf(player.getBestPokemonAtCity(region).getScore()));
                    }
                    else {
                        valueView.setText("0.0");
                    }

                }
                else {
                    usernameView.setVisibility(View.GONE);
                    valueView.setVisibility(View.GONE);
                }
                break;
        }

        return view;
    }

    public void setState(int state) {
        this.state = state;
    }
    public void setRegion(String region) {
        this.region = region;
    }
}
