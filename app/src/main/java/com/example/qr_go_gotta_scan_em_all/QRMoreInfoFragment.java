package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // TODO: Rename and change types of parameters


    public QRMoreInfoFragment() {
        // Required empty public constructor
    }

    public QRMoreInfoFragment(Player p, Pokemon pk) {
        // Needed for checking if the pokemon the player is commenting on is actually in the array or not.
        this.p = p;
        this.pk = pk;
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

}