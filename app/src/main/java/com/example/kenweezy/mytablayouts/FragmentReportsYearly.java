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
    int Graphyear = Calendar.getInstance().get(Calendar.YEAR);

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
        for(int myx=0;myx<=3;myx++){

            labels.add(Integer.toString(Graphyear-myx));

        }

        return labels;


    }

    public ArrayList<BarEntry> getGroup1(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();

        for(int myx=0;myx<=3;myx++){

            bargroup1.add(new BarEntry(getVLSuppressed(Integer.toString(Graphyear-myx)), myx));
        }

        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup2(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();

        for(int myx=0;myx<=3;myx++){

            bargroup2.add(new BarEntry(getVLUnsuppressed(Integer.toString(Graphyear-myx)), myx));
        }

        return bargroup2;
    }

    public ArrayList<BarEntry> getGroup4(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();

        for(int myx=0;myx<=3;myx++){

            bargroup2.add(new BarEntry(getSmsCountVL(Integer.toString(Graphyear-myx)), myx));


        }

        return bargroup2;
    }

    public ArrayList<BarEntry> getGroup3(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();

        for(int myx=0;myx<=3;myx++){

            bargroup2.add(new BarEntry(getVLInvalid(Integer.toString(Graphyear-myx)), myx));

        }

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
        bct.setDescription("");
        bct.setDescriptionPosition(90,15);
        bct.setDescriptionTextSize(30);
        bct.setDescriptionColor(R.color.colorPrimary);

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



    public int getVLSuppressed(String mnth){
        int counter=0;


        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);


        for(int x=0;x<bdy.size();x++){

            try{


                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();

                if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))) {


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

                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];


                    if(splitvalarray[0].contains("<")){
                        System.out.println("i am suppressed "+splitvalarray[0]);
                        if(myyear.contentEquals(mnth)){

                            counter += 1;
                        }



                    }

                    else{



                        int myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>1000){
                            System.out.println("i am unsuppressed with a value "+myval);




                        }
                        else{
                            System.out.println("i am suppressed with a value "+myval);
                            if(myyear.contentEquals(mnth)){

                                counter += 1;
                            }


                        }


                    }


                }


            }
            catch(Exception e){

                System.out.println("exception occured "+e);

            }


        }
        return counter;
    }




    public int getVLUnsuppressed(String mnth){
        int counter=0;


        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);


        for(int x=0;x<bdy.size();x++){

            try{


                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();

                if(!(messbdy.contains("Collect new sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))) {


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


                    String[] newyear=month.split("\\s+");
                    String myyear=newyear[0];

                    if(splitvalarray[0].contains("<")){
                        System.out.println("i am suppressed "+splitvalarray[0]);



                    }

                    else{



                        int myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>1000){
                            System.out.println("i am unsuppressed with a value "+myval);
                            if(myyear.contentEquals(mnth)){

                                counter += 1;
                            }



                        }
                        else{
                            System.out.println("i am suppressed with a value "+myval);


                        }


                    }


                }


            }
            catch(Exception e){

                System.out.println("exception occured "+e);

            }


        }
        return counter;
    }








}
