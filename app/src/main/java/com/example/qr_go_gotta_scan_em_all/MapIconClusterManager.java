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

public class MapIconClusterManager extends DefaultClusterRenderer<MapIconCluster> {
    private final IconGenerator iconGenerator;
    private final ImageView imageView;
    private final int markerSize;

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

    @Override
    protected void onBeforeClusterItemRendered(MapIconCluster item, MarkerOptions markerOptions) {

        imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return false;
    }
}
