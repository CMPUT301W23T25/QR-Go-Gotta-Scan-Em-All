package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TextView usernameVal;
    TextView totalScore;
    TextView itemsScanned;
    TextView highestScore;
    TextView lowestScore;
    ListView lW;

    Database db;

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
        db = new Database(getActivity().getApplicationContext());
        usernameVal = view.findViewById(R.id.usernameView_show);
        totalScore = view.findViewById(R.id.toal_score_view);
        itemsScanned = view.findViewById(R.id.item_scanned);
        highestScore = view.findViewById(R.id.highest_score_qr);
        lowestScore = view.findViewById(R.id.lowest_score_qr);
        lW = view.findViewById(R.id.list_view);
        double totalScoreNum = 0.0;
        double minScore = Double.POSITIVE_INFINITY;
        double maxScore = 0.0;
        String lowestScoringQRName = "";
        String highestScoringQRName = "";


        try{
            System.out.println(player.getUserName());
            usernameVal.setText(player.getUserName());

            for (int i = 0; i < player.getPokemonArray().size(); i++){
                double pScore = player.getPokemonArray().get(i).getPokemon().getScore();
                totalScoreNum +=  pScore;
                if (pScore > maxScore){
                    maxScore = pScore;
                    highestScoringQRName = player.getPokemonArray().get(i).getPokemon().getName();
                }
                if (pScore < minScore){
                    minScore = pScore;
                    lowestScoringQRName = player.getPokemonArray().get(i).getPokemon().getName();
                }
            }

            totalScore.setText("Total Score: " + Double.toString(totalScoreNum));
            highestScore.setText("Highest Scoring: " + highestScoringQRName);
            lowestScore.setText("Lowest Scoring: " + lowestScoringQRName);
            itemsScanned.setText("QR Scanned: " + Integer.toString(player.getPokemonArray().size()));
            pokemonArrayAdapter = new PokemonArrayAdapter(getActivity().getApplicationContext(),player.getPokemonArray());

            lW.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/*                new DeleteVisit().show(getSupportFragmentManager(),"Delete Visit");
                return true;*/
                    // add delete confirmaton
                    deleteFromPlayerList(position);
                    return true;
                }
            });
            lW.setAdapter(pokemonArrayAdapter);
            pokemonArrayAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            System.out.println("loading db");
        }



        // or  (ImageView) view.findViewById(R.id.foo);
    }

    private void deleteFromPlayerList(int pos){
        System.out.println(player.getPokemonArray().size());
        PokemonInformation pI = player.getPokemonAtIndex(pos);
        pokemonArrayAdapter.remove(pI);
        System.out.println(player.getPokemonArray().size());
        pokemonArrayAdapter.notifyDataSetChanged();

        DocumentReference playerRef = db.getPlayerCol().document(player.getUserId());
        // Add the Pokemon to the player's list of owned Pokemon
        ArrayList<HashMap<String,Object>> a = new ArrayList<HashMap<String,Object>>();
        for(PokemonInformation p:player.getPokemonArray()){
            if(p.getPokemon().getID() != pI.getPokemon().getID()){
                String byteArrayRaw = "";
                if (pI.getImageByteArray() != null){
                    byteArrayRaw = new String(pI.getImageByteArray(), StandardCharsets.UTF_8);
                }

                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("ID",p.getPokemon().getID());
                map.put("lat",p.getLocationLat());
                map.put("long",p.getLocationLong());
                map.put("image", byteArrayRaw);
                map.put("city",p.getCityName());
                a.add(map);
            }
        }

        playerRef.update("pokemon_owned", a)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle success, if needed
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure, if needed
                    }
                });
    }

}