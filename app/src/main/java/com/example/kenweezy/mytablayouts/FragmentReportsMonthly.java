package com.example.kenweezy.mytablayouts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
 * Created by KENWEEZY on 2017-02-07.
 */

public class FragmentReportsMonthly extends Fragment {
    HorizontalBarChart bct;
    View v;

    public static FragmentReportsMonthly instance(){return (new FragmentReportsMonthly());}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmentreportsmonthly,container,false);
        bct=(HorizontalBarChart) v.findViewById(R.id.mbchart);

        drawGraph();




        return v;

    }

    public void drawGraph(){


        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(getXAxisValues(), getDataSet());
        bct.setData(data);
        bct.setDescription("Monthly");
        bct.setDescriptionPosition(90,15);
        bct.setDescriptionTextSize(30);
        bct.getXAxis().setLabelRotationAngle(45);

        data.setValueFormatter(new Myformater());
//       bct.setScaleMinima(0f,0f);
//        bct.setScaleX(2f);
//        bct.setScaleY(2f);
//        data.setGroupSpace(data.getGroupSpace()*5);
//        bct.setMinimumWidth(100);
//        data.setGroupSpace(100);
//        bct.setDescriptionColor(R.color.colorPrimary);

        //set the legend

        Legend l=bct.getLegend();
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
        barDataSet0.setColor(Color.rgb(255,255,0));
        BarDataSet barDataSet1 = new BarDataSet(bgrp1(), "Suppressed");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(bgrp2(), "Unsuppressed");
        barDataSet2.setColor(Color.rgb(0, 10, 60));
        BarDataSet barDataSet3 = new BarDataSet(bgrp3(), "Invalid");
        barDataSet3.setColor(Color.rgb(255, 0, 0));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet0);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

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
    private ArrayList<BarEntry> bgrp2(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getVLUnsuppressed("01"), 0));
        bargroup2.add(new BarEntry(getVLUnsuppressed("02"), 1));
        bargroup2.add(new BarEntry(getVLUnsuppressed("03"), 2));
        bargroup2.add(new BarEntry(getVLUnsuppressed("04"), 3));
        bargroup2.add(new BarEntry(getVLUnsuppressed("05"), 4));
        bargroup2.add(new BarEntry(getVLUnsuppressed("06"), 5));
        bargroup2.add(new BarEntry(getVLUnsuppressed("07"), 6));
        bargroup2.add(new BarEntry(getVLUnsuppressed("08"), 7));
        bargroup2.add(new BarEntry(getVLUnsuppressed("09"), 8));
        bargroup2.add(new BarEntry(getVLUnsuppressed("10"), 9));
        bargroup2.add(new BarEntry(getVLUnsuppressed("11"), 10));
        bargroup2.add(new BarEntry(getVLUnsuppressed("12"), 11));
        return bargroup2;
    }


    private ArrayList<BarEntry> bgrp4(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getSmsCountVL("01"), 0));
        bargroup2.add(new BarEntry(getSmsCountVL("02"), 1));
        bargroup2.add(new BarEntry(getSmsCountVL("03"), 2));
        bargroup2.add(new BarEntry(getSmsCountVL("04"), 3));
        bargroup2.add(new BarEntry(getSmsCountVL("05"), 4));
        bargroup2.add(new BarEntry(getSmsCountVL("06"), 5));
        bargroup2.add(new BarEntry(getSmsCountVL("07"), 6));
        bargroup2.add(new BarEntry(getSmsCountVL("08"), 7));
        bargroup2.add(new BarEntry(getSmsCountVL("09"), 8));
        bargroup2.add(new BarEntry(getSmsCountVL("10"), 9));
        bargroup2.add(new BarEntry(getSmsCountVL("11"), 10));
        bargroup2.add(new BarEntry(getSmsCountVL("12"), 11));
        return bargroup2;
    }


    private ArrayList<BarEntry> bgrp1(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getVLSuppressed("01"), 0));
        bargroup1.add(new BarEntry(getVLSuppressed("02"), 1));
        bargroup1.add(new BarEntry(getVLSuppressed("03"), 2));
        bargroup1.add(new BarEntry(getVLSuppressed("04"), 3));
        bargroup1.add(new BarEntry(getVLSuppressed("05"), 4));
        bargroup1.add(new BarEntry(getVLSuppressed("06"), 5));
        bargroup1.add(new BarEntry(getVLSuppressed("07"), 6));
        bargroup1.add(new BarEntry(getVLSuppressed("08"), 7));
        bargroup1.add(new BarEntry(getVLSuppressed("09"), 8));
        bargroup1.add(new BarEntry(getVLSuppressed("10"), 9));
        bargroup1.add(new BarEntry(getVLSuppressed("11"), 10));
        bargroup1.add(new BarEntry(getVLSuppressed("12"), 11));
        return bargroup1;
    }

    private ArrayList<BarEntry> bgrp3(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getVLInvalid("01"), 0));
        bargroup1.add(new BarEntry(getVLInvalid("02"), 1));
        bargroup1.add(new BarEntry(getVLInvalid("03"), 2));
        bargroup1.add(new BarEntry(getVLInvalid("04"), 3));
        bargroup1.add(new BarEntry(getVLInvalid("05"), 4));
        bargroup1.add(new BarEntry(getVLInvalid("06"), 5));
        bargroup1.add(new BarEntry(getVLInvalid("07"), 6));
        bargroup1.add(new BarEntry(getVLInvalid("08"), 7));
        bargroup1.add(new BarEntry(getVLInvalid("09"), 8));
        bargroup1.add(new BarEntry(getVLInvalid("10"), 9));
        bargroup1.add(new BarEntry(getVLInvalid("11"), 10));
        bargroup1.add(new BarEntry(getVLInvalid("12"), 11));
        return bargroup1;
    }


    public int getSmsCountEID(String mnth){
        int counter=0;

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
                DateFormat secformat=new SimpleDateFormat("ss");
                DateFormat mnthformat=new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth=mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }

        catch(Exception e){

        }

        return counter;
    }


    public int getSmsCountVL2(String mnth){
        int counter=0;

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
                DateFormat secformat=new SimpleDateFormat("ss");
                DateFormat mnthformat=new SimpleDateFormat("MM");
                Calendar calendar = Calendar.getInstance();

                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth=mnthformat.format(calendar.getTime());

                if (stw.contains("FFViral") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }

        catch(Exception e){

        }

        return counter;
    }


//
//    public int getVLInvalid(String mnth){
//        int counter=0;
//
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return 0;
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//
//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//                DateFormat secformat=new SimpleDateFormat("ss");
//                DateFormat mnthformat=new SimpleDateFormat("MM");
//                Calendar calendar = Calendar.getInstance();
//
//                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
//                Long timestamp = Long.parseLong(ndate);
//                long now = timestamp;
//                calendar.setTimeInMillis(now);
//
//                String mymnth=mnthformat.format(calendar.getTime());
//
//                if (stw.contains("FFViral") && stw.contains("Invalid") && mnth.contentEquals(mymnth)) {
//                    counter += 1;
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//
//        catch(Exception e){
//
//        }
//
//        return counter;
//    }


    public int getVLInvalid(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%Collect new sample%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();

                    String[] checkSplitdate=ndate.split("/");


                    if(checkSplitdate.length>1){

                    }
                    else{
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    String[] day=ndate.split("/");
                    String month=day[1];
//                    Log.i(TAG,month);
                    if(month.contentEquals(mnth)){

                        value+=1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        }
        catch(Exception e){

        }

        return value;
    }

    public int getVLSuppressed(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String messbdy=bdy.get(x).getmBody();

                    String[] mymessarray=messbdy.split(":");
                    String splitVal=mymessarray[6];
                    String[] splitvalarray=splitVal.split("\\s+");

                    String[] checkSplitdate=ndate.split("/");


                    if(checkSplitdate.length>1){

                    }
                    else{
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    String[] day=ndate.split("/");
                    String month=day[1];
                    int myval=0;

                    if(splitvalarray[0].contentEquals("<")){

                        if(month.contentEquals(mnth)){

                            value+=1;
                        }



                    }

                    else{
                        myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>=1000){

                        }
                        else{
                            if(month.contentEquals(mnth)){

                                value+=1;
                            }


                        }

                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }



    public int getVLUnsuppressed(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String messbdy=bdy.get(x).getmBody();

                    String[] mymessarray=messbdy.split(":");
                    String splitVal=mymessarray[6];
                    String[] splitvalarray=splitVal.split("\\s+");

                    String[] checkSplitdate=ndate.split("/");


                    if(checkSplitdate.length>1){

                    }
                    else{
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    String[] day=ndate.split("/");
                    String month=day[1];
                    int myval=0;

                    if(splitvalarray[0].contentEquals("<")){
                        System.out.println("i am suppressed "+splitvalarray[0]);



                    }

                    else{
                        myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>=1000){
                            if(month.contentEquals(mnth)){

                                value+=1;
                            }
                        }
                        else{
                            System.out.println("i am suppressed with a value "+myval);


                        }

                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }


//    public int getVLSuppressed(String mnth){
//        int counter=0;
//
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return 0;
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//
//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//                DateFormat secformat=new SimpleDateFormat("ss");
//                DateFormat mnthformat=new SimpleDateFormat("MM");
//                Calendar calendar = Calendar.getInstance();
//
//                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
//                Long timestamp = Long.parseLong(ndate);
//                long now = timestamp;
//                calendar.setTimeInMillis(now);
//
//                String mymnth=mnthformat.format(calendar.getTime());
//
//                if (stw.contains("FFViral") && stw.contains("LDL") && mnth.contentEquals(mymnth)) {
//                    counter += 1;
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//
//        catch(Exception e){
//
//        }
//
//        return counter;
//    }




    public int getSmsCountVL(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    System.out.println("the date is.........."+ndate);

                    String[] checkSplitdate=ndate.split("/");


                    if(checkSplitdate.length>1){

                    }
                    else{
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    String[] day=ndate.split("/");
                    String month=day[1];
                    System.out.println("the month is.........."+month);
                    if(month.contentEquals(mnth)){

                        value+=1;
                    }
//                    String[] newyear=year.split("\\s+");
//                    String myyear=newyear[0];

                }


            }
        }
        catch(Exception e){

        }

        return value;
    }



//    public int getVLUnsuppressed(String mnth){
//        int counter=0;
//
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return 0;
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//
//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
//                DateFormat secformat=new SimpleDateFormat("ss");
//                DateFormat mnthformat=new SimpleDateFormat("MM");
//                Calendar calendar = Calendar.getInstance();
//
//                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
//                Long timestamp = Long.parseLong(ndate);
//                long now = timestamp;
//                calendar.setTimeInMillis(now);
//
//                String mymnth=mnthformat.format(calendar.getTime());
//
//                if (stw.contains("FFViral")&& (!stw.contains("LDL"))&&!stw.contains("Invalid") && mnth.contentEquals(mymnth)) {
//                    counter += 1;
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//
//        catch(Exception e){
//
//        }
//
//        return counter;
//    }
//
}
