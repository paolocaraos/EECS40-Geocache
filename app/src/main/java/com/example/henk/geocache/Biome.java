package com.example.henk.geocache;

import android.location.Location;

import com.google.android.gms.location.Geofence;

/**
 * Created by Paolo on 6/3/2016.
 */
public class Biome{

    public enum Type{
        FRESH_WATER,
        URBAN,
        GRASSY,
        RARE
    }

    public static class Constants{
        static final double UCI_LAT = 33.645928;
        static final double UCI_LONG = -117.842820;

        static final double STUDENT_CENTER_LAT = 33.648661;
        static final double STUDENT_CENTER_LONG = -117.842395;
        static final float STUDENT_CENTER_RADIUS_IN_METERS = 135;
        static final Type STUDENT_CENTER_BIOME_TYPE = Type.URBAN;

        static final double ALDRICH_PARK_LAT = 33.64979;
        static final double ALDRICH_PARK_LONG = -117.842681;
        static final float ALDRICH_PARK_RADIUS_IN_METERS = 144;
        static final Type ALDRICH_PARK_BIOME_TYPE = Type.GRASSY;

        static final double ENGINEERING_LAT = 33.643788;
        static final double ENGINEERING_LONG = -117.840817;
        static final float ENGINEERING_RADIUS_IN_METERS = 100;
        static final Type ENGINEERING_BIOME_TYPE = Type.URBAN;

        static final double INFINITY_FOUNTAIN_LAT = 33.644593;
        static final double INFINITY_FOUNTAIN_LONG = -117.843529;
        static final float INFINITY_FOUNTAIN_RADIUS_IN_METERS = 15;
        static final Type INFINITY_FOUNTAIN_BIOME_TYPE = Type.FRESH_WATER;
    }

    private String Name;
    private double longitude;
    private double latitude;
    private Location location;
    private float radius_meters;
    private Type type;

    private Geofence geofence;

    void setName(String n){
        Name = n;
    }

    void setRadius(float r){
        radius_meters = r;
    }

    void setLongitude(double l){
        longitude = l;
    }

    void setLatitude(double l){
        latitude = l;
    }

    void setType(Type t){
        type = t;
    }

    void setLocation(Location l){
        location = l;
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

    Location getLocation(){return location;}

    String getName(){
        return Name;
    }

    Type getType(){
        return type;
    }

    public static class Builder {
        private String Name;
        private double longitude;
        private double latitude;
        private float radius_meters;
        private Type type;

        Builder setName(String n){
            this.Name = n;
            return this;
        }

        Builder setRadius(float r){
            this.radius_meters = r;
            return this;
        }

        Builder setLongitude(double l){
            this.longitude = l;
            return this;
        }

        Builder setLatitude(double l){
            this.latitude = l;
            return this;
        }

        Builder setType(Type t){
            this.type = t;
            return this;
        }

        Biome build(){
            Biome i = new Biome();
            Location loc = new Location(Name);
            loc.setLongitude(longitude);
            loc.setLatitude(latitude);

            i.setName(Name);
            i.setLatitude(latitude);
            i.setLongitude(longitude);
            i.setRadius(radius_meters);
            i.setType(type);

            i.setLocation(loc);
            return i;
        }
    }

}
