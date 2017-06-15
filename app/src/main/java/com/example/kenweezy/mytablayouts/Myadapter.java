package com.example.kenweezy.mytablayouts;

/**
 * Created by kennedy on 5/31/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Myadapter extends ArrayAdapter<Mymodel> {
    public Myadapter(Context context, ArrayList<Mymodel> mess) {
        super(context, 0, mess);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Mymodel mymess = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }
        // Lookup view for data population
        TextView addr = (TextView) convertView.findViewById(R.id.addr);
        TextView bdy = (TextView) convertView.findViewById(R.id.bdy);
        // Populate the data into the template view using the data object
        addr.setText(mymess.getAddress());
        bdy.setText(mymess.getBody());
        // Return the completed view to render on screen
        return convertView;
    }
}
