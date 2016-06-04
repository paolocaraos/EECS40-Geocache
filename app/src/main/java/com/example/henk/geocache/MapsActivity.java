package com.example.henk.geocache;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locManager;
    private LocationListener locListener;

    private Location recentKnownLocation;

    private MarkerOptions marker;
    private Location markerLocation;
    private TextView locationText;
    private TextView distanceText;

    public static class ConfigConstants
    {
        public static final int VERIFY_REQUEST = 10;

        public static final int UPDATE_TIME_FREQUENCY = 5000;
        public static final int UPDATE_SPACE_FREQUENCY = 0;

        public static final int DEFAULT_ZOOM_LEVEL = 17;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        locationText = (TextView) findViewById(R.id.locationText);
        distanceText = (TextView) findViewById(R.id.distanceText);

        markerLocation = new Location("Marker");

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                recentKnownLocation = location;

                locationText.setText("This Lat = " + markerLocation.getLatitude() + "\nThis Long = " + markerLocation.getLongitude());

                distanceText.setText("Distance = " + location.distanceTo(markerLocation));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        verifyPermissions();

        setUpMapIfNeeded();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case ConfigConstants.VERIFY_REQUEST:
                verifyPermissions();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in UCI and move the camera
        LatLng uci = new LatLng(33.645928, -117.842820);

        marker = new MarkerOptions()
                .position(uci)
                .title("Marker")
                .draggable(true);

        mMap.addMarker(marker);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                markerLocation.setLongitude(marker.getPosition().longitude);
                markerLocation.setLatitude(marker.getPosition().latitude);
            }
        });
        try {
            mMap.setMyLocationEnabled(true);
        }catch (SecurityException e) {
            e.printStackTrace();
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uci, ConfigConstants.DEFAULT_ZOOM_LEVEL));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void onCamera(View view)
    {
       Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    private void verifyPermissions()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        , ConfigConstants.VERIFY_REQUEST);
            }
            return;
        }


        try {
            locManager.requestLocationUpdates("gps", ConfigConstants.UPDATE_TIME_FREQUENCY, ConfigConstants.UPDATE_SPACE_FREQUENCY, locListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

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
}
