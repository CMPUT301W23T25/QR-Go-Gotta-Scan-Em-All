package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;


/**
 * An ArrayAdapter {@link LeaderboardArrayAdapter} for displaying a list of Players on a leaderboard.
 */
public class LeaderboardArrayAdapter extends ArrayAdapter<Player> {
    /**
     * Constructor for the LeaderboardArrayAdapter class.
     *
     * @param context The context of the current activity.
     * @param objects An ArrayList of Player objects to display in the list.
     */
    public LeaderboardArrayAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
    }

    /**
     * Get the View for the list item at the specified position in the list.
     *
     * @param position The position of the item in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent view group that the list item view will be attached to.
     * @return The new view for the list item at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_leaderboard_list, parent,false);
        } else {
            view = convertView;
        }

        // Get current Player
        Player player = getItem(position);
        Map<String, String> leaderboardStats = player.getLeaderboardStats();

        // Bind player data to views in the layout
        TextView usernameView = view.findViewById(R.id.leaderboard_username);
        TextView totalScoreView = view.findViewById(R.id.leaderboard_score);

        usernameView.setText(leaderboardStats.get("username"));
        totalScoreView.setText(leaderboardStats.get("totalScore"));

        return view;
    }
}
