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

public class FragmentReportsYearly extends Fragment {
    HorizontalBarChart bct;
    View v;

    public static FragmentReportsYearly instance(){return (new FragmentReportsYearly());}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmentreportsyearly,container,false);
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
        bargroup1.add(new BarEntry(getVLSuppressed("2017"), 0));
        bargroup1.add(new BarEntry(getVLSuppressed("2016"), 1));
        bargroup1.add(new BarEntry(getVLSuppressed("2015"), 2));
        bargroup1.add(new BarEntry(getVLSuppressed("2014"), 3));
        bargroup1.add(new BarEntry(getVLSuppressed("2013"), 4));
        bargroup1.add(new BarEntry(getVLSuppressed("2012"), 5));
        bargroup1.add(new BarEntry(getVLSuppressed("2011"), 6));

        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup2(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getVLUnsuppressed("2017"), 0));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2016"), 1));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2015"), 2));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2014"), 3));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2013"), 4));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2012"), 5));
        bargroup2.add(new BarEntry(getVLUnsuppressed("2011"), 6));

        return bargroup2;
    }

    public ArrayList<BarEntry> getGroup4(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getSmsCountVL("2017"), 0));
        bargroup2.add(new BarEntry(getSmsCountVL("2016"), 1));
        bargroup2.add(new BarEntry(getSmsCountVL("2015"), 2));
        bargroup2.add(new BarEntry(getSmsCountVL("2014"), 3));
        bargroup2.add(new BarEntry(getSmsCountVL("2013"), 4));
        bargroup2.add(new BarEntry(getSmsCountVL("2012"), 5));
        bargroup2.add(new BarEntry(getSmsCountVL("2011"), 6));

        return bargroup2;
    }

    public ArrayList<BarEntry> getGroup3(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(getVLInvalid("2017"), 0));
        bargroup2.add(new BarEntry(getVLInvalid("2016"), 1));
        bargroup2.add(new BarEntry(getVLInvalid("2015"), 2));
        bargroup2.add(new BarEntry(getVLInvalid("2014"), 3));
        bargroup2.add(new BarEntry(getVLInvalid("2013"), 4));
        bargroup2.add(new BarEntry(getVLInvalid("2012"), 5));
        bargroup2.add(new BarEntry(getVLInvalid("2011"), 6));

        return bargroup2;
    }



    public void drawGraph(){

        BarDataSet barDataSet0 = new BarDataSet(getGroup4(), "All");
        barDataSet0.setColor(Color.rgb(255,255,0));

        // creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(getGroup1(), "Suppressed");

        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(getGroup2(), "Unsuppressed");
        barDataSet2.setColor(Color.rgb(0, 10, 60));

        BarDataSet barDataSet3 = new BarDataSet(getGroup3(), "Invalid");
        barDataSet2.setColor(Color.rgb(255, 0, 0));

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
//        bct.setDescriptionColor(R.color.colorPrimary);

        data.setValueFormatter(new Myformater());

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




    public int getVLInvalid(String mnth) {
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




                    if((messbdy.contains("Collect new sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){




                        String[] mymessarray=messbdy.split(":");


                        String splitVal="";

                        if(messbdy.contains("Sex") && messbdy.contains("Age")){
                            splitVal=mymessarray[6];

                        }
                        else{

                            splitVal=mymessarray[3];
                        }



                        String[] checkSplitdate=ndate.split("/");

                        String[] splitvalarray=splitVal.split("\\s+");

                        if(checkSplitdate.length>1){

                        }
                        else{
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(ndate));
                            ndate = formatter.format(calendar.getTime());

                        }

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
        }
        catch(Exception e){

        }

        return value;
    }





    public int getVLSuppressed2(String mnth) {
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


                    if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid"))){




                    }

                    String[] mymessarray=messbdy.split(":");


                    String splitVal="";

                    if(messbdy.contains("Sex") && messbdy.contains("Age")){
                        splitVal=mymessarray[6];

                    }
                    else{

                        splitVal=mymessarray[3];
                    }



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
                    String month=day[2];
//                    Log.i(TAG,month);
                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];

                    int myval=0;

                    if(splitvalarray[0].contentEquals("<")){

                        if(myyear.contentEquals(mnth)){

                            value+=1;
                        }



                    }

                    else{



                        myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>=1000){

                        }
                        else{
                            if(myyear.contentEquals(mnth)){

                                value+=1;
                            }


                        }


                    }


                }
//                hapa sasa


            }
//            hadi hapa










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




                    if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){




                        String[] mymessarray=messbdy.split(":");


                        String splitVal="";

                        if(messbdy.contains("Sex") && messbdy.contains("Age")){
                            splitVal=mymessarray[6];

                        }
                        else{

                            splitVal=mymessarray[3];
                        }



                        String[] checkSplitdate=ndate.split("/");

                        String[] splitvalarray=splitVal.split("\\s+");

                        if(checkSplitdate.length>1){

                        }
                        else{
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(ndate));
                            ndate = formatter.format(calendar.getTime());

                        }

                        String[] day=ndate.split("/");
                        String month=day[2];
//                    Log.i(TAG,month);
                        String[] newyear=month.split("\\s+");
                        String myyear=newyear[0];
                        if(myyear.contentEquals(mnth) && splitvalarray[0].contains("<")){

                            value+=1;
                        }

                        else{



                            int myval=Integer.parseInt(splitvalarray[0]);
                            if(myval>=1000){

                            }
                            else{
                                if(myyear.contentEquals(mnth)){

                                    value+=1;
                                }


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




                    if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){




                        String[] mymessarray=messbdy.split(":");


                        String splitVal="";

                        if(messbdy.contains("Sex") && messbdy.contains("Age")){
                            splitVal=mymessarray[6];

                        }
                        else{

                            splitVal=mymessarray[3];
                        }



                        String[] checkSplitdate=ndate.split("/");

                        String[] splitvalarray=splitVal.split("\\s+");

                        if(checkSplitdate.length>1){

                        }
                        else{
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(ndate));
                            ndate = formatter.format(calendar.getTime());

                        }

                        String[] day=ndate.split("/");
                        String month=day[2];
//                    Log.i(TAG,month);
                        String[] newyear=month.split("\\s+");
                        String myyear=newyear[0];
                        if(myyear.contentEquals(mnth) && splitvalarray[0].contains("<")){


                        }

                        else{



                            int myval=Integer.parseInt(splitvalarray[0]);
                            if(myval>=1000){

                                value+=1;

                            }
                            else{
                                if(myyear.contentEquals(mnth)){

//                                    value+=1;
                                }


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







}
