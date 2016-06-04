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

        double getLatitude(){
            return latitude;
        }

        double getRadius(){
            return radius_meters;
        }

        double getLongitude(){
            return longitude;
        }

        Type getType(){
            return type;
        }
    }

    public final Instance ALDRICH_PARK = new Instance()
            .setName("UCI: Aldrich Park")
            .setRadius(150.00000)
            .setLatitude(33.64979)
            .setLongitude(-117.842681)
            .setType(Type.GRASSY);

    public final Instance ENGINEERING = new Instance()
            .setName("UCI: Henry Samueli School of Engineering")
            .setLatitude(33.643788)
            .setLongitude(-117.840817)
            .setRadius(100.0000)
            .setType(Type.URBAN);

    public final Instance STUDENT_CENTER = new Instance()
            .setName("UCI: Student Center")
            .setLatitude(33.648661)
            .setLongitude(-117.842395)
            .setRadius(140.0000)
            .setType(Type.URBAN);
}
