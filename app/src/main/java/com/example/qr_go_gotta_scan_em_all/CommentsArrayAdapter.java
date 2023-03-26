package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**

 This class is an ArrayAdapter used to display a list of Pokemon objects in a ListView.
 It extends the ArrayAdapter class and overrides the getView method to populate each row of the ListView with the appropriate Pokemon object properties.
 */


public class CommentsArrayAdapter extends ArrayAdapter<Comment> {

    /**
     * Constructor for PokemonArrayAdapter.
     * @param context The context of the activity or fragment where the adapter will be used.
     * @param objects An ArrayList of Pokemon objects to be displayed in the ListView.
     */
    public CommentsArrayAdapter(@NonNull Context context, @NonNull ArrayList<Comment> objects) {
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
            view = LayoutInflater.from(getContext()).inflate(R.layout.content2,parent,false);
        } else {
            view = convertView;
        }
        // Get the item from the array adapter that we want to project onto our listview
        Comment c = super.getItem(position);

        // get all the components from the GasStation object that we want on the listview

        TextView userNameTw = view.findViewById(R.id.user_name);
        TextView textTw = view.findViewById(R.id.comment_text);


        // Name
        String userName = c.getPlayer().getUserName();
        // Text
        String text = c.getText();

        // Credits: Android Studio Website
        // Reference: https://developer.android.com/reference/java/text/SimpleDateFormat

        // Simple date format requires date object

        userNameTw.setText("Name: "+userName);
        textTw.setText("Comment: " + text);


        return view;
    }
}
