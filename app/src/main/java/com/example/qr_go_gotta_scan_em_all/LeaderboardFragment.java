package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class LeaderboardFragment extends Fragment {

    private ListView leaderboard_list_view;
    private LeaderboardArrayAdapter adapter;
    private Database db;

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

    /**

     Called after the view has been created. Populates the ListView with data from the database.

     @param view The View returned by onCreateView.

     @param savedInstanceState The Bundle containing any saved state information.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the database and the adapter
        db = new Database(getContext());
        ArrayList<Player> data = new ArrayList<>();
        adapter = new LeaderboardArrayAdapter(getContext(), data);
        leaderboard_list_view.setAdapter(adapter);

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

                                // TODO: Add player-owned QR codes to player object

                                // Add player to data array
                                data.add(player);
                            }
                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();
                            Log.d("LEADERBOARD_FRAGMENT", "Cached get succeeded.");
                        } else {
                            Log.d("LEADERBOARD_FRAGMENT", "Cached get failed: ", task.getException());
                        }
                    }
                });
    }
}