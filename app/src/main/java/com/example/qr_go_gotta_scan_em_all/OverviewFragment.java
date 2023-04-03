package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import static java.lang.Double.parseDouble;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    ImageView deleteButton;

    ImageView commentButton;
    ImageView closeButton;

    private Player player;

    private FragmentManager fragmentManager;
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
    /** This is where you should initialize your UI and start any operations that
     * need to be done when the activity is created.
     *  @param savedInstanceState This fragment's previously saved state, if any
    */

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
     * Called immediately after onCreateView() has returned a non-null View,
     * and allows the fragment to perform further initialization of that View.
     * Sets the text of the username field to the current user's username, and
     * populates the list view with the user's Pokémon.
     *
     * @param view The View object returned by onCreateView()
     * @param savedInstanceState This fragment's previously saved state, if any
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        db = new Database(getActivity().getApplicationContext());
        usernameVal = view.findViewById(R.id.usernameView_show);
        totalScore = view.findViewById(R.id.toal_score_view);
        itemsScanned = view.findViewById(R.id.item_scanned);
        highestScore = view.findViewById(R.id.highest_score_qr);
        lowestScore = view.findViewById(R.id.lowest_score_qr);
        fragmentManager = getParentFragmentManager();

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

//            lW.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
///*                new DeleteVisit().show(getSupportFragmentManager(),"Delete Visit");
//                return true;*/
//                    // add delete confirmaton
//                    deleteFromPlayerList(position);
//                    return true;
//                }
//            });

            lW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showDialogueBox(position, view);
                }
            });
            lW.setAdapter(pokemonArrayAdapter);
            pokemonArrayAdapter.notifyDataSetChanged();
        }catch (NullPointerException e){
            System.out.println("loading db");
        }



        // or  (ImageView) view.findViewById(R.id.foo);
    }
    /**
     * Displays a dialog box to show details of the clicked Pokémon, and allows the user to delete
     * the Pokémon or view additional information about it.
     *
     * @param position The index of the clicked Pokémon in the ListView
     * @param view The view object representing the clicked Pokémon
     */
    void showDialogueBox(int position, View view){


        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.comment_delete_dialog);
        deleteButton = dialog.findViewById(R.id.delete_btn);
        commentButton = dialog.findViewById(R.id.comment_btn);
        closeButton = dialog.findViewById(R.id.close_btn);
        TextView hashTextView = dialog.findViewById(R.id.visual_reper);
        hashTextView.setText(player.getPokemonArray().get(position).getPokemon().visualReper());
        try{
            if (player.getPokemonArray().get(position).getImageByteArray() != null){
                System.out.println(player.getPokemonArray().get(position).getDecodedImage());
                Bitmap bmp = player.getPokemonArray().get(position).getDecodedImage();

                ImageView image = dialog.findViewById(R.id.image_view_display);

                image.setImageBitmap(bmp);
            }
        } catch (NullPointerException e){
            System.out.println(e);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                totalScore.setText("Total Score: " + Double.toString(player.getTotalScore()));
                highestScore.setText("Highest Scoring: " + player.getBestPokemon());
                lowestScore.setText("Lowest Scoring: " + getWorstPokemon());
                itemsScanned.setText("QR Scanned: " + Integer.toString(player.getPokemonArray().size()));
                deleteFromPlayerList(position);
                dialog.dismiss();
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                // Switch the fragment to the specific QR code's page
                goToOverview(position);
                dialog.dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                dialog.dismiss();
            }
        });



        // show the dialog box
        dialog.show();

        }

        /**
         * Deletes a Pokémon from the player's list of caught Pokémon and updates the UI.
         *
         * @param pos The index of the Pokémon to delete
         */
        private void deleteFromPlayerList(int pos){

            PokemonInformation pI = player.getPokemonArray().get(pos);
            String valueToDelete = pI.getPokemon().getID();
            pokemonArrayAdapter.remove(pI);

// Retrieve the document containing the array field that needs to be modified
            DocumentReference docRef = db.getPlayerCol().document(player.getUserId());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    List<Map<String,String>> myArray = (List<Map<String,String>>)documentSnapshot.get("pokemon_owned");
                    Map<String,String> valRemove = null;
                    // Modify the array by removing the element with the specified value

                    // Iterate through the array's maps until the map with the appropriate ID is found
                    for(Map<String,String> m: myArray){
                        if(Objects.equals((String) m.get("ID"), valueToDelete)){
                            valRemove = m;
                        }

                    }

                    myArray.remove(valRemove);


                    // Update the document with the modified array
                    docRef.update("pokemon_owned", myArray)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Document updated successfully");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                    switchToNetworkFail();
                                }
                            });
                }
            });
    }
    /**
     * Transitions to the QRMoreInfoFragment to display more information about the selected Pokémon.
     *
     * @param position The index of the selected Pokémon in the ListView
     */
    private void goToOverview(int position) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.container, new QRMoreInfoFragment(player,player.getPokemonArray().get(position).getPokemon()), null);
        transaction.commit();
    }

    private String getWorstPokemon() {
        double minScore = Double.POSITIVE_INFINITY;
        String lowestScoringQRName = "";
        try {
            System.out.println(player.getUserName());
            usernameVal.setText(player.getUserName());

            for (int i = 0; i < player.getPokemonArray().size(); i++) {
                double pScore = player.getPokemonArray().get(i).getPokemon().getScore();
                if (pScore < minScore) {
                    minScore = pScore;
                    lowestScoringQRName = player.getPokemonArray().get(i).getPokemon().getName();
                }
            }
        } catch (Exception e) {

        }
        return lowestScoringQRName;
    }

    private void switchToNetworkFail() {
        startActivity(new Intent(getActivity(), ConnectionErrorActivity.class));
        getActivity().finish();
    }

}