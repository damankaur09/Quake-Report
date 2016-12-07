package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Daman on 06-Dec-2016.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>>
{
    private static final String LOG_TAG=EarthquakeLoader.class.getName();

    /*Query url*/
    String mUrl;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"Test:onStartLoading() called");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.i(LOG_TAG,"Test: loadInBackground() called");
        if(mUrl==null)
        {
            return null;
        }
        List<Earthquake> earthquakes=QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
