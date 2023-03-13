package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PokemonArrayAdapter extends ArrayAdapter<Pokemon> {
    public PokemonArrayAdapter(@NonNull Context context, @NonNull ArrayList<Pokemon> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // convertView is used for converting the information from the front end (view)
        // to backend

        View view;
        if(convertView == null){
            // if there is no previously used view for the fragment, then the addgasstation layout
            // must be inflated
            // this is for optimization so that the previously used view can be used.
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,parent,false);
        } else {
            view = convertView;
        }
        // Get the item from the array adapter that we want to project onto our listview
        Pokemon pokemon = super.getItem(position);

        // get all the components from the GasStation object that we want on the listview

        TextView pokemonNameTextView = view.findViewById(R.id.pokemon_name);
        TextView hashTextView = view.findViewById(R.id.visual_reper);


        // Name
        String name = pokemon.getName();
        String visualReper = pokemon.visualReper();
        // Carbon

        // Credits: Android Studio Website
        // Reference: https://developer.android.com/reference/java/text/SimpleDateFormat

        // Simple date format requires date object

        pokemonNameTextView.setText("Name: "+name);
        hashTextView.setText("Visual: " + visualReper);


        return view;
    }
}
