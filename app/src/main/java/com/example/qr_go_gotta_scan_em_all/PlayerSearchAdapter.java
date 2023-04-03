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

/**
 * PlayerSearchAdapter class extends ArrayAdapter to provide a custom adapter for displaying a list of Player objects.
 * This adapter displays the username of each player in the list.
 */
public class PlayerSearchAdapter extends ArrayAdapter<Player> {
    private TextView usernameTextView;

    /**
     * Constructor for the PlayerSearchAdapter, initializes the adapter with the given context and list of Player objects.
     *
     * @param context The context in which the adapter is operating.
     * @param objects The list of Player objects to be displayed.
     */
    public PlayerSearchAdapter(@NonNull Context context, @NonNull ArrayList<Player> objects) {
        super(context, 0, objects);
    }

    /**
     * Returns the view for an item within the list.
     *
     * @param position    The position of the item in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent view group that this view will eventually be attached to.
     * @return The View for the specified position in the list.
     */
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
