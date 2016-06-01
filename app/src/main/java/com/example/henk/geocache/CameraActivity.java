package com.example.henk.geocache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;

public class CameraActivity extends Activity {
    Context context = this;
    Button button;
    Button movetoMaps;
    ImageView imageView;
    static final int CAM_REQUEST = 1;

    Filter filter = new Filter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);
            }
        });
        /* moving to MapsActivity */
        movetoMaps = (Button) findViewById(R.id.button2);
        movetoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMaps = new Intent(context, MapsActivity.class);
                startActivity(gotoMaps);
            }
        });
    }



    private File getFile(){
        File folder = new File ("sdcard/camera_app");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }

        File image_file = new File (folder,"cam_image.jpg");
        return image_file;
    }

    @Override
    /*get the image from the directory and put the image into imageview*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }
}
