package com.example.qr_go_gotta_scan_em_all;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Pokemon pokemon;
    private Player player;
    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public OverviewFragment (Pokemon pokemon){
        this.pokemon =pokemon;
    }

    public OverviewFragment (Player player){
        this.player =player;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.overview_page, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView usernameVal = view.findViewById(R.id.usernameView_show);

        try{
            System.out.println(player.getUserName());
            usernameVal.setText(player.getUserName());
        }catch (NullPointerException e){
            System.out.println("loading db");
        }

        // or  (ImageView) view.findViewById(R.id.foo);
    }
}