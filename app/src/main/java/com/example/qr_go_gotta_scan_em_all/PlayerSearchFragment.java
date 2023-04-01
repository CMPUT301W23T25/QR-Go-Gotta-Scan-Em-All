package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class PlayerSearchFragment extends Fragment {
    private FragmentManager fragmentManager;
    private ImageView playerSearchButton;
    private EditText playerSearchBar;
    private PlayerSearchAdapter adapter;
    private ArrayList<Player> data;
    private ListView playerSearchList;

    public PlayerSearchFragment() {
        // Required empty public constructor
    }

    public PlayerSearchFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_search, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the views
        playerSearchButton = view.findViewById(R.id.search_button_icon);
        playerSearchBar = view.findViewById(R.id.player_search_input);
        playerSearchList = view.findViewById(R.id.player_searches);

        // Set the adapter
        data = new ArrayList<Player>();
        adapter = new PlayerSearchAdapter(getContext(), data);
        playerSearchList.setAdapter(adapter);

        // Set the click listener for the search button
        playerSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        // Set the text change listener
        playerSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}