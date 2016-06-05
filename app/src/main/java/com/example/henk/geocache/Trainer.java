package com.example.henk.geocache;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Paolo on 6/5/2016.
 */
public class Trainer implements Serializable{

    private String name;
    private String encounteredPokemonName;

    private int score;

    private Vector<String> pokemonCaught = new Vector<String>(3,1);

    Trainer setName(String n){
        name = n;
        return this;
    }

    Trainer setScore(int i){
        score = i;
        return this;
    }

    void addScore(int i){
        score += i;
    }

    void encounterPokemon(Pokemon p){
        encounteredPokemonName = p.getName();
    }

    String getEncounteredPokemonName(){
        return encounteredPokemonName;
    }

    Trainer recordPokemonCaught(Pokemon  p){
        String caughtName = p.getName();
        for(String s: pokemonCaught){
            if(caughtName.equals(s)){
                return this;
            }
        }
        pokemonCaught.add(caughtName);
        addScore(p.getValue());
        return this;
    }

    String getListOfCaughtPokemon(){
        String s = new String();

        for(String name: pokemonCaught){
            s.concat("\n" + name);
        }

        return s;
    }

    String getName(){
        return name;
    }

    int getScore(){
        return score;
    }
}
