package com.example.henk.geocache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
    //LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
    static final int REQUEST_IMAGE_CAPTURE = 1;


    //Filter filter = new Filter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        System.out.println("Switched to camera.");

        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,REQUEST_IMAGE_CAPTURE);
            }
        });

        /* moving to MapsActivity*/
        movetoMaps = (Button) findViewById(R.id.button2);
        movetoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                /*code when camera is the launching activity
                Intent gotoMaps = new Intent(context, MapsActivity.class);
                startActivity(gotoMaps);*/
            }
        });

    }

    /*private View.OnClickListener btnListenerCamera = new View.OnClickListener() {
        public void onClick(View v)
        {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = getFile();
            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(camera_intent,REQUEST_IMAGE_CAPTURE);
        }
    };

    private View.OnClickListener btnListenerMap = new View.OnClickListener() {
        public void onClick(View v)
        {
            Intent gotoMaps = new Intent(context, MapsActivity.class);
            startActivity(gotoMaps);
        }
    };
    */


    private File getFile(){
        File folder = new File ("sdcard/camera_app");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }

        File image_file = new File (folder,"cam_image3.jpg");
        return image_file;
    }

    @Override
    /*get the image from the directory and put the image into imageview*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String path = "sdcard/camera_app/cam_image3.jpg";
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap photo = BitmapFactory.decodeFile(path,bmOptions);
            /*scale down the bitmap size*/
            int scaling = (int) (photo.getHeight()*(512.0/photo.getWidth()));
            photo = Bitmap.createScaledBitmap(photo,512,scaling,true);
            /*rotate bitmap*/
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            photo = Bitmap.createBitmap(photo,0,0,photo.getWidth(),photo.getHeight(),matrix,true);
            imageView.setImageBitmap(photo);
            //imageView.setImageDrawable(Drawable.createFromPath(path));

            /*adding filter to to the imageview*/
            Bitmap result = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            /*filter.draw(canvas);
            filter.setLayoutParams(new FrameLayout.LayoutParams(25,25));
            layout.addView(filter);*/

        }
    }
}
