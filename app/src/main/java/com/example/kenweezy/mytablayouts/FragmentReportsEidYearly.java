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

public class FragmentReportsEidYearly extends Fragment {
    HorizontalBarChart bct;
    View v;

    public static FragmentReportsEidYearly instance(){return (new FragmentReportsEidYearly());}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmentreportseidyearly,container,false);
        bct=(HorizontalBarChart) v.findViewById(R.id.ybchart);

        drawGraph();

        return v;

    }

    public ArrayList<String> getLabels(){

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2017");
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        return labels;


    }

    public ArrayList<BarEntry> getGroup1(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getEIDNegative("2017"), 0));
        bargroup1.add(new BarEntry(getEIDNegative("2016"), 1));
        bargroup1.add(new BarEntry(getEIDNegative("2015"), 2));
        bargroup1.add(new BarEntry(getEIDNegative("2014"), 3));
        bargroup1.add(new BarEntry(getEIDNegative("2013"), 4));
        bargroup1.add(new BarEntry(getEIDNegative("2012"), 5));
        bargroup1.add(new BarEntry(getEIDNegative("2011"), 6));

        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup4(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(getSmsCountEID("2017"), 0));
        bargroup1.add(new BarEntry(getSmsCountEID("2016"), 1));
        bargroup1.add(new BarEntry(getSmsCountEID("2015"), 2));
        bargroup1.add(new BarEntry(getSmsCountEID("2014"), 3));
        bargroup1.add(new BarEntry(getSmsCountEID("2013"), 4));
        bargroup1.add(new BarEntry(getSmsCountEID("2012"), 5));
        bargroup1.add(new BarEntry(getSmsCountEID("2011"), 6));

        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup2(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getEIDPositive("2017"), 0));
        bargroup2.add(new BarEntry(getEIDPositive("2016"), 1));
        bargroup2.add(new BarEntry(getEIDPositive("2015"), 2));
        bargroup2.add(new BarEntry(getEIDPositive("2014"), 3));
        bargroup2.add(new BarEntry(getEIDPositive("2013"), 4));
        bargroup2.add(new BarEntry(getEIDPositive("2012"), 5));
        bargroup2.add(new BarEntry(getEIDPositive("2011"), 6));

        return bargroup2;
    }

    public ArrayList<BarEntry> getGroup3(){

        ArrayList<BarEntry> bargroup3 = new ArrayList<>();
        bargroup3.add(new BarEntry(getEIDInvalid("2017"), 0));
        bargroup3.add(new BarEntry(getEIDInvalid("2016"), 1));
        bargroup3.add(new BarEntry(getEIDInvalid("2015"), 2));
        bargroup3.add(new BarEntry(getEIDInvalid("2014"), 3));
        bargroup3.add(new BarEntry(getEIDInvalid("2013"), 4));
        bargroup3.add(new BarEntry(getEIDInvalid("2012"), 5));
        bargroup3.add(new BarEntry(getEIDInvalid("2011"), 6));

        return bargroup3;
    }



    public void drawGraph(){
        BarDataSet barDataSet0 = new BarDataSet(getGroup4(), "All");
        barDataSet0.setColor(Color.rgb(255,255,0));

        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(getGroup1(), "Negative");

        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(getGroup2(), "Positive");
        barDataSet2.setColor(Color.rgb(0, 10, 60));

        BarDataSet barDataSet3 = new BarDataSet(getGroup3(), "Invalid");
        barDataSet3.setColor(Color.rgb(255, 0, 0));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet0);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(getLabels(), dataSets);
        bct.setData(data);
        bct.setDescription("Yearly");
        bct.setDescriptionPosition(90,15);
        bct.setDescriptionTextSize(30);
        data.setValueFormatter(new Myformater());
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





    public int getSmsCountEID(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String[] day=ndate.split("/");
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];
                    if(myyear.contentEquals(mnth)){

                        value+=1;
                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }



    public int getSmsCountEID2(String mnth){
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
                DateFormat mnthformat=new SimpleDateFormat("yyyy");
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

    public int getEIDPositived(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Positive%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String[] day=ndate.split("/");
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];
                    if(myyear.contentEquals(mnth)){

                        value+=1;
                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }


    public int getSmsCountVL1(String mnth){
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
                DateFormat mnthformat=new SimpleDateFormat("yyyy");
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


    public int getEIDNegative(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String[] day=ndate.split("/");
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];
                    if(myyear.contentEquals(mnth)){

                        value+=1;
                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }



    public int getEIDNegativet(String mnth){
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
                DateFormat mnthformat=new SimpleDateFormat("yyyy");
                Calendar calendar = Calendar.getInstance();

                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth=mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Negative") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }

        catch(Exception e){

        }

        return counter;
    }


    public int getEIDPositivel(String mnth){
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
                DateFormat mnthformat=new SimpleDateFormat("yyyy");
                Calendar calendar = Calendar.getInstance();

                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth=mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Positive") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }

        catch(Exception e){

        }

        return counter;
    }


    public int getEIDPositive(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Positive%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String[] day=ndate.split("/");
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];
                    if(myyear.contentEquals(mnth)){

                        value+=1;
                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }




    public int getEIDInvalid2(String mnth){
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
                DateFormat mnthformat=new SimpleDateFormat("yyyy");
                Calendar calendar = Calendar.getInstance();

                String ndate =  smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date"));
                Long timestamp = Long.parseLong(ndate);
                long now = timestamp;
                calendar.setTimeInMillis(now);

                String mymnth=mnthformat.format(calendar.getTime());

                if (stw.contains("FFEID") && stw.contains("Invalid") && mnth.contentEquals(mymnth)) {
                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }

        catch(Exception e){

        }

        return counter;
    }


    public int getEIDInvalid(String mnth) {
        int value=0;
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty()) {
                value=0;

            }
            else{
                for(int x=0;x<bdy.size();x++){

                    String ndate = bdy.get(x).getmTimeStamp();
                    String thebdy=bdy.get(x).getmBody();

                    String[] day=ndate.split("/");
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];
                    if(myyear.contentEquals(mnth) && (thebdy.contains("Collect new sample")|| thebdy.contains("Invalid"))){

                        value+=1;
                    }


                }


            }
        }
        catch(Exception e){

        }

        return value;
    }




}
