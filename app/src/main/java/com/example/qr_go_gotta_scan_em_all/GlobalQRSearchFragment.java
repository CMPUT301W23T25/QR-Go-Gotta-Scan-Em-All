package com.example.qr_go_gotta_scan_em_all;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Player p;

    private EditText locSearchTxt;
    private ImageView searchBttn;

    private String textSearch;

    private LocationHandler lH;

    private ImageView useCurrentLocBtn;


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
        lH = new LocationHandler(getActivity().getApplicationContext());
        textSearch = "";
        locSearchTxt = view.findViewById(R.id.location_search);
        searchBttn = view.findViewById(R.id.search_bttn);
        nearByPokemon = new ArrayList<>();
        useCurrentLocBtn = view.findViewById(R.id.use_current_location_btn);
        adapter = new PokemonArrayAdapter(getActivity().getApplicationContext(),nearByPokemon);
        Pair<String,String> myCityCountry = lH.getCityAndCountry(lH.getCurrentLocation());
        lW.setAdapter(adapter);

        searchBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                // get the text entered into the search
                textSearch = locSearchTxt.getText().toString().toUpperCase(Locale.ROOT);
                // Make a query to search for results that match the city and country
                getAllPokemonNearBy(textSearch,textSearch);
            }
        });

        useCurrentLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                // get the text entered into the search
                textSearch = locSearchTxt.getText().toString().toUpperCase(Locale.ROOT);
                // Make a query to search for results that match the city and country
                getAllPokemonNearBy(myCityCountry.second,myCityCountry.first);
            }
        });

        lW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                // Replace whatever is in the fragment_container view with this fragment
                transaction.replace(R.id.container, new QRMoreInfoFragment(p,nearByPokemon.get(position).getPokemon()), null);
                transaction.commit();
            }
        });

    }


    private void getAllPokemonNearBy(String city, String country) {
        CollectionReference pokemonRef = db.getPokemonCol();
        // Retrieve documents in the pokemon collection where the country or city field matches the search text
        pokemonRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Iterate over each document in the collection
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Get the pokemon_locations array for the current document
                    List<Map<String, Object>> locationArray = (List<Map<String, Object>>) document.getData().get("pokemon_locations");

                    // Check each location in the array
                    for(Map<String, Object> m : locationArray){
                        String countryMap = ((String) m.get("country")).toUpperCase();
                        String cityMap = ((String) m.get("city")).toUpperCase();

                        // Check if textSearch is included in the country or city name
                        if (cityMap.contains(city.toUpperCase().trim()) || countryMap.contains(country.toUpperCase().trim())) {
                            // Add the document to the list
                            Pokemon tempP = new Pokemon();
                            tempP.setID(document.getId());
                            PokemonInformation tempPI = new PokemonInformation(tempP,null,(double)m.get("latitude"),(double)m.get("longitude"),cityMap,countryMap);
                            adapter.add(tempPI);
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





}
