package com.example.henk.geocache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Henk on 2016/6/1.
 */
public class SpriteFactory{

    private Context context;

    private Random r = new Random();

    private Vector<Bitmap> water = new Vector (3,1);
    private Vector<Bitmap> urban = new Vector (3,1);
    private Vector<Bitmap> park = new Vector (3,1);

    private Vector<Bitmap> rare = new Vector<Bitmap>(3,1);

    public enum Biome{
        FRESH_WATER,
        PARK,
        URBAN
    }

    private HashMap<Biome, Vector<Bitmap>> mapOfSpawnLists = new HashMap<Biome, Vector<Bitmap>>();

    SpriteFactory(Context context){
        this.context = context;

        rare.add(BitmapFactory.decodeResource(context.getResources(), R.mipmap.lugia));

        water.add(BitmapFactory.decodeResource(context.getResources(), R.mipmap.squirtle));
        //add more water types


        park.add(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bulbasaur));
        //add more grass types

        urban.add(BitmapFactory.decodeResource(context.getResources(), R.mipmap.charmander));
        urban.add(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pikachu));
        //add more building pokemon

    }
}
