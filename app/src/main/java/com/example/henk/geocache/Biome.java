package com.example.henk.geocache;

/**
 * Created by Paolo on 6/3/2016.
 */
public class Biome {

    public enum Type{
        FRESH_WATER,
        URBAN,
        GRASSY,
        RARE
    }

    public final class Instance{
        private String Name;
        private double latitude;
        private double longitude;
        private double radius_meters;
        private Type type;

        Instance setName(String n){
            Name = n;
            return this;
        }

        Instance setRadius(double r){
            radius_meters = r;
            return this;
        }

        Instance setLongitude(double l){
            longitude = l;
            return this;
        }

        Instance setLatitude(double l){
            latitude = l;
            return this;
        }

        Instance setType(Type t){
            type = t;
            return this;
        }
    }

    final Instance ALDRICH_PARK = new Instance()
            .setName("Aldrich Park")
            .setRadius(147.1763)
            .setLatitude(33.64979)
            .setLongitude(-117.842681)
            .setType(Type.GRASSY);




}
