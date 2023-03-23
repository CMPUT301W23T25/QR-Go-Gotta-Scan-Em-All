package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

/**

 An ArrayAdapter that is used to display the leaderboard of players in a ListView.
 Each item in the ListView corresponds to a Player object and contains the player's
 username and total score.
 */
public class LeaderboardArrayAdapter extends ArrayAdapter<Player> {
    /**
     * Constructor for the LeaderboardArrayAdapter class.
     * @param context The context in which the adapter is used.
     * @param objects The ArrayList of Player objects to be displayed in the ListView.
     */
    public LeaderboardArrayAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
    }

    /**
     * Gets the View for each item in the ListView.
     * @param position The position of the item in the ListView.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent view group of the item.
     * @return The View for the item at the specified position in the ListView.
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
//        Map<String, Object> leaderboardStats = player.getLeaderboardStats();

        // Bind player data to views in the layout
        TextView usernameView = view.findViewById(R.id.leaderboard_username);
        TextView totalScoreView = view.findViewById(R.id.leaderboard_score);

//        usernameView.setText((String) leaderboardStats.get("username"));
//        totalScoreView.setText(String.valueOf((Double) leaderboardStats.get("totalScore")));

        return view;
    }
}
