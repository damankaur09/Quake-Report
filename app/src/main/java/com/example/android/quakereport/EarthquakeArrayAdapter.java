package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daman on 03-Nov-2016.
 */
public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake>
{
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



        TextView magnitude=(TextView)listView.findViewById(R.id.magnitude_view);

        magnitude.setText(cuurentQuake.getMagnitude());

        TextView km=(TextView)listView.findViewById(R.id.km_view);
        TextView eplace=(TextView)listView.findViewById(R.id.place_view);
        String location=cuurentQuake.getPlace();
        if(location.contains("of")) {
            int index=location.indexOf("of");

            km.setText(location.substring(0,index+2));

            eplace.setText(location.substring(index+2,location.length()-1));
        }
        else
        {
            km.setText("Near the");
            eplace.setText(location);
        }


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
}
