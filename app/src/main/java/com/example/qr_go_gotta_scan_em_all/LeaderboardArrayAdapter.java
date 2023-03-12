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

public class LeaderboardArrayAdapter extends ArrayAdapter<Player> {
    public LeaderboardArrayAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
    }

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
