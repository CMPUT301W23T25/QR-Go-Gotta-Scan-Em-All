package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    ArrayAdapter<Comment> commentArrayAdapter;
    List<String> owners = new ArrayList<>();


    ListView lW;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        db = new Database(getActivity().getApplicationContext());
        TextView visual = view.findViewById(R.id.visual_reper);
        lW = view.findViewById(R.id.comments_list);
        visual.setText(pk.visualReper());
        commentArrayAdapter = new CommentsArrayAdapter(getActivity().getApplicationContext(),comments);
        Button cmtBtn = view.findViewById(R.id.add_comment_btn);
        lW.setAdapter(commentArrayAdapter);
        Button ownBtn = view.findViewById(R.id.owners_btn);


        try{
            getCommentsFromDB();
            getOwners();
        }catch (NullPointerException e){
            System.out.println("loading db");
        }
        commentArrayAdapter.notifyDataSetChanged();

        cmtBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                if(checkPokemonExistsOwnedPlayer(pk)){
                    showDialogueBox();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot comment on Pokemon you don't own.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ownBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                ownerDialog();


            }
        });
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
                                        Player playerObject = new Player(userId);

                                        db.getPlayerCol()
                                                .document(userId)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                String username = null;
                                                                while(username == null){
                                                                    username = document.getString("username");
                                                                    commentArrayAdapter.notifyDataSetChanged();

                                                                }
                                                                // Add username to comment
                                                                playerObject.setUserName(username);
                                                            } else {
                                                                Log.d(TAG, "No such document");
                                                            }
                                                        } else {
                                                            Log.d(TAG, "get failed with ", task.getException());
                                                        }
                                                    }
                                                });
                                            // Add the comment to the array of comments
                                            Comment comment = new Comment(playerObject,(String)c.get("text"));
                                            commentArrayAdapter.add(comment);

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

    void showDialogueBox(){


        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_comment_dialog);
        Button commentButton = dialog.findViewById(R.id.comment_btn);
        Button closeButton = dialog.findViewById(R.id.close_btn);
        TextView commentTxt = dialog.findViewById(R.id.comment_textbox);

        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                // Switch the fragment to the specific QR code's page
                // Make a new comment
                Comment cmt = new Comment(p,commentTxt.getText().toString());
                addComment(cmt);
                commentArrayAdapter.notifyDataSetChanged();
                // add the comment to the database
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

    void ownerDialog(){


        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.owners_dialog);
        ListView ownersLW = dialog.findViewById(R.id.owners_list);
        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, owners);
        ownersLW.setAdapter(a);
        Button closeButton = dialog.findViewById(R.id.close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                dialog.dismiss();
            }
        });



        // show the dialog box
        dialog.show();

    }

    private void addComment(Comment c){
        // Add the comment to the Pokemon's comments array
        Map<String,String> m = new HashMap<>();
        m.put("user_id",c.getPlayer().getUserId());
        m.put("text",c.getText());
        db.getPokemonCol()
                .document(pk.getID())
                .update("comments", FieldValue.arrayUnion(m))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Comment added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding comment", e);
                    }
                });
        commentArrayAdapter.add(c);
    }

    private boolean checkPokemonExistsOwnedPlayer(Pokemon px){
        for (PokemonInformation pI: p.getPokemonArray()){
            if(Objects.equals(pI.getPokemon().getID(), px.getID())){
                System.out.println(pI.getPokemon().getID());
                return true;
            }
        }
        return false;
    }

    private void getOwners(){
        // Query the player collection
        db.getPlayerCol().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot playerDoc : task.getResult()) {
                        // Get the pokemon_owned array from the current player document
                        List<Map<String, String>> pokemonOwned = (List<Map<String, String>>) playerDoc.get("pokemon_owned");

                        // Check if the current player owns the given pokemon
                        for (Map<String, String> pokemon : pokemonOwned) {
                            if (pokemon.get("ID").equals(pk.getID())) {
                                // Add the player's username to the list of owners
                                owners.add(playerDoc.getString("username"));
                                break; // stop searching through the pokemon_owned array for this player
                            }
                        }
                    }

                    // Do something with the list of owners (e.g. update UI)
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


}