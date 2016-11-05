package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake>
{
    //location separator
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthquakeArrayAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context,0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listView=convertView;

        Earthquake cuurentQuake=getItem(position);

        if(listView==null)
        {
            listView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }


        //Magnitude of Earthquake
        TextView magnitude=(TextView)listView.findViewById(R.id.magnitude_view);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle=(GradientDrawable)magnitude.getBackground();

        int magnitudeColor=getMagnitudeColor(cuurentQuake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        //use decimal formatter so that 0.0 value shown in this format
        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        String output=decimalFormat.format(cuurentQuake.getMagnitude());
        magnitude.setText(output);

        //Place of Earthquake
       //origional location of earthquake
        String origionalLoc=cuurentQuake.getPlace();

        //text view show distance of earthquake
        TextView primaryLoc=(TextView)listView.findViewById(R.id.km_view);
        //text view shows name of place where earthquake occurred
        TextView locationOffset=(TextView)listView.findViewById(R.id.place_view);

        if(origionalLoc.contains(LOCATION_SEPARATOR)) {

            String [] parts=origionalLoc.split(LOCATION_SEPARATOR);

            primaryLoc.setText(parts[0]+LOCATION_SEPARATOR);

            locationOffset.setText(parts[1]);
        }
        else
        {
            primaryLoc.setText("Near the");
            locationOffset.setText(origionalLoc);
        }

        //Date and Time of Earthquake
        //convert the time in milliseconds into a Date object by calling the Date constructor
        Date dateObject=new Date(cuurentQuake.getDateTime());
        //date
        TextView date=(TextView)listView.findViewById(R.id.date_view);

        // initialize a SimpleDateFormat instance and configure it to provide a more readable representation according to the given format.
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM DD, yyyy");
        String edate=dateFormat.format(dateObject);
        date.setText(edate);

        //time
        TextView time=(TextView)listView.findViewById(R.id.time_view);
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        String etime=timeFormat.format(dateObject);
        time.setText(etime);



        return listView;
    }
    private int getMagnitudeColor(double mag)
    {
        int colorID=-1;
        int magnitude=(int)Math.floor(mag);

        switch (magnitude){
            case 0:
                colorID=R.color.magnitude1;
                break;
            case 1:
                colorID=R.color.magnitude1;
                break;
            case 2:
                colorID=R.color.magnitude2;
                break;
            case 3:
                colorID=R.color.magnitude3;
                break;
            case 4:
                colorID=R.color.magnitude4;
                break;
            case 5:
                colorID=R.color.magnitude5;
                break;
            case 6:
                colorID=R.color.magnitude6;
                break;
            case 7:
                colorID=R.color.magnitude7;
                break;
            case 8:
                colorID=R.color.magnitude8;
                break;
            case 9:
                colorID=R.color.magnitude9;
                break;
            case 10:
                colorID=R.color.magnitude10plus;
                break;
        }
        System.out.println("colorID = " + colorID);
        return ContextCompat.getColor(getContext(),colorID);
    }
}
