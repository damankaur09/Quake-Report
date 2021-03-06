/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>>,SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private TextView emptyView;


    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */

    private final static  int EARTHQUAKE_LOADER_ID=1;

    /** Sample JSON response for a USGS query */
    private static final String SAMPLE_JSON_RESPONSE =

            "http://earthquake.usgs.gov/fdsnws/event/1/query";
    // Create a new {@link ArrayAdapter} of earthquakes
    EarthquakeArrayAdapter adapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG,"Test: EarthquakeActivity onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        emptyView=(TextView)findViewById(R.id.empty_view);

        earthquakeListView.setEmptyView(emptyView);

        adapter=new EarthquakeArrayAdapter(this,new ArrayList<Earthquake>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        prefs.registerOnSharedPreferenceChangeListener(this);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake=adapter.getItem(position);
                String url=earthquake.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        // Before attempting to fetch the URL, makes sure that there is a network connection.
        ConnectivityManager congmgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=congmgr.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager=getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG,"Test: initLoader() called");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
        }
        else
        {
            //hide loading indicator
            View loadingIndicator=findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            emptyView.setText("No Internet Connection");
        }



    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.settings_min_magnitude_key))||key.equals(getString(R.string.settings_order_by_key)))
        {
            adapter.clear();

            emptyView.setVisibility(View.GONE);

            // Show the loading indicator while new data is being fetched
            View loadingIndicator=findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.VISIBLE);

            // Restart the loader to requery the USGS as the query settings have been updated
            getLoaderManager().restartLoader(EARTHQUAKE_LOADER_ID,null,this);
        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"Test: EarthquakeActivity onCreateLoader() called");
        SharedPreferences sharedPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        String minMag=sharedPrefs.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.settings_min_magnitude_default));

        String orderBy=sharedPrefs.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));

        Uri baseUri=Uri.parse(SAMPLE_JSON_RESPONSE);
        Uri.Builder uriBuilder=baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format","geojson");
        uriBuilder.appendQueryParameter("limit","10");
        uriBuilder.appendQueryParameter("minmag",minMag);
        uriBuilder.appendQueryParameter("orderby",orderBy);

        return new EarthquakeLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.i(LOG_TAG,"Test: EarthquakeActivity onLoadFinished() called");

        //hide loading indicator
        View loadingIndicator=findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        emptyView.setText(R.string.no_earthquake);
        adapter.clear();

        if(data!=null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i(LOG_TAG,"Test: EarthquakeActivity onLoadReset() called");
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.actions_settings)
        {
            Intent settingsIntent=new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

