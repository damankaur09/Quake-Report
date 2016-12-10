package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class QueryUtils
{


    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return the list of objects
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        //create a url
        URL url=createUrl(requestUrl);

        //Perform HTTP Request to the URL and get the JSON response

        String jsonResponse=null;
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Problem making the http request",e);
        }

        List<Earthquake> earthquakes=extractFeatureFromJSON(jsonResponse);
        //Collections.sort(earthquakes);

        return earthquakes;
    }

    /**
     * Return URL object from the given string
     */

    public static URL createUrl(String stringUrl)
    {
        URL url=null;
        try {
            url=new URL(stringUrl);
        }
        catch(MalformedURLException e)
        {
            Log.e(LOG_TAG,"Problem building the URL",e);
        }

        return url;
    }
    /**
     * Make a http request for given url and return a string as a response
     */
    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        if(url==null)
        {
            return jsonResponse;
        }

        //open a url connection
        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"Error response code"+urlConnection.getResponseCode());
            }

        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Read data through input stream and convert it into String which contains whole jsonresponse from the server
     */

    public static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        try {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while(line!=null)
                {
                    output.append(line);
                    line=bufferedReader.readLine();
                }

            }
        } catch (IOException e){


        }
        return output.toString() ;
    }
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractFeatureFromJSON(String earthquakeJSON) {

        if(TextUtils.isEmpty(earthquakeJSON))
        {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root=new JSONObject(earthquakeJSON);

            JSONArray feature=root.getJSONArray("features");

            for(int i=0;i<feature.length();i++)
            {
                JSONObject prop=feature.getJSONObject(i);
                JSONObject a=prop.getJSONObject("properties");
                double magnitude=Double.parseDouble(a.getString("mag"));
                String place=a.getString("place");
                long milliseconds=a.getLong("time");
                String url=a.getString("url");

                earthquakes.add(new Earthquake(magnitude,place,milliseconds,url));
            }




        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}

