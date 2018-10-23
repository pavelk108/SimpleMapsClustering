package com.example.pavel.simplemapclaster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class CarMarker implements ClusterItem {
    private double latitude;
    private double longitude;

    CarMarker(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }

    @Override
    public String getTitle() {
        return "title";
    }

    @Override
    public String getSnippet() {
        return "snippet";
    }

}
