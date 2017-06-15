package com.example.kenweezy.mytablayouts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

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

public class FragmentReportsEidWeekly extends Fragment {
    HorizontalBarChart bct;
    View v;
    Spinner myspinner,yearspinner;
    int year=0;
    int month=0;

    public static FragmentReportsEidWeekly instance(){return (new FragmentReportsEidWeekly());}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmentreportseidweekly,container,false);
        bct=(HorizontalBarChart) v.findViewById(R.id.wbchart);
        myspinner=(Spinner) v.findViewById(R.id.mnthspin);
        yearspinner=(Spinner) v.findViewById(R.id.yearspin);
        myspinner.setPrompt("Select an item");


        drawGraph();

        YearSpinnerSelected();
        MonthSpinnerSelected();





        return v;

    }

    public void drawGraph(){


        BarDataSet barDataSet0 = new BarDataSet(getGroup4(), "All");
        barDataSet0.setColor(Color.rgb(255,255,0));

        BarDataSet barDataSet1 = new BarDataSet(getGroup1(), "Negative");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(getGroup2(), "Positive");
        barDataSet2.setColor(Color.rgb(20, 10, 60));

        BarDataSet barDataSet3 = new BarDataSet(getGroup3(), "Invalid");
        barDataSet3.setColor(Color.rgb(255,0, 0));

//        // creating dataset for Bar Group1
//        BarDataSet barDataSet1 = new BarDataSet(getGroup1(), "VL");
//
//        barDataSet1.setColor(Color.rgb(0, 155, 0));
////        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
//
//// creating dataset for Bar Group 2
//        BarDataSet barDataSet2 = new BarDataSet(getGroup2(), "EID");
//        barDataSet2.setColor(Color.rgb(0, 10, 60));
//        BarDataSet barDataSet3 = new BarDataSet(getGroup2(), "EID");
//        barDataSet3.setColor(Color.rgb(255, 10, 60));


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet0);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        // initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(getLabels(), dataSets);
        bct.setData(data);
        bct.setDescription("Weekly");
        bct.setDescriptionPosition(90,15);
        bct.setDescriptionTextSize(30);
        data.setValueFormatter(new Myformater());
//        bct.getXAxis().setLabelRotationAngle(45);
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

    public ArrayList<String> getLabels(){

        ArrayList<String> labels = new ArrayList<String>();


        List x=getNumberOfWeeks(year,month);
        for(int i=0;i<x.size();i++){
            ArrayList y=(ArrayList)x.get(i);
            for(int b=0;b<y.size();b++){
                System.out.println(y.get(b));


            }
            labels.add(x.get(i).toString());
        }

        return labels;


    }

//    public ArrayList<BarEntry> getGroup1(){
//
//        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
//
//        for(int gw=0;gw<getNumberOfWeeks(year,month).size();gw++){
//            bargroup1.add(new BarEntry(8f, gw));
//        }
////        bargroup1.add(new BarEntry(8f, 0));
////        bargroup1.add(new BarEntry(2f, 1));
////        bargroup1.add(new BarEntry(5f, 2));
////        bargroup1.add(new BarEntry(20f, 3));
////        bargroup1.add(new BarEntry(15f, 4));
////        bargroup1.add(new BarEntry(19f, 5));
//
//        return bargroup1;
//    }

    public ArrayList<BarEntry> getGroup1(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        List x=getNumberOfWeeks(year,month);


        for(int gw=0;gw<getNumberOfWeeks(year,month).size();gw++){
            bargroup1.add(new BarEntry(getEidNegative(year,month,(ArrayList)getNumberOfWeeks(year,month).get(gw)), gw));
        }


        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup4(){

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        List x=getNumberOfWeeks(year,month);


        for(int gw=0;gw<getNumberOfWeeks(year,month).size();gw++){
            bargroup1.add(new BarEntry(getEidCount(year,month,(ArrayList)getNumberOfWeeks(year,month).get(gw)), gw));
        }


        return bargroup1;


    }

    public ArrayList<BarEntry> getGroup2(){

        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        List x=getNumberOfWeeks(year,month);


        for(int gw=0;gw<getNumberOfWeeks(year,month).size();gw++){
            bargroup2.add(new BarEntry(getEidPositive(year,month,(ArrayList)getNumberOfWeeks(year,month).get(gw)), gw));
        }


        return bargroup2;


    }


    public ArrayList<BarEntry> getGroup3(){

        ArrayList<BarEntry> bargroup3 = new ArrayList<>();
        List x=getNumberOfWeeks(year,month);


        for(int gw=0;gw<getNumberOfWeeks(year,month).size();gw++){
            bargroup3.add(new BarEntry(getEidInvalid(year,month,(ArrayList)getNumberOfWeeks(year,month).get(gw)), gw));
        }


        return bargroup3;


    }



 public int getEidCount(int year1,int month1,ArrayList myarr){


        int counter=0;

        try {
            List myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body");

            int x=0;

            if (myl2.size()==0){
                return 0;

            }


            do {

                Messages ms=(Messages) myl2.get(x);
                String mystrbdy = ms.getmBody();
                String stw = new String(mystrbdy);
                String mydate=ms.getmTimeStamp();


                String[] checkSplitdate=mydate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(mydate));
                    mydate = formatter.format(calendar.getTime());

                }

                String[] splitedDate=mydate.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                System.out.println("the splitted date is*************"+yeararr);
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];


                List x2=getNumberOfWeeks(year1,month1);

                String[] mys1=myarr.get(0).toString().split("-");
                String[] mys2=myarr.get(1).toString().split("-");

                int mys1val=Integer.parseInt(mys1[0]);
                int mys2val=Integer.parseInt(mys2[0]);

                int mnth1val=Integer.parseInt(mys1[1]);
                int mnth2val=Integer.parseInt(mys2[1]);

                int year1val=Integer.parseInt(mys1[2]);
                int year2val=Integer.parseInt(mys2[2]);

                int testdate=Integer.parseInt(myday);
                int testmnth=Integer.parseInt(mymnth);
                int testyear=Integer.parseInt(myyear);


                if(((testdate<=mys1val && testdate<=mys2val && testmnth>mnth1val && testmnth==mnth2val) || (testdate>=mys1val && testdate<=mys2val && testmnth==mnth1val && testmnth==mnth2val)) && testyear==year1val && stw.contains("FFEID")){

//                if((((testdate<=mys1val && mnth1val<mnth2val && mnth2val<testmnth )|| (testdate>=mys1val && mnth1val==testmnth && mnth2val>mnth1val ))) && testyear==year1val && stw.contains("FFEID") && stw.contains("Negative")){
                    counter += 1;

                }

                x++;

            } while (x<myl2.size());
        }

        catch(Exception e){

            System.out.println("error occured one.."+e);

        }



        System.out.println("counter EID is****** "+counter);

        return counter;
    }


    public void YearSpinnerSelected(){

        yearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                year=Integer.parseInt(parent.getItemAtPosition(position).toString());
                drawGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void MonthSpinnerSelected(){

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String[] mymnths={"January","February","March","April","May","June","July","August","September","October","November","December"};

                month=position;

                drawGraph();


//                Toast.makeText(getActivity().getApplicationContext(), ""+month, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }




    public List<List<String>> getNumberOfWeeks(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        List<List<String>> weekdates = new ArrayList<List<String>>();
        List<String> dates;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);
        while (c.get(Calendar.MONTH) == month) {
            dates = new ArrayList<String>();
            while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                c.add(Calendar.DAY_OF_MONTH, -1);
            }
            dates.add(format.format(c.getTime()));
            c.add(Calendar.DAY_OF_MONTH, 6);
            dates.add(format.format(c.getTime()));
            weekdates.add(dates);
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
//        System.out.println(weekdates);
        return weekdates;
    }



    public int getEidNegative(int year1,int month1,ArrayList myarr){


        int counter=0;

        try {
            List myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body");

            int x=0;

            if (myl2.size()==0){
                return 0;

            }


            do {

                Messages ms=(Messages) myl2.get(x);
                String mystrbdy = ms.getmBody();
                String stw = new String(mystrbdy);
                String mydate=ms.getmTimeStamp();

                String[] checkSplitdate=mydate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(mydate));
                    mydate = formatter.format(calendar.getTime());

                }
                System.out.println("the mydate value is *****"+mydate);

                String[] splitedDate=mydate.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];


                List x2=getNumberOfWeeks(year1,month1);
                System.out.println("the week array is**********"+myarr.get(0));

                String[] mys1=myarr.get(0).toString().split("-");
                String[] mys2=myarr.get(1).toString().split("-");

                int mys1val=Integer.parseInt(mys1[0]);
                int mys2val=Integer.parseInt(mys2[0]);

                int mnth1val=Integer.parseInt(mys1[1]);
                int mnth2val=Integer.parseInt(mys2[1]);

                int year1val=Integer.parseInt(mys1[2]);
                int year2val=Integer.parseInt(mys2[2]);

                int testdate=Integer.parseInt(myday);
                int testmnth=Integer.parseInt(mymnth);
                int testyear=Integer.parseInt(myyear);
                 System.out.println("my test date is "+testdate);
                System.out.println("my first date is "+mys1val);
                System.out.println("my second date is "+mys2val);

                if(((testdate<=mys1val && testdate<=mys2val && testmnth>mnth1val && testmnth==mnth2val) || (testdate>=mys1val && testdate<=mys2val && testmnth==mnth1val && testmnth==mnth2val)) && testyear==year1val && stw.contains("FFEID") && stw.contains("Negative")){

//                if((((testdate<=mys1val && mnth1val<mnth2val && mnth2val<testmnth )|| (testdate>=mys1val && mnth1val==testmnth && mnth2val>mnth1val ))) && testyear==year1val && stw.contains("FFEID") && stw.contains("Negative")){
                    counter += 1;

                }

                x++;

            } while (x<myl2.size());
        }

        catch(Exception e){

            System.out.println("error occured two... "+e);

        }



        System.out.println("counter EID Negative is****** "+counter);
        return counter;
    }


    public int getEidPositive(int year1,int month1,ArrayList myarr){


        int counter=0;

        try {
            List myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body");

            int x=0;

            if (myl2.size()==0){
                return 0;

            }


            do {

                Messages ms=(Messages) myl2.get(x);
                String mystrbdy = ms.getmBody();
                String stw = new String(mystrbdy);
                String mydate=ms.getmTimeStamp();

                String[] checkSplitdate=mydate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(mydate));
                    mydate = formatter.format(calendar.getTime());

                }
                String[] splitedDate=mydate.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];


                List x2=getNumberOfWeeks(year1,month1);

                String[] mys1=myarr.get(0).toString().split("-");
                String[] mys2=myarr.get(1).toString().split("-");

                int mys1val=Integer.parseInt(mys1[0]);
                int mys2val=Integer.parseInt(mys2[0]);

                int mnth1val=Integer.parseInt(mys1[1]);
                int mnth2val=Integer.parseInt(mys2[1]);

                int year1val=Integer.parseInt(mys1[2]);
                int year2val=Integer.parseInt(mys2[2]);

                int testdate=Integer.parseInt(myday);
                int testmnth=Integer.parseInt(mymnth);
                int testyear=Integer.parseInt(myyear);


                if(((testdate<=mys1val && testdate<=mys2val && testmnth>mnth1val && testmnth==mnth2val) || (testdate>=mys1val && testdate<=mys2val && testmnth==mnth1val && testmnth==mnth2val)) && testyear==year1val && stw.contains("FFEID") && stw.contains("Positive")){

//                if((((testdate<=mys1val && mnth1val<mnth2val && mnth2val<testmnth )|| (testdate>=mys1val && mnth1val==testmnth && mnth2val>mnth1val ))) && testyear==year1val && stw.contains("FFEID") && stw.contains("Negative")){
                    counter += 1;

                }

                x++;

            } while (x<myl2.size());
        }

        catch(Exception e){

            System.out.println("error occured three.... "+e);

        }




        System.out.println("counter EID Positive is****** "+counter);
        return counter;
    }





    public int getEidInvalid(int year1,int month1,ArrayList myarr){


        int counter=0;

        try {
            List myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body");

            int x=0;

            if (myl2.size()==0){
                return 0;

            }


            do {

                Messages ms=(Messages) myl2.get(x);
                String mystrbdy = ms.getmBody();
                String stw = new String(mystrbdy);
                String mydate=ms.getmTimeStamp();

                String[] checkSplitdate=mydate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(mydate));
                    mydate = formatter.format(calendar.getTime());

                }


                String[] splitedDate=mydate.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];


                List x2=getNumberOfWeeks(year1,month1);

                String[] mys1=myarr.get(0).toString().split("-");
                String[] mys2=myarr.get(1).toString().split("-");

                int mys1val=Integer.parseInt(mys1[0]);
                int mys2val=Integer.parseInt(mys2[0]);

                int mnth1val=Integer.parseInt(mys1[1]);
                int mnth2val=Integer.parseInt(mys2[1]);

                int year1val=Integer.parseInt(mys1[2]);
                int year2val=Integer.parseInt(mys2[2]);

                int testdate=Integer.parseInt(myday);
                int testmnth=Integer.parseInt(mymnth);
                int testyear=Integer.parseInt(myyear);


                if(((testdate<=mys1val && testdate<=mys2val && testmnth>mnth1val && testmnth==mnth2val) || (testdate>=mys1val && testdate<=mys2val && testmnth==mnth1val && testmnth==mnth2val)) && testyear==year1val && stw.contains("FFEID") && stw.contains("Collect new sample")){

//                if((((testdate<=mys1val && mnth1val<mnth2val && mnth2val<testmnth )|| (testdate>=mys1val && mnth1val==testmnth && mnth2val>mnth1val ))) && testyear==year1val && stw.contains("FFEID") && stw.contains("Negative")){
                    counter += 1;

                }

                x++;

            } while (x<myl2.size());
        }

        catch(Exception e){

            System.out.println("error occured four... "+e);

        }




        System.out.println("counter EID Invalid is****** "+counter);
        return counter;
    }




}
