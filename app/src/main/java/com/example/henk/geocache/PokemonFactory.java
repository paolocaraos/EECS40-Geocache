package com.example.henk.geocache;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Paolo on 6/3/2016.
 */
public class PokemonFactory {

    private Context context;

    private Random r = new Random();

    private Vector<Pokemon> water = new Vector<Pokemon> (3,1);
    private Vector<Pokemon> urban = new Vector<Pokemon> (3,1);
    private Vector<Pokemon> park = new Vector<Pokemon> (3,1);

    private Vector<Pokemon> rare = new Vector<Pokemon>(3,1);

    private HashMap<Biome.Type, Vector<Pokemon>> mapOfSpawnLists = new HashMap<Biome.Type, Vector<Pokemon>>();

    PokemonFactory(Context context){
        this.context = context;

        rare.add(new Pokemon("Lugia")
            .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.lugia))
            .addHabitat(Biome.Type.RARE));

        water.add(new Pokemon("Squirtle")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.squirtle))
                .addHabitat(Biome.Type.FRESH_WATER));
        //add more water types

        park.add(new Pokemon("Bulbasaur")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bulbasaur))
                .addHabitat(Biome.Type.GRASSY));

        urban.add(new Pokemon("Charmander")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.charmander))
                .addHabitat(Biome.Type.URBAN));
        urban.add(new Pokemon("Pikachu")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pikachu))
                .addHabitat(Biome.Type.URBAN));
        //add more building pokemon

        mapOfSpawnLists.put(Biome.Type.FRESH_WATER, water);
        mapOfSpawnLists.put(Biome.Type.GRASSY, park);
        mapOfSpawnLists.put(Biome.Type.URBAN, urban);
        mapOfSpawnLists.put(Biome.Type.RARE, rare);
    }

    public Pokemon getLocalPokemon(Biome biome)
    {
        if(biome == null)
            return null;

        Random r = new Random();
        Vector<Pokemon> v = mapOfSpawnLists.get(biome.getType());
        return v.get(r.nextInt(v.size()));
    }
}
