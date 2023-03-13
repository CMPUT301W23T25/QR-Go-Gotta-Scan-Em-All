package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class LeaderboardFragment extends Fragment {

    private ListView leaderboard_list_view;
    private LeaderboardArrayAdapter adapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        leaderboard_list_view = view.findViewById(R.id.leaderboard_list_view);
        return view;
    }
    /**

     Called after the fragment's view has been created and makes sure that the ListView adapter is set with the

     appropriate data. In this case, the data is a mock list of Players, but should be replaced with actual data from

     the database.

     @param view - the view hierarchy returned by onCreateView

     @param savedInstanceState - saved state information about the fragment, can be null
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: Replace with data from Database
        ArrayList<Player> data = new ArrayList<>();
        Map<String, String> mockPlayer1 = new HashMap<String, String>() {{
            put("username", "User1");
            put("totalScore", "10000");
        }};
        Map<String, String> mockPlayer2 = new HashMap<String, String>() {{
            put("username", "User2");
            put("totalScore", "9000");
        }};
        data.add(new Player(null, "User1", null, mockPlayer1, null, null));
        data.add(new Player(null, "User2", null, mockPlayer2, null, null));


        adapter = new LeaderboardArrayAdapter(getContext(), data);
        leaderboard_list_view.setAdapter(adapter);
    }
}