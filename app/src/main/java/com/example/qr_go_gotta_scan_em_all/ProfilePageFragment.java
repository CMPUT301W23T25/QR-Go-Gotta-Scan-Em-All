package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePageFragment} factory method to
 * create an instance of this fragment.
 */
public class ProfilePageFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Player player;

    /**

     Required empty public constructor for the fragment.
     */
    public ProfilePageFragment() {
        // Required empty public constructor
    }

    /**

     Constructor that sets the player object passed as an argument.
     @param player the player object whose profile page is being displayed
     */
    public ProfilePageFragment(Player player) {
        // Required empty public constructor
        this.player = player;
    }

    /**

     Called when the fragment is created.
     @param savedInstanceState the saved instance state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    /**

     Called when the fragment's view is created.
     @param inflater the layout inflater
     @param container the view group container
     @param savedInstanceState the saved instance state of the fragment
     @return the inflated view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_page, container, false);
    }

    /**

     Called when the fragment's view has been created and is ready to be modified.
     @param view the fragment's view
     @param savedInstanceState the saved instance state of the fragment
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView usernameVal = view.findViewById(R.id.username_val);
        System.out.println(player.getUserName());
        usernameVal.setText(player.getUserName());
        // or  (ImageView) view.findViewById(R.id.foo);
    }
}