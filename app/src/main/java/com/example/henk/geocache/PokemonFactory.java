package com.example.henk.geocache;

import android.content.Context;
import android.graphics.Bitmap;
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

    private Vector<Pokemon> all = new Vector<Pokemon>(5,1);

    private HashMap<Biome.Type, Vector<Pokemon>> mapOfSpawnLists = new HashMap<Biome.Type, Vector<Pokemon>>();

    PokemonFactory(MapsActivity context){
        this.context = context;

        rare.add(new Pokemon("Lugia")
            .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.lugia))
            .addHabitat(Biome.Type.RARE)
            .setScoreValue(Pokemon.Rarity.LEGENDARY));

        water.add(new Pokemon("Squirtle")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.squirtle))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.FRESH_WATER));
        //add more water types

        park.add(new Pokemon("Bulbasaur")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bulbasaur))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.GRASSY));

        urban.add(new Pokemon("Charmander")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.charmander))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.URBAN));
        urban.add(new Pokemon("Pikachu")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pikachu))
                .setScoreValue(Pokemon.Rarity.PIKA)
                .addHabitat(Biome.Type.URBAN));
        //add more building pokemon

        mapOfSpawnLists.put(Biome.Type.FRESH_WATER, water);
        mapOfSpawnLists.put(Biome.Type.GRASSY, park);
        mapOfSpawnLists.put(Biome.Type.URBAN, urban);
        mapOfSpawnLists.put(Biome.Type.RARE, rare);
    }

    PokemonFactory(CameraActivity context)
    {
        this.context = context;

        all.add(new Pokemon("Lugia")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.lugia))
                .setScoreValue(Pokemon.Rarity.LEGENDARY)
                .addHabitat(Biome.Type.RARE));

        all.add(new Pokemon("Squirtle")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.squirtle))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.FRESH_WATER));

        all.add(new Pokemon("Bulbasaur")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bulbasaur))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.GRASSY));

        all.add(new Pokemon("Charmander")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.charmander))
                .setScoreValue(Pokemon.Rarity.RARE)
                .addHabitat(Biome.Type.URBAN));
        all.add(new Pokemon("Pikachu")
                .setSprite(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pikachu))
                .setScoreValue(Pokemon.Rarity.PIKA)
                .addHabitat(Biome.Type.URBAN));
    }

    public Pokemon getLocalPokemon(Biome biome)
    {
        if(biome == null)
            return null;

        Random r = new Random();
        Vector<Pokemon> v = mapOfSpawnLists.get(biome.getType());
        return v.get(r.nextInt(v.size()));
    }

    public Bitmap getPokemon(String name)
    {
        for(Pokemon p: all)
        {
            if(name.equals(p.getName()))
                return p.getSprite();
        }

        return null;
    }
}
