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

public class PlayerSearchAdapter extends ArrayAdapter<Player> {
    private TextView usernameTextView;

    public PlayerSearchAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_player_search_list, parent, false);
        } else {
            view = convertView;
        }

        // Get the player at the specified position
        Player player = getItem(position);

        // Get the views
        usernameTextView = view.findViewById(R.id.username_search);

        // Set the text
        usernameTextView.setText(player.getUserName());

        return view;
    }
}
