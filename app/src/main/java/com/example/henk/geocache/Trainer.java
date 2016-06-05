package com.example.henk.geocache;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Paolo on 6/5/2016.
 */
public class Trainer implements Serializable{

    private String name;

    private int score;

    private Vector<String> pokemonCaught = new Vector<String>(3,1);

    Trainer(String name){
        this.name = name;
    }

    Trainer setScore(int i){
        score = i;
        return this;
    }

    void addScore(int i){
        score += i;
    }

    Trainer addPokemonCaught(String  p){
        pokemonCaught.add(p);
        return this;
    }

    String getListOfCaughtPokemon(){
        String s = new String();

        for(String name: pokemonCaught){
            s.concat("\n" + name);
        }

        return s;
    }
}
