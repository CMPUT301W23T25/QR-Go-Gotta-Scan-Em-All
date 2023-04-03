package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlobalQRSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlobalQRSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private PokemonArrayAdapter adapter;

    private ArrayList<PokemonInformation> nearByPokemon;

    private ListView lW;

    private Database db;

    // TODO: Rename and change types of parameters
    private Player p;


    private ArrayList<Pokemon> pokemonNearMeList;

    public GlobalQRSearchFragment() {
        // Required empty public constructor
    }

    public GlobalQRSearchFragment(Player p) {
        this.p = p;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_q_r_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        db = new Database(getActivity().getApplicationContext());
        lW = view.findViewById(R.id.listview_pokemon);



    }

    private void getAllPokemonNearBy(){

    }




}