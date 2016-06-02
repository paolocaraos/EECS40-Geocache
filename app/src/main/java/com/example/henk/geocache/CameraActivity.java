package com.example.henk.geocache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class CameraActivity extends Activity {
    Context context = this;
    Button button;
    Button movetoMaps;
    ImageView imageView;
    LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
    static final int REQUEST_IMAGE_CAPTURE = 1;


    Filter filter = new Filter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);

        System.out.println("Switched to camera.");

        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,REQUEST_IMAGE_CAPTURE);
            }
        });*/
        /* moving to MapsActivity */
        /*movetoMaps = (Button) findViewById(R.id.button2);
        movetoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMaps = new Intent(context, MapsActivity.class);
                startActivity(gotoMaps);
            }
        });*/
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String path = "sdcard/camera_app/cam_image.jpg";
            imageView.setImageDrawable(Drawable.createFromPath(path));
            /*
            Bundle extras = data.getExtras();
            Bitmap cameraimage = (Bitmap) extras.get("data");
            */

            /*adding filter to to the imageview*/
            Bitmap result = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            filter.draw(canvas);
            filter.setLayoutParams(new FrameLayout.LayoutParams(25,25));
            layout.addView(filter);
        }
    }
}
