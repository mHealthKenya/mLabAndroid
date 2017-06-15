package com.example.kenweezy.mytablayouts;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by KENWEEZY on 2017-02-15.
 */

public class Myformater implements ValueFormatter {

    private DecimalFormat mFormat;

    public Myformater() {
        mFormat = new DecimalFormat("###,###,##0"); // use one decimal
    }
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return mFormat.format(value);// return mFormat.format(value) + " $";
    }


}


