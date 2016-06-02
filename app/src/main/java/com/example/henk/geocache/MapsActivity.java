package com.example.henk.geocache;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    private DialogFragment warning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();

        warning = new GPSWarning();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in UCI and move the camera
        LatLng uci = new LatLng(33.645928, -117.842820);
        mMap.addMarker(new MarkerOptions().position(uci).title("Marker at UCI"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("My Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uci, 18));

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {
            System.out.println(se.getMessage());
            warning.show(getFragmentManager(), "Warning");
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onZoom(View view)
    {
        if(view.getId() == R.id.Bzoomin) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        }
        if(view.getId() == R.id.Bzoomout) {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    public void onCamera(View view)
    {
        System.out.println("Clicked Camera");
       Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }


    private void setUpMapIfNeeded()
    {
        if(mMap == null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if(mMap != null){
                setUpMap();
            }
        }

    }

    private void setUpMap()
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("My Location"));
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {
            System.out.println(se.getMessage());
        }
    }


     public static class GPSWarning extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Geocache");
            builder.setMessage("You have not enabled GPS locator on your phone.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just want it dismissed when clicked
                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

 

}
