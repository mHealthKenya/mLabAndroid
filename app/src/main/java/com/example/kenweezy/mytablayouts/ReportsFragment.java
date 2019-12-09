package com.example.kenweezy.mytablayouts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-02-02.
 */

public class ReportsFragment extends Fragment {
    View v;
    private float[] ydata = {20, 30};
    private String[] xdata = {"VLoad", "Eid"};
    PieChart pct;
    HorizontalBarChart bct;
    FrameLayout fl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.reportsfragmenteid, container, false);
//        pct=(PieChart) v.findViewById(R.id.pchart);

        bct = (HorizontalBarChart) v.findViewById(R.id.bchart);
        fl = (FrameLayout) v.findViewById(R.id.rcontent);

        drawGraph();


        return v;


    }


    public void drawGraph() {

        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(getXAxisValues(), getDataSet());
        bct.setData(data);
        bct.setDescription("");
        bct.setDescriptionPosition(90, 15);
        bct.setDescriptionTextSize(30);
        bct.getXAxis().setLabelRotationAngle(45);

        data.setValueFormatter(new Myformater());

        //set the legend

        Legend l = bct.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setFormSize(25f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used

//        barChart.animateX(5000, Easing.EasingOption.EaseInBounce);
        bct.animateY(3000, Easing.EasingOption.EaseInElastic);
        bct.invalidate();

        XAxis xLabels = bct.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xLabels.setAvoidFirstLastClipping(true);


    }


    private ArrayList<IBarDataSet> getDataSet() {

        BarDataSet barDataSet0 = new BarDataSet(bgrp4(), "All");
        barDataSet0.setColor(Color.rgb(255, 255, 0));

        BarDataSet barDataSet1 = new BarDataSet(bgrp1(), "Negative");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(bgrp2(), "Positive");
        barDataSet2.setColor(Color.rgb(20, 10, 60));

        BarDataSet barDataSet3 = new BarDataSet(bgrp3(), "Invalid");
        barDataSet3.setColor(Color.rgb(255, 0, 0));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet0);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        xAxis.add("JUL");
        xAxis.add("AUG");
        xAxis.add("SEP");
        xAxis.add("OCT");
        xAxis.add("NOV");
        xAxis.add("DEC");
        return xAxis;
    }
//    private ArrayList<BarEntry> bgrp2(){
//
//        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
//        bargroup2.add(new BarEntry(getSmsCountVL("01"), 0));
//        bargroup2.add(new BarEntry(getSmsCountVL("02"), 1));
//        bargroup2.add(new BarEntry(getSmsCountVL("03"), 2));
//        bargroup2.add(new BarEntry(getSmsCountVL("04"), 3));
//        bargroup2.add(new BarEntry(getSmsCountVL("05"), 4));
//        bargroup2.add(new BarEntry(getSmsCountVL("06"), 5));
//        bargroup2.add(new BarEntry(getSmsCountVL("07"), 6));
//        bargroup2.add(new BarEntry(getSmsCountVL("08"), 7));
//        bargroup2.add(new BarEntry(getSmsCountVL("09"), 8));
//        bargroup2.add(new BarEntry(getSmsCountVL("10"), 9));
//        bargroup2.add(new BarEntry(getSmsCountVL("11"), 10));
//        bargroup2.add(new BarEntry(getSmsCountVL("12"), 11));
//        return bargroup2;
//    }


    public void replaceContent(Fragment newfrag) {
        getChildFragmentManager().beginTransaction().replace(R.id.rcontent, newfrag).commit();
        bct.setVisibility(View.GONE);
        fl.setVisibility(View.VISIBLE);


    }

    public Fragment getFrag() {

        return getChildFragmentManager().findFragmentById(R.id.rcontent);
    }


    public int getSmsCountEID1(String mnth) {
        int counter = 0;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return 0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                DateFormat secformat = new SimpleDateFormat("ss");
                DateFormat mnthformat = new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth = mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        } catch (Exception e) {

        }

        return counter;
    }


    public int getSmsCountEID(String mnth) {
        int value = 0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {
                for (int x = 0; x < bdy.size(); x++) {

                    String ndate = bdy.get(x).getmTimeStamp();

                    String[] checkSplitdate = ndate.split("/");


                    if (checkSplitdate.length > 1) {

                    } else {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }


                    String[] day = ndate.split("/");
                    String month = day[1];
//                    Log.i(TAG,month);
                    if (month.contentEquals(mnth)) {

                        value += 1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        } catch (Exception e) {

        }

        return value;
    }


    public int getEIDNegative1(String mnth) {
        int counter = 0;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return 0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                DateFormat secformat = new SimpleDateFormat("ss");
                DateFormat mnthformat = new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth = mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Negative") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        } catch (Exception e) {

        }

        return counter;
    }

    public int getEIDNegative(String mnth) {
        int value = 0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {
                for (int x = 0; x < bdy.size(); x++) {

                    String ndate = bdy.get(x).getmTimeStamp();

                    String[] checkSplitdate = ndate.split("/");


                    if (checkSplitdate.length > 1) {

                    } else {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    String[] day = ndate.split("/");
                    String month = day[1];
//                    Log.i(TAG,month);
                    if (month.contentEquals(mnth)) {

                        value += 1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        } catch (Exception e) {

        }

        return value;
    }


    public int getEIDPositive1(String mnth) {
        int counter = 0;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return 0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                DateFormat secformat = new SimpleDateFormat("ss");
                DateFormat mnthformat = new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth = mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Positive") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        } catch (Exception e) {

        }

        return counter;
    }


    public int getEIDPositive(String mnth) {
        int value = 0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Positive%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {
                for (int x = 0; x < bdy.size(); x++) {

                    String ndate = bdy.get(x).getmTimeStamp();

                    String[] checkSplitdate = ndate.split("/");


                    if (checkSplitdate.length > 1) {

                    } else {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }


                    String[] day = ndate.split("/");
                    String month = day[1];
//                    Log.i(TAG,month);
                    if (month.contentEquals(mnth)) {

                        value += 1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        } catch (Exception e) {

        }

        return value;
    }


    public int getEIDInvalid1(String mnth) {
        int counter = 0;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return 0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                DateFormat secformat = new SimpleDateFormat("ss");
                DateFormat mnthformat = new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth = mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Invalid") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        } catch (Exception e) {

        }

        return counter;
    }

    public int getEIDInvalid(String mnth) {
        int value = 0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value = 0;

            } else {
                for (int x = 0; x < bdy.size(); x++) {

                    String ndate = bdy.get(x).getmTimeStamp();
                    String thebdy = bdy.get(x).getmBody();

                    String[] checkSplitdate = ndate.split("/");


                    if (checkSplitdate.length > 1) {

                    } else {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }


                    String[] day = ndate.split("/");
                    String month = day[1];
//                    Log.i(TAG,month);
                    if (month.contentEquals(mnth) && (thebdy.contains("Collect New Sample") || thebdy.contains("Collect new sexample") || thebdy.contains("Invalid"))) {

                        value += 1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        } catch (Exception e) {

        }

        return value;
    }


    public int getSmsCountVL1(String mnth) {
        int counter = 0;

        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return 0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                DateFormat secformat = new SimpleDateFormat("ss");
                DateFormat mnthformat = new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth = mnthformat.format(calendar.getTime());

                if (stw.contains("FFViral") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        } catch (Exception e) {

        }

        return counter;
    }
//
//    private ArrayList<BarEntry> bgrp1(){
//
//        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
//        bargroup1.add(new BarEntry(getSmsCountEID("01"), 0));
//        bargroup1.add(new BarEntry(getSmsCountEID("02"), 1));
//        bargroup1.add(new BarEntry(getSmsCountEID("03"), 2));
//        bargroup1.add(new BarEntry(getSmsCountEID("04"), 3));
//        bargroup1.add(new BarEntry(getSmsCountEID("05"), 4));
//        bargroup1.add(new BarEntry(getSmsCountEID("06"), 5));
//        bargroup1.add(new BarEntry(getSmsCountEID("07"), 6));
//        bargroup1.add(new BarEntry(getSmsCountEID("08"), 7));
//        bargroup1.add(new BarEntry(getSmsCountEID("09"), 8));
//        bargroup1.add(new BarEntry(getSmsCountEID("10"), 9));
//        bargroup1.add(new BarEntry(getSmsCountEID("11"), 10));
//        bargroup1.add(new BarEntry(getSmsCountEID("12"), 11));
//        return bargroup1;
//    }


    private ArrayList<BarEntry> bgrp2() {

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getEIDPositive("01"), 0));
        bargroup2.add(new BarEntry(getEIDPositive("02"), 1));
        bargroup2.add(new BarEntry(getEIDPositive("03"), 2));
        bargroup2.add(new BarEntry(getEIDPositive("04"), 3));
        bargroup2.add(new BarEntry(getEIDPositive("05"), 4));
        bargroup2.add(new BarEntry(getEIDPositive("06"), 5));
        bargroup2.add(new BarEntry(getEIDPositive("07"), 6));
        bargroup2.add(new BarEntry(getEIDPositive("08"), 7));
        bargroup2.add(new BarEntry(getEIDPositive("09"), 8));
        bargroup2.add(new BarEntry(getEIDPositive("10"), 9));
        bargroup2.add(new BarEntry(getEIDPositive("11"), 10));
        bargroup2.add(new BarEntry(getEIDPositive("12"), 11));
        return bargroup2;
    }


    private ArrayList<BarEntry> bgrp1() {

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getEIDNegative("01"), 0));
        bargroup1.add(new BarEntry(getEIDNegative("02"), 1));
        bargroup1.add(new BarEntry(getEIDNegative("03"), 2));
        bargroup1.add(new BarEntry(getEIDNegative("04"), 3));
        bargroup1.add(new BarEntry(getEIDNegative("05"), 4));
        bargroup1.add(new BarEntry(getEIDNegative("06"), 5));
        bargroup1.add(new BarEntry(getEIDNegative("07"), 6));
        bargroup1.add(new BarEntry(getEIDNegative("08"), 7));
        bargroup1.add(new BarEntry(getEIDNegative("09"), 8));
        bargroup1.add(new BarEntry(getEIDNegative("10"), 9));
        bargroup1.add(new BarEntry(getEIDNegative("11"), 10));
        bargroup1.add(new BarEntry(getEIDNegative("12"), 11));
        return bargroup1;
    }

    private ArrayList<BarEntry> bgrp4() {

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getSmsCountEID("01"), 0));
        bargroup1.add(new BarEntry(getSmsCountEID("02"), 1));
        bargroup1.add(new BarEntry(getSmsCountEID("03"), 2));
        bargroup1.add(new BarEntry(getSmsCountEID("04"), 3));
        bargroup1.add(new BarEntry(getSmsCountEID("05"), 4));
        bargroup1.add(new BarEntry(getSmsCountEID("06"), 5));
        bargroup1.add(new BarEntry(getSmsCountEID("07"), 6));
        bargroup1.add(new BarEntry(getSmsCountEID("08"), 7));
        bargroup1.add(new BarEntry(getSmsCountEID("09"), 8));
        bargroup1.add(new BarEntry(getSmsCountEID("10"), 9));
        bargroup1.add(new BarEntry(getSmsCountEID("11"), 10));
        bargroup1.add(new BarEntry(getSmsCountEID("12"), 11));
        return bargroup1;
    }


    private ArrayList<BarEntry> bgrp3() {

        ArrayList<BarEntry> bargroup3 = new ArrayList<>();
        bargroup3.add(new BarEntry(getEIDInvalid("01"), 0));
        bargroup3.add(new BarEntry(getEIDInvalid("02"), 1));
        bargroup3.add(new BarEntry(getEIDInvalid("03"), 2));
        bargroup3.add(new BarEntry(getEIDInvalid("04"), 3));
        bargroup3.add(new BarEntry(getEIDInvalid("05"), 4));
        bargroup3.add(new BarEntry(getEIDInvalid("06"), 5));
        bargroup3.add(new BarEntry(getEIDInvalid("07"), 6));
        bargroup3.add(new BarEntry(getEIDInvalid("08"), 7));
        bargroup3.add(new BarEntry(getEIDInvalid("09"), 8));
        bargroup3.add(new BarEntry(getEIDInvalid("10"), 9));
        bargroup3.add(new BarEntry(getEIDInvalid("11"), 10));
        bargroup3.add(new BarEntry(getEIDInvalid("12"), 11));
        return bargroup3;
    }

}
