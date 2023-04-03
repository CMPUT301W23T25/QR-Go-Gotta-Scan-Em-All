package com.example.qr_go_gotta_scan_em_all;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;


//referenced from
//CodingWithMitch - https://youtu.be/U6Z8FkjGEb4 and https://github.com/mitchtabian/Google-Maps-2018/tree/creating-custom-google-map-markers-end
/**
 * MapIconClusterManager is a custom implementation of DefaultClusterRenderer that
 * customizes the appearance of cluster item markers on the map.
 */
public class MapIconClusterManager extends DefaultClusterRenderer<MapIconCluster> {
    private final IconGenerator iconGenerator;
    private final ImageView imageView;
    private final int markerSize;

    /**
     * Constructs a MapIconClusterManager with the specified context, GoogleMap,
     * and ClusterManager.
     *
     * @param context       The context associated with this cluster manager.
     * @param googleMap     The GoogleMap instance used to display the markers.
     * @param clusterManager The ClusterManager instance used to manage the clusters.
     */
    public MapIconClusterManager(Context context, GoogleMap googleMap,
                                 ClusterManager<MapIconCluster> clusterManager) {

        super(context, googleMap, clusterManager);

        // initialize cluster item icon generator
        iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView = new ImageView(context.getApplicationContext());
        markerSize = (int) context.getResources().getDimension(R.dimen.custom_marker_size);;
        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerSize, markerSize));
        int padding = (int) context.getResources().getDimension(R.dimen.custom_marker_padding);
        imageView.setPadding(padding, padding, padding, padding);
        iconGenerator.setContentView(imageView);

    }

    /**
     * Called before an individual cluster item is rendered on the map. Sets the
     * appearance of the cluster item's marker using the provided icon.
     *
     * @param item          The MapIconCluster item to be rendered.
     * @param markerOptions The MarkerOptions instance used to customize the marker's appearance.
     */
    @Override
    protected void onBeforeClusterItemRendered(MapIconCluster item, MarkerOptions markerOptions) {

        imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    /**
     * Determines whether the cluster should be rendered as a single cluster or as individual markers.
     * This method always returns false, causing the cluster to always render as individual markers.
     *
     * @param cluster The Cluster instance to be rendered.
     * @return false to always render the cluster as individual markers.
     */
    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return false;
    }
}
