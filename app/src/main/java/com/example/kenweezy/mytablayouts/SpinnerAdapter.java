package com.example.kenweezy.mytablayouts;

/**
 * Created by kennedy on 8/17/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {
    Context context;

    String[] Options;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, String[] options) {
        this.context = applicationContext;

        this.Options = options;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Options.length;
    }

    @Override
    public Object getItem(int i) {

        return Options[i];

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_row, null);

        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        names.setText(Options[i]);
        String mys = Options[i];
        if (mys.contentEquals("please select an Option")) {

            names.setBackgroundResource(R.color.mycolor);
            names.setTextSize(19);

//            names.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (mys.contentEquals("Please select Security Question")) {
            names.setBackgroundResource(R.color.orangered);
            names.setTextSize(19);
            names.setTextColor(Color.WHITE);


        }


        return view;
    }
}


