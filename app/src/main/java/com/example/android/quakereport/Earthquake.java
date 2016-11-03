package com.example.android.quakereport;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class Earthquake
{
    private String magnitude;
    private String place;
    private String date;

    public Earthquake(String magnitude, String place, String date) {
        this.place = place;
        this.magnitude = magnitude;
        this.date = date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }
}
