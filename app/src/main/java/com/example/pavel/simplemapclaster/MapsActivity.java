package com.example.pavel.simplemapclaster;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<CarMarker> clusterManager;

    private ArrayList<CarMarker> arrCarMarker = new ArrayList<>();

    private final double base_lat = 55.752241; //Red square
    private final double base_lon = 37.622407;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // generate 100 random points
        for (int i = 0; i < 100; i++) {
            arrCarMarker.add(
                    new CarMarker(
                            base_lat + (Math.random() - 0.5) * 0.4, // 0.4 degree ~ Moscow's diameter
                            base_lon + (Math.random() - 0.5) * 0.4)
            );
        }

        ((Switch)findViewById(R.id.main_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //enable clustering
                    mMap.clear();
                    for (CarMarker marker : arrCarMarker){
                        clusterManager.addItem(marker);
                    }
                    clusterManager.cluster();
                } else {
                    clusterManager.clearItems();
                    clusterManager.cluster();
                    for (CarMarker marker : arrCarMarker){
                        mMap.addMarker(new MarkerOptions().position(marker.getPosition()));
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //init cluster manager
        clusterManager = new ClusterManager<>(getApplicationContext(), mMap);

        //set listener to refresh clusters after move
        mMap.setOnCameraIdleListener(clusterManager);

        clusterManager.cluster();

        // move the camera in Moscow
        LatLng base_pos = new LatLng(base_lat, base_lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(base_pos));

        //add markers (no clustering by default)
        for (CarMarker marker : arrCarMarker){
            mMap.addMarker(new MarkerOptions().position(marker.getPosition()));
        }
    }
}
