package com.example.qr_go_gotta_scan_em_all;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlobalQRSearchFragment} factory method to
 * create an instance of this fragment.
 */
public class GlobalQRSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private PokemonArrayAdapter adapter;

    private ArrayList<PokemonInformation> nearByPokemon;

    private ListView lW;

    private Database db;

    // TODO: Rename and change types of parameters
    private Player p;

    private String playerCity;
    private String playerCountry;

    private EditText locSearchTxt;
    private ImageView searchBttn;

    private String textSearch;


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
        textSearch = "";
        locSearchTxt = view.findViewById(R.id.location_search);
        searchBttn = view.findViewById(R.id.search_bttn);

        searchBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text entered into the search
                textSearch = locSearchTxt.getText().toString().toUpperCase(Locale.ROOT);
                // Make a query to search for results that match the city and country
            }
        });

    }

    private void getAllPokemonNearBy(){
        CollectionReference pokemonRef = db.getPokemonCol();

        // Retrieve all documents in the pokemon collection
        pokemonRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Iterate over each document in the collection
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the pokemon_location key for the current document
                    List<Map<String, Object>> locationArray = (List<Map<String, Object>>) document.getData().get("pokemon_locations");
                    // if the pokemon is close to the player add it on the map (do later)

                    for(Map<String, Object> m: locationArray){
                        if (textSearch == (String)m.get("country") || textSearch == (String)m.get("city")){
                            // add it to list
                            Pokemon tempP = new Pokemon();
                            tempP.setID(document.getId());
                        }
                    }


                }
            } else {
                System.out.println("Error getting documents: " + task.getException());
                switchToNetworkFail();
            }
        });

    }

    private void switchToNetworkFail() {
        startActivity(new Intent(getActivity(), ConnectionErrorActivity.class));
        getActivity().finish();
    }

    private void addPokemonToArray(PokemonInformation pI){
        for (PokemonInformation pI2:nearByPokemon){

        }
    }




}