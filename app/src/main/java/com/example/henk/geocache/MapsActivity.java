package com.example.henk.geocache;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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
    private TextView username;
    private TextView points;

    private Button profileButton;

    public PokemonFactory pokeFactory;
    private Trainer trainer;

    private Vector<Biome> biomeVector = new Vector<Biome>(3, 1);

    public static class ConfigConstants {
        public static final int VERIFY_REQUEST = 10;

        public static final int UPDATE_TIME_FREQUENCY = 1000;
        public static final int UPDATE_SPACE_FREQUENCY = 0;

        public static final int DEFAULT_ZOOM_LEVEL = 17;

        public static final int CAMERA_ACTIVITY_REQUEST_CODE = 10;
        public static final int PROFILE_ACTIVITY_REQUEST_CODE = 20;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        //added by Henk to get trainer obj from main activity
        trainer  = (Trainer) getIntent().getSerializableExtra("Trainer");

        pokeFactory = new PokemonFactory(this);

        pokemonText = (TextView) findViewById(R.id.pokemonText);
        biomeText = (TextView) findViewById(R.id.biomeText);
        locationText = (TextView) findViewById(R.id.locationText);
        username = (TextView) findViewById(R.id.username);
        points = (TextView) findViewById(R.id.points);

        profileButton = (Button) findViewById(R.id.toProfile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("Trainer", trainer);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        markerLocation = new Location("Marker");

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                /*
                if(recentKnownBiome == null)
                    recentKnownBiome = biomeVector.elementAt(0);*/

                if (!getCurrentBiome(location).equals(recentKnownBiome)) {
                    recentKnownBiome = getCurrentBiome(location);
                    if(recentKnownBiome != null) {
                        trainer.encounterPokemon(pokeFactory.getLocalPokemon(recentKnownBiome));
                        pokemonText.setText("Nearby Pokemon: " + trainer.getEncounteredPokemonName());
                        biomeText.setText("My Location: " + recentKnownBiome.getName()+ "\nBiome: " + recentKnownBiome.getType().name());

                    }
                    else{
                        biomeText.setText("Biome: null\n My Location: null");
                    }
                }
                locationText.setText(




                         "\nMy Lat = " + location.getLatitude()
                        + "\nMy Long = " + location.getLongitude()/*
                        + "\n Marker Lat = " + markerLocation.getLatitude()
                        + "\nMarker Long = " + markerLocation.getLongitude()
                        + "\nDistanceToMark = " + location.distanceTo(markerLocation)*/);

                String usernameText = "Username: " + trainer.getName();
                String pointsText = "Score: " + String.valueOf(trainer.getScore());
                username.setText(usernameText);
                points.setText(pointsText);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uci, ConfigConstants.DEFAULT_ZOOM_LEVEL));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("Trainer", trainer);
        startActivityForResult(intent, ConfigConstants.CAMERA_ACTIVITY_REQUEST_CODE);
    }

    public void onPokeCaught(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("Trainer", trainer);
        startActivityForResult(intent, ConfigConstants.PROFILE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (ConfigConstants.CAMERA_ACTIVITY_REQUEST_CODE): {
                if (resultCode == Activity.RESULT_OK) {
                    trainer = (Trainer) data.getSerializableExtra("Trainer");
                }
                break;
            }
            case (ConfigConstants.PROFILE_ACTIVITY_REQUEST_CODE):{
                if(resultCode ==  Activity.RESULT_OK){
                    trainer = (Trainer) data.getSerializableExtra("Trainer");
                }
                break;
            }
        }
    }

    private void verifyPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
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


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if (mMap != null) {
                setUpMap();
            }
        }

    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("My Location"));
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {
            System.out.println(se.getMessage());
        }
    }

    private void setUpBiomes() {

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

        biomeVector.add(new Biome.Builder()
                .setName("UCI: Campus")
                .setRadius(Biome.Constants.EVERYWHERE_RADIUS_IN_METERS)
                .setLatitude(Biome.Constants.EVERYWHERE_LAT)
                .setLongitude(Biome.Constants.EVERYWHERE_LONG)
                .setType(Biome.Constants.EVERYWHERE_BIOME_TYPE)
                .build());
    }

    private Biome getCurrentBiome(Location loc) {
        Biome closestBiome = null;
        float closestDistance = 11000;

        for (Biome b : biomeVector) {
            float f = loc.distanceTo(b.getLocation());
            if (b.getRadius() > f) {
                if (f < closestDistance) {
                    closestBiome = b;
                    closestDistance = f;
                }
            }
        }

        return closestBiome;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.henk.geocache/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-gienerated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.henk.geocache/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
