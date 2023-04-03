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

 A fragment representing the more info screen for a specific Pokemon with its comments and owners.
 Activities that contain this fragment must implement the {@link QRMoreInfoFragment} interface
 to handle interaction events.
 create an instance of this fragment.
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
    private ArrayAdapter<Comment> commentArrayAdapter;
    private List<String> owners = new ArrayList<>();
    private ImageView cmtBtn;
    private ImageView ownBtn;
    private ListView lW;
    private TextView name;
    private ImageView closeButton;

    // TODO: Rename and change types of parameters


    public QRMoreInfoFragment() {
        // Required empty public constructor
    }
    /**
     * Constructor that initializes the fragment with the provided player and Pokemon instances.
     *
     * @param p  The Player object
     * @param pk The Pokemon object
     */
    public QRMoreInfoFragment(Player p, Pokemon pk) {
        // Needed for checking if the pokemon the player is commenting on is actually in the array or not.
        this.p = p;
        this.pk = pk;
        this.comments = new ArrayList<>();
    }

    /**
     * Called to do the initial creation of a fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Called after onCreateView, allows to set up any additional views and event listeners.
     *
     * @param view               The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        db = new Database(getActivity().getApplicationContext());
        TextView visual = view.findViewById(R.id.visual_reper);
        lW = view.findViewById(R.id.comments_list);
        visual.setText(pk.visualReper());
        name = view.findViewById(R.id.pokemon_name);
        name.setText(pk.getName());
        commentArrayAdapter = new CommentsArrayAdapter(getActivity().getApplicationContext(),comments);
        cmtBtn = view.findViewById(R.id.add_comment_btn);
        lW.setAdapter(commentArrayAdapter);
        ownBtn = view.findViewById(R.id.owners_btn);


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
    /**
     * Called when the view hierarchy associated with the fragment is being created.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_info_new, container, false);
    }

    /**
     * Retrieves the comments for the current Pokemon from the database and adds them to the comments list.
     */
    private void getCommentsFromDB(){
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
                                                            switchToNetworkFail();
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
                            switchToNetworkFail();
                        }
                    }
                });




    }
    /**
     * Shows the custom dialog box for adding a comment to the Pokemon.
     */
    private void showDialogueBox(){


        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_comment_dialog);
        ImageView commentButton = dialog.findViewById(R.id.comment_btn);
        ImageView closeButton = dialog.findViewById(R.id.close_btn);
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
    /**
     * Shows the custom dialog box displaying the list of owners of the Pokemon.
     */
    private void ownerDialog(){
        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.owners_dialog);
        ListView ownersLW = dialog.findViewById(R.id.owners_list);
        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, owners);
        ownersLW.setAdapter(a);
        closeButton = dialog.findViewById(R.id.close_btn);

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
     * Adds the specified comment to the Pokemon's comments array and updates the database.
     *
     * @param c The Comment object to be added.
     */
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
    /**
     * Checks if the specified Pokemon exists in the player's owned Pokemon list.
     *
     * @param px The Pokemon object to be checked.
     * @return True if the player owns the Pokemon, otherwise false.
     */
    private boolean checkPokemonExistsOwnedPlayer(Pokemon px){
        for (PokemonInformation pI: p.getPokemonArray()){
            if(Objects.equals(pI.getPokemon().getID(), px.getID())){
                System.out.println(pI.getPokemon().getID());
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves the list of owners for the current Pokemon from the database and adds them to the owners list.
     */
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
                    switchToNetworkFail();
                }
            }
        });
    }
    /**
     * Switch to the ConnectionErrorActivity in case of network failure.
     */
    private void switchToNetworkFail() {
        startActivity(new Intent(getActivity(), ConnectionErrorActivity.class));
        getActivity().finish();
    }


}