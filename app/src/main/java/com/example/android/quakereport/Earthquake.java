package com.example.android.quakereport;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class Earthquake
{
    private String magnitude;
    private String place;
    private int time;

    public Earthquake(String magnitude, String place, int time) {
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public int getTime() {
        return time;
    }
}
