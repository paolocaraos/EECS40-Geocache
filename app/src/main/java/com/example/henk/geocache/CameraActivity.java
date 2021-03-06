package com.example.henk.geocache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class CameraActivity extends Activity {
    Bitmap globalphoto = null;
    Bitmap globalpoke = null;
    Context context = this;
    Button button;
    Button movetoMaps;
    Button saveFilter;
    ImageView imageView;
    ImageView filter;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    int imageNumber;
    int lastKnownNumber;
    //creates bug... RelativeLayout mainlayout = (RelativeLayout)findViewById(R.id.mainlayout);
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Trainer trainer;
    private PokemonFactory pokemonFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        //Added by Paolo: retrive pokemon from MapsActivity
        trainer  = (Trainer) getIntent().getSerializableExtra("Trainer");
        pokemonFactory = new PokemonFactory(this);

        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        filter = (ImageView) findViewById(R.id.filter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = makeFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,REQUEST_IMAGE_CAPTURE);
                System.out.println("After starting activity, imageNumber = " + imageNumber);
            }
        });

        /* moving to MapsActivity*/
        movetoMaps = (Button) findViewById(R.id.button2);
        movetoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("Trainer", trainer);
                setResult(Activity.RESULT_OK, i);
                finish();

                /*code when camera is the launching activity
                Intent gotoMaps = new Intent(context, MapsActivity.class);
                startActivity(gotoMaps);*/
            }
        });

        /*save filter button*/
        saveFilter = (Button) findViewById(R.id.button3);
        saveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((globalphoto!=null)&&(globalpoke!=null)) {
                    mergeImages(globalphoto, globalpoke);

                    /*success message*/
                    CharSequence sText = "The pokemon has been captured and saved to the camera_app folder!";
                    int sDuration = Toast.LENGTH_LONG;
                    Context sContext = getApplicationContext();
                    Toast sToast = Toast.makeText(sContext, sText, sDuration);

                    trainer.recordPokemonCaught(pokemonFactory.getPokemon(trainer.getEncounteredPokemonName()));

                    saveUser();
                    sToast.show();
                }
                else {
                    /*fail message*/
                    CharSequence fText = "Failed to capture pokemon";
                    int fDuration = Toast.LENGTH_SHORT;
                    Context fContext = getApplicationContext();
                    Toast fToast = Toast.makeText(fContext,fText,fDuration);
                    fToast.show();
                }

            }
        });


    }
    private int getImageNumber(){
        return imageNumber;
    }

    private void incrementImageNumber(){
        imageNumber++;
    }

    private File makeFile(){
        File folder = new File ("sdcard/Geocache/camera_app");
        imageNumber=0;
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String imageName = "cam_image"+ String.valueOf(getImageNumber()) + ".jpg";
        File image_file = new File (folder,imageName);
        while(image_file.exists()){
            incrementImageNumber();
            imageName = "cam_image"+ String.valueOf(getImageNumber()) + ".jpg";
            image_file = new File(folder,imageName);
        }
        System.out.println("makeFile, imageNumber = " + getImageNumber());
        return image_file;
    }

    private File getPreviousFile(){
        File folder = new File ("sdcard/Geocache/camera_app");
        imageNumber=0;
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String imageName = "cam_image"+ String.valueOf(getImageNumber()) + ".jpg";
        File image_file = new File (folder,imageName);
        while(image_file.exists()){
            incrementImageNumber();
            imageName = "cam_image"+ String.valueOf(getImageNumber()) + ".jpg";
            image_file = new File(folder,imageName);
        }
        imageName = "cam_image"+ String.valueOf(getImageNumber()-1) + ".jpg";
        image_file = new File(folder,imageName);
        System.out.println("makeFile, imageNumber = " + getImageNumber());
        return image_file;
    }

    private File getFile(){
        File folder = new File ("sdcard/Geocache/camera_app");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String imageName = "cam_image"+ String.valueOf(getImageNumber()) + ".jpg";
        File image_file = new File (folder,imageName);
        System.out.println("getFile, imageNumber = " + getImageNumber());
        return image_file;
    }

    private File getUserFile(){
        File folder = new File ("sdcard/Geocache/user_profile");
        //check for folder existence
        if(!folder.exists()){
            folder.mkdir();
        }
        String profileName = trainer.getName() + ".txt";
        File file = new File (folder,profileName);

        return file;
    }

    public void mergeImages(Bitmap bottom, Bitmap top){
        Bitmap combine = Bitmap.createBitmap(bottom.getWidth(), bottom.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combine);


        canvas.drawBitmap(bottom, 0f, 0f, null);
        canvas.drawBitmap(top, 0f, bottom.getHeight()/2,null);

        OutputStream outStream = null;
        File file = getFile();

        try {
            outStream = new FileOutputStream(file);
            combine.compress(Bitmap.CompressFormat.JPEG,100,outStream);
            outStream.flush();
            outStream.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveUser(){
        File file = getUserFile();
        try{
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trainer);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    /*get the image from the directory and put the image into imageview*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File file = getPreviousFile();
            System.out.println("Loading image to imageView, imageNumber = " + imageNumber);
            //String path = "sdcard/camera_app/cam_image" + String.valueOf(imageNumber) + ".jpg";
            String path2 = file.getAbsolutePath();
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap photo = BitmapFactory.decodeFile(path2,bmOptions);

            /*print text onto screen*/
            textView1 = (TextView) findViewById(R.id.textView1);
            textView1.setVisibility(View.VISIBLE);
            textView2 = (TextView) findViewById(R.id.textView2);
            textView2.setVisibility(View.VISIBLE);
            textView3 = (TextView) findViewById(R.id.textView3);
            textView3.setVisibility(View.VISIBLE);

            /*scale down the bitmap size*/
            int scaling = (int) (photo.getHeight()*(512.0/photo.getWidth()));
            photo = Bitmap.createScaledBitmap(photo,512,scaling,true);
            /*rotate bitmap*/
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            photo = Bitmap.createBitmap(photo,0,0,photo.getWidth(),photo.getHeight(),matrix,true);
            imageView.setImageBitmap(photo);
            //imageView.setImageDrawable(Drawable.createFromPath(path));

            Bitmap poke = pokemonFactory.getPokemonSprite(trainer.getEncounteredPokemonName());

            if(poke != null)
                filter.setImageBitmap(poke);
            poke = poke.createScaledBitmap(poke,photo.getWidth(),photo.getHeight()/2,true);
            globalphoto = photo;
            globalpoke = poke;

        }
    }
}
