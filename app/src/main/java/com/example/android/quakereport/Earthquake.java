package com.example.android.quakereport;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class Earthquake
{
    private String magnitude;
    private String place;
    private long datetime;

    public Earthquake(String magnitude, String place, long datetime) {
        this.place = place;
        this.magnitude = magnitude;
        this.datetime = datetime;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getDateTime() {
        return datetime;
    }
}
