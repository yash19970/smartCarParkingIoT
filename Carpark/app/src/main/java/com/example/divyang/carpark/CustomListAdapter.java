package com.example.divyang.carpark;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gauri on 25/03/18.
 */

public class CustomListAdapter extends ArrayAdapter {
    //to store the list of countries
    final Activity context;
    final ArrayList<String> location;
     final ArrayList<String> inTime;
     final ArrayList<String> outTime;


    public CustomListAdapter( Activity context, ArrayList<String> location, ArrayList<String> inTime, ArrayList<String> outTime) {
        super(context, R.layout.listview_row,location);

        this.context = context;
        this.location = location;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView locationTextField = (TextView) rowView.findViewById(R.id.tv1);
        TextView inTimeTextField = (TextView) rowView.findViewById(R.id.tv2);
        TextView outTimeTextField = (TextView) rowView.findViewById(R.id.tv3);
        //this code sets the values of the objects to values from the arrays
        locationTextField.setText(location.get(position));
        inTimeTextField.setText(inTime.get(position));
        outTimeTextField.setText(outTime.get(position));

        return rowView;

    };
}
