package com.example.henk.geocache;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class CameraActivity extends AppCompatActivity {

    /* for the camera*/
    CameraPreview cv;
    Filter filter;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_camera);

        /* for camera*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
