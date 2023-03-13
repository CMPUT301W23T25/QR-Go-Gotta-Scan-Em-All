package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PokemonArrayAdapter pokemonArrayAdapter;

    private Player player;
    public OverviewFragment() {
        // Required empty public constructor
    }


    /**

     Use this factory method to create a new instance of
     this fragment using the provided parameters.
     @param player A {@link Player} object representing the current user
     @return A new instance of fragment OverviewFragment.
     */
    public OverviewFragment (Player player){
        this.player =player;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
    /**

     Inflates the layout for this fragment.
     @param inflater The LayoutInflater object that can be used to inflate views in the fragment
     @param container The parent view that the fragment UI should be attached to
     @param savedInstanceState This fragment's previously saved state, if any
     @return The inflated View object for the fragment's UI, or null
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.overview_page, container, false);


    }
    /**

     Called immediately after onCreateView() has returned a non-null View,

     and allows the fragment to perform further initialization of that View.

     Sets the text of the username field to the current user's username, and

     populates the list view with the user's Pokémon.

     @param view The View object returned by onCreateView()

     @param savedInstanceState This fragment's previously saved state, if any
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView usernameVal = view.findViewById(R.id.usernameView_show);
        TextView totalScore = view.findViewById(R.id.toal_score_view);
        TextView itemsScanned = view.findViewById(R.id.item_scanned);
        TextView highestScore = view.findViewById(R.id.highest_score_qr);
        TextView lowestScore = view.findViewById(R.id.lowest_score_qr);
        ListView lW = view.findViewById(R.id.list_view);
        double totalScoreNum = 0.0;
        double minScore = Double.POSITIVE_INFINITY;
        double maxScore = 0.0;
        String lowestScoringQRName = "";
        String highestScoringQRName = "";
        try{
            System.out.println(player.getUserName());
            usernameVal.setText(player.getUserName());
            for (int i = 0; i < player.getPokemonArray().size(); i++){
                double pScore = player.getPokemonArray().get(i).getScore();
                totalScoreNum +=  pScore;
                if (pScore > maxScore){
                    maxScore = pScore;
                    highestScoringQRName = player.getUserName();
                }
                if (pScore < minScore){
                    minScore = pScore;
                    lowestScoringQRName = player.getUserName();
                }
            }

            totalScore.setText("Total Score: " + Double.toString(totalScoreNum));
            highestScore.setText("Total Score: " + highestScoringQRName);
            lowestScore.setText("Highest Scoring QR: " + lowestScoringQRName);
            itemsScanned.setText("Lowest Scoring QR: " + Integer.toString(player.getPokemonArray().size()));
            pokemonArrayAdapter = new PokemonArrayAdapter(getActivity().getApplicationContext(),player.getPokemonArray());
            lW.setAdapter(pokemonArrayAdapter);
            pokemonArrayAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            System.out.println("loading db");
        }

        // or  (ImageView) view.findViewById(R.id.foo);
    }
}