package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRMoreInfoFragment} factory method to
 * create an instance of this fragment.
 */
public class QRMoreInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Player p;
    private Database db;
    private Pokemon pk;
    private ArrayList<Comment> comments;

    // TODO: Rename and change types of parameters


    public QRMoreInfoFragment() {
        // Required empty public constructor
    }

    public QRMoreInfoFragment(Player p, Pokemon pk) {
        // Needed for checking if the pokemon the player is commenting on is actually in the array or not.
        this.p = p;
        this.pk = pk;
        this.comments = new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_more_info, container, false);
    }

    private void getCommentsFromDB(){
//        db.getPokemonCol().document(pk.getID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//
//                        List<Map<String, String>> myArray = (List<Map<String, String>>) document.get("comments");
//
//
//                        // Store the array of hashmaps of the pokemons  { pokemonID: …, location: …, photo: …, }.
//
//                    }
//
//                } else {
//                    startActivity(new Intent(((MainActivity)getActivity()), ConnectionErrorActivity.class));
//                }
//            }
//        });

// Query Pokemon collection to get comments for the specified Pokemon
        db.getPokemonCol()
                .document(pk.getID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Get comments field from Pokemon document
                                List<Map<String, String>> commentsM = (List<Map<String, String>>) document.get("comments");
                                if (comments != null) {
                                    // Query players collection to get usernames
                                    for (Map<String, String> c : commentsM) {
                                        String userId = c.get("user_id");
                                        db.getPlayerCol()
                                                .document(userId)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                String username = document.getString("user_name");
                                                                // Add username to comment
                                                                c.put("user_name", username);
                                                            } else {
                                                                Log.d(TAG, "No such document");
                                                            }
                                                        } else {
                                                            Log.d(TAG, "get failed with ", task.getException());
                                                        }
                                                    }
                                                });
                                        // Add the comment to the array of comments
                                        Comment comment = new Comment(new Player((String)c.get("user_id"),(String)c.get("user_name")),(String)c.get("text"));
                                        comments.add(comment);
                                    }
                                    // Do something with comments array after all queries complete
                                    // For example, update UI with comments and usernames
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


    }

//    private ArrayList<PokemonInformation> convertRawDataToPInfo(List<Map<String,Object>> a){
//        // Make a new array
//
//        ArrayList<Comment> pIList = new ArrayList<>();
//        for (Map<String,Object> m:a){
////            PokemonInformation pI = new PokemonInformation();
//            Comment c = new Comment(new Player(),);
//            Pokemon p= new Pokemon();
//            p.setID((String)m.get("ID"));
//        }
//
//        return pIList;
//
//    }

    /**
     *
     *
     * This method retrieves the player data from the database based on their login
     * information,
     * creates a Player object, and sets the "isRegistered" flag to true if the
     * player is found in
     * the database. It also switches to the main activity and passes the player
     * object as an extra
     * to the intent.
     */

//    private void getPlayerData(String userID, Player player) {
//
//
//        db.getPlayerCol().document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        player = new Player((String)document.get("username"), document.getId());
//                        List<Map<String, Object>> myArray = (List<Map<String, Object>>) document.get("pokemon_owned");
//                        player.setPokemonArray(convertRawDataToPInfo(myArray));
//                        //
//                        isRegistered = true;
//                        System.out.println("registered");
//                        intent.putExtra("player",player);
//
//                        // Store the array of hashmaps of the pokemons  { pokemonID: …, location: …, photo: …, }.
//
//                        switchToMainActivity();
//
//                    } else {
//                        System.out.println("tre");
//                        isRegistered = false;
//                    }
//
//                } else {
//                    switchToNetworkFail();
//                }
//            }
//        });
//
//    }

}