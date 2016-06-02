package com.example.henk.geocache;

import android.graphics.Bitmap;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Henk on 2016/6/1.
 */
public class SpriteFactory {
    Random r = new Random();
    Vector<Bitmap> water = new Vector (3,1);
    Vector<Bitmap> building = new Vector (3,1);
    Vector<Bitmap> park = new Vector (3,1);

    void addWaterSprite (Bitmap sprite) {
        water.add(sprite);
    }

    void addBuildingSprite (Bitmap sprite) {
        building.add(sprite);
    }

    void addParkSprite (Bitmap sprite) {
        park.add(sprite);
    }

    Bitmap getWaterSprite(){
        int pokeIndex = r.nextInt(water.size());
        return water.get(pokeIndex);
    }

    Bitmap getBuildingSprite(){
        int pokeIndex = r.nextInt(building.size());
        return building.get(pokeIndex);
    }

    Bitmap getParkSprite(){
        int pokeIndex = r.nextInt(park.size());
        return park.get(pokeIndex);
    }
}
