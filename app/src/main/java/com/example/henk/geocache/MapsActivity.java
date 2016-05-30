package com.example.henk.geocache;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    /* for the camera*/
    CameraPreview cv;
    Filter filter;
    FrameLayout layout;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* for camera*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void Load(){
        Camera camera = getCamera();
        if(camera != null){
            layout = new FrameLayout(this);
            layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

            cv = new CameraPreview(this,camera);
            layout.addView(cv);
            filter = new Filter(this);
            layout.addView(filter);

            setContentView(layout);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to find camera. Closing", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    public static Camera getCamera(){
        Camera camera = null;
        try{
            camera = Camera.open();
        }
        catch(Exception e){
        }
        return camera;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(cv != null){
            cv.onPause();
            cv = null;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Load();
    }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
