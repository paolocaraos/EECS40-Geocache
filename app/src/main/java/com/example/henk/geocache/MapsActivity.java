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

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locManager;
    private LocationListener locListener;

    private Biome recentKnownBiome;

    private MarkerOptions marker;
    private Location markerLocation;

    private TextView pokemonText;
    private TextView biomeText;
    private TextView locationText;

    public PokemonFactory pokeFactory;
    private Trainer trainer;

    private Vector<Biome> biomeVector = new Vector<Biome>(3,1);

    public static class ConfigConstants
    {
        public static final int VERIFY_REQUEST = 10;

        public static final int UPDATE_TIME_FREQUENCY = 1000;
        public static final int UPDATE_SPACE_FREQUENCY = 0;

        public static final int DEFAULT_ZOOM_LEVEL = 17;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        pokeFactory = new PokemonFactory(this);

        pokemonText = (TextView) findViewById(R.id.pokemonText);
        biomeText = (TextView) findViewById(R.id.biomeText);
        locationText = (TextView) findViewById(R.id.locationText);

        markerLocation = new Location("Marker");

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(!getCurrentBiome(location).equals(recentKnownBiome)) {
                    recentKnownBiome = getCurrentBiome(location);
                    trainer.encounterPokemon(pokeFactory.getLocalPokemon(recentKnownBiome));
                    pokemonText.setText("Nearby Pokemon: " + trainer.getEncounteredPokemonName());
                }

                biomeText.setText("Biome: "+ recentKnownBiome.getType().name());
                locationText.setText("My Location: " + recentKnownBiome.getName()
                        +"\nMy Lat = " + location.getLatitude()
                        +"\nMy Long = " + location.getLongitude()
                        +"\n Marker Lat = " + markerLocation.getLatitude()
                        +"\nMarker Long = " + markerLocation.getLongitude()
                        +"\nDistanceToMark = "+ location.distanceTo(markerLocation));
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

        setUpBiomes();
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
        LatLng uci = new LatLng(Biome.Constants.UCI_LAT, Biome.Constants.UCI_LONG);

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
        intent.putExtra("Trainer", trainer);
        startActivity(intent);
    }

    private void verifyPermissions()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

    private void setUpBiomes()
    {
        biomeVector.add(new Biome.Builder()
                .setName("UCI: Aldrich Park")
                .setRadius(Biome.Constants.ALDRICH_PARK_RADIUS_IN_METERS)
                .setLatitude(Biome.Constants.ALDRICH_PARK_LAT)
                .setLongitude(Biome.Constants.ALDRICH_PARK_LONG)
                .setType(Biome.Constants.ALDRICH_PARK_BIOME_TYPE)
                .build());

        biomeVector.add(new Biome.Builder()
                .setName("UCI: Student Center")
                .setRadius(Biome.Constants.STUDENT_CENTER_RADIUS_IN_METERS)
                .setLatitude(Biome.Constants.STUDENT_CENTER_LAT)
                .setLongitude(Biome.Constants.STUDENT_CENTER_LONG)
                .setType(Biome.Constants.STUDENT_CENTER_BIOME_TYPE)
                .build());

        biomeVector.add(new Biome.Builder()
                .setName("UCI: Henry Samueli of School Of Engineering")
                .setRadius(Biome.Constants.ENGINEERING_RADIUS_IN_METERS)
                .setLatitude(Biome.Constants.ENGINEERING_LAT)
                .setLongitude(Biome.Constants.ENGINEERING_LONG)
                .setType(Biome.Constants.ENGINEERING_BIOME_TYPE)
                .build());

        biomeVector.add(new Biome.Builder()
                .setName("UCI: Infinity Fountain")
                .setRadius(Biome.Constants.INFINITY_FOUNTAIN_RADIUS_IN_METERS)
                .setLatitude(Biome.Constants.INFINITY_FOUNTAIN_LAT)
                .setLongitude(Biome.Constants.INFINITY_FOUNTAIN_LONG)
                .setType(Biome.Constants.INFINITY_FOUNTAIN_BIOME_TYPE)
                .build());
    }

    private Biome getCurrentBiome(Location loc){
        Biome closestBiome = null;
        float closestDistance = 2000;

        for(Biome b: biomeVector){
            float f =  loc.distanceTo(b.getLocation());
            if (b.getRadius() > f){
                if(f < closestDistance){
                    closestBiome = b;
                    closestDistance = f;
                }
            }
        }

        return closestBiome;
    }
}
