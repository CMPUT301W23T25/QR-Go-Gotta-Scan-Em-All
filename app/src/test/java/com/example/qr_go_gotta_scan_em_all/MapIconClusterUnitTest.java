package com.example.qr_go_gotta_scan_em_all;

import com.google.type.LatLng;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MapIconClusterUnitTest {
    @Test
    public void testMapIconCluster() {
        String title = "title";
        String snippet = "snippet";
        int iconImage = 0;
        MapIconCluster mapIconCluster = new MapIconCluster(
                null,
                title,
                snippet,
                iconImage
        );

        assertEquals(mapIconCluster.getTitle(), title);
        assertEquals(mapIconCluster.getSnippet(), snippet);
        assertEquals(mapIconCluster.getIconPicture(), iconImage);

        title = "title2";
        snippet = "snippet2";
        iconImage = 1;

        mapIconCluster.setTitle(title);
        mapIconCluster.setSnippet(snippet);
        mapIconCluster.setIconPicture(iconImage);

        assertEquals(mapIconCluster.getTitle(), title);
        assertEquals(mapIconCluster.getSnippet(), snippet);
        assertEquals(mapIconCluster.getIconPicture(), iconImage);
    }
}
