package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.auth.User;
import com.google.maps.android.clustering.ClusterItem;

//referenced from
//CodingWithMitch - https://youtu.be/U6Z8FkjGEb4 and https://github.com/mitchtabian/Google-Maps-2018/tree/creating-custom-google-map-markers-end
/**
 * MapIconCluster represents a single map marker with custom attributes, such as a
 * title, snippet, and icon image. This class implements the ClusterItem interface
 * from the Google Maps Android API Utility Library, allowing it to be used with
 * marker clustering.
 */
public class MapIconCluster implements ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;
    private int iconImage;

    /**
     * Constructs a MapIconCluster with the specified position, title, snippet, and icon image.
     *
     * @param position  The geographical position of the marker on the map.
     * @param title     The title of the marker.
     * @param snippet   The snippet of the marker.
     * @param iconImage The resource ID of the icon image for the marker.
     */
    public MapIconCluster(LatLng position, String title, String snippet,int iconImage) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconImage = iconImage;
    }

    /**
     * Retrieves the resource ID of the icon image for the marker.
     *
     * @return The resource ID of the icon image.
     */
    public int getIconPicture() {
        return iconImage;
    }

    /**
     * Sets the resource ID of the icon image for the marker.
     *
     * @param iconPicture The resource ID of the icon image.
     */
    public void setIconPicture(int iconPicture) {
        this.iconImage = iconPicture;
    }

    /**
     * Sets the geographical position of the marker on the map.
     *
     * @param position The geographical position of the marker.
     */
    public void setPosition(LatLng position) {
        this.position = position;
    }

    /**
     * Sets the title of the marker.
     *
     * @param title The title of the marker.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the snippet of the marker.
     *
     * @param snippet The snippet of the marker.
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * Retrieves the geographical position of the marker on the map.
     *
     * @return The geographical position of the marker.
     */
    @NonNull
    public LatLng getPosition() {
        return position;
    }

    /**
     * Retrieves the title of the marker.
     *
     * @return The title of the marker.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the snippet of the marker.
     *
     * @return The snippet of the marker.
     */
    public String getSnippet() {
        return snippet;
    }

}
