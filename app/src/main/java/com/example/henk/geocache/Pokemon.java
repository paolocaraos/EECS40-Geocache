package com.example.henk.geocache;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Paolo on 6/3/2016.
 */
public class Pokemon implements Serializable{

    private Bitmap sprite;

    private int scoreValue = 100;

    public static class Rarity{
        static final int LEGENDARY = 1000;
        static final int PIKA = 250;
        static final int RARE = 100;
        static final int COMMON = 50;
    }

    private String name;

    private Vector<Biome.Type> listOfHabitat = new Vector<Biome.Type>(1,1);

    Pokemon(String n){
        this.name = n;
    }

    Pokemon setSprite(Bitmap s){
        sprite = s;
        return this;
    }

    Pokemon addHabitat(Biome.Type type){
        listOfHabitat.add(type);
        return this;
    }

    Pokemon setScoreValue(int i){
        scoreValue = i;
        return this;
    }

    Bitmap getSprite(){
        return sprite;
    }

    int getValue(){
        return scoreValue;
    }

    String getName(){ return name;}
}
