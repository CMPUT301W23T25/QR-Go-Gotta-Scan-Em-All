package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**

 This class is an ArrayAdapter used to display a list of Pokemon objects in a ListView.
 It extends the ArrayAdapter class and overrides the getView method to populate each row of the ListView with the appropriate Pokemon object properties.
 */


public class PokemonArrayAdapter extends ArrayAdapter<Pokemon> {

    /**
     * Constructor for PokemonArrayAdapter.
     * @param context The context of the activity or fragment where the adapter will be used.
     * @param objects An ArrayList of Pokemon objects to be displayed in the ListView.
     */
    public PokemonArrayAdapter(@NonNull Context context, @NonNull ArrayList<Pokemon> objects) {
        super(context, 0, objects);
    }

    /**
     * This method overrides the getView method in ArrayAdapter to populate each row of the ListView with the appropriate Pokemon object properties.
     * @param position The position of the Pokemon object in the ArrayList that we want to display in the current row of the ListView.
     * @param convertView A view that has been inflated from a previous row in the ListView. This view can be recycled and reused to save memory.
     * @param parent The parent ViewGroup that the current row of the ListView belongs to.
     * @return A view that represents a row in the ListView.
     */
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
//        TextView hashTextView = view.findViewById(R.id.visual_reper);
        TextView scoreView = view.findViewById(R.id.score);
        TextView locationView = view.findViewById(R.id.location);


        // Name
        String name = pokemon.getName();
        String visualReper = pokemon.visualReper();
        // Carbon

        // Credits: Android Studio Website
        // Reference: https://developer.android.com/reference/java/text/SimpleDateFormat

        // Simple date format requires date object

        pokemonNameTextView.setText("Name: "+name);
        scoreView.setText("Score: " + Double.toString(pokemon.getScore()));

        if (pokemon.getLocation() != null){
            locationView.setText("Location: " + "(" + Double.toString(pokemon.getLocation().first) +"," + Double.toString(pokemon.getLocation().second) +")");
        } else{
            locationView.setText("Location: Not added");
        }

        return view;
    }
}
