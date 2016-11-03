package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        TextView place=(TextView)listView.findViewById(R.id.place_view);

        place.setText(cuurentQuake.getPlace());

        TextView date=(TextView)listView.findViewById(R.id.date_view);

        date.setText(cuurentQuake.getDate());



        return listView;
    }
}
