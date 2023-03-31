package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.auth.User;
import com.google.maps.android.clustering.ClusterItem;

//referenced from
//CodingWithMitch - https://youtu.be/U6Z8FkjGEb4 and https://github.com/mitchtabian/Google-Maps-2018/tree/creating-custom-google-map-markers-end
public class MapIconCluster implements ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;
    private int iconImage;


    public MapIconCluster(LatLng position, String title, String snippet,int iconImage) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconImage = iconImage;
    }

    public int getIconPicture() {
        return iconImage;
    }

    public void setIconPicture(int iconPicture) {
        this.iconImage = iconPicture;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    @NonNull
    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

}
