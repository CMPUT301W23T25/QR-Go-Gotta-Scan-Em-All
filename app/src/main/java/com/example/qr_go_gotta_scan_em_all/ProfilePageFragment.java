package com.example.qr_go_gotta_scan_em_all;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePageFragment} factory method to
 * create an instance of this fragment.
 *
 */
public class ProfilePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    // The player object whose profile page is being displayed.
    private Player player;
    private FragmentManager fragmentManager;
    private ImageView playerSearchButton;

    private ImageView pokemonNearMeButton;

    private ImageView editProfilePage;

    private TextView emailTextView;
    private Database db;

    /**
     * 
     * Required empty public constructor for the fragment.
     */
    public ProfilePageFragment() {
        // Required empty public constructor
    }


    /**
     * 
     * Constructor that sets the player object passed as an argument.
     * 
     * @param player the player object whose profile page is being displayed
     */
    public ProfilePageFragment(Player player, FragmentManager fragmentManager) {
        this.player = player;
        this.fragmentManager = fragmentManager;
    }

    /**
     * 
     * /**
     * 
     * Called when the fragment is created.
     * 
     * @param savedInstanceState the saved instance state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    /**
     * 
     * Called when the fragment's view is created.
     * 
     * @param inflater           the layout inflater
     * @param container          the view group container
     * @param savedInstanceState the saved instance state of the fragment
     * @return the inflated view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_page, container, false);
    }

    /**
     * Called when the fragment's view has been created and is ready to be modified.
     * 
     * @param view               the fragment's view
     * @param savedInstanceState the saved instance state of the fragment
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView usernameVal = view.findViewById(R.id.username_val);
        pokemonNearMeButton = view.findViewById(R.id.pokemon_near_me_button);
        editProfilePage = view.findViewById(R.id.edit_profile_info);
        emailTextView = view.findViewById(R.id.email_val);

        if(!player.getEmailAddress().trim().equals("")){
            emailTextView.setText(player.getEmailAddress());
        }
        System.out.println(player.getUserName());
        usernameVal.setText(player.getUserName());
        db = new Database(getContext().getApplicationContext());
        // or (ImageView) view.findViewById(R.id.foo);

        playerSearchButton = view.findViewById(R.id.search_button);

        // Set the onClickListener for the player search button.
        playerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new PlayerSearchFragment and display it.
                PlayerSearchFragment playerSearchFragment = new PlayerSearchFragment(fragmentManager);

                // Replace the current fragment with player search fragment.
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, playerSearchFragment);

                // Add the transaction to the back stack so the user can navigate back.
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        pokemonNearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                // Replace whatever is in the fragment_container view with this fragment
                transaction.replace(R.id.container, new GlobalQRSearchFragment(player), null);
                transaction.commit();
            }
        });

        editProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileDialog();
            }
        });
    }

    /**
     * Shows the custom dialog box allowing the user to modify the email.
     */
    private void editProfileDialog() {
        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());


        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.edit_profile);
        ImageView closeButton = dialog.findViewById(R.id.close_button);
        ImageView saveButton = dialog.findViewById(R.id.save_button);
        EditText emailTxt = dialog.findViewById(R.id.email_textview);
        dialog.show();


        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                String emailTxtString = emailTxt.getText().toString();
                updateEmailAddress(emailTxtString);
                player.setEmailAddress(emailTxtString);
                if(!player.getEmailAddress().trim().equals("")){
                    emailTextView.setText(player.getEmailAddress());
                } else{
                    emailTextView.setText("");
                }
                dialog.dismiss();
            }
        });
    }

    private void updateEmailAddress(String email){
        String playerId = player.getUserId();


        DocumentReference playerRef = db.getPlayerCol().document(playerId);

        playerRef.update("email", email)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Email updated successfully"))
                .addOnFailureListener(e -> switchToNetworkFail());
    }

    private void switchToNetworkFail() {
        startActivity(new Intent(getActivity(), ConnectionErrorActivity.class));
        getActivity().finish();
    }
}