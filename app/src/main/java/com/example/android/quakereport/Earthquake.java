package com.example.android.quakereport;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class Earthquake
{
    private double magnitude;
    private String place;
    private long datetime;
    private String url;



    public Earthquake(double magnitude, String place, long datetime,String url) {
        this.place = place;
        this.magnitude = magnitude;
        this.datetime = datetime;
        this.url=url;

    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getDateTime() {
        return datetime;
    }

    public String getUrl() {
        return url;
    }
}
