package com.example.kenweezy.mytablayouts.GetMessageCount;

import com.example.kenweezy.mytablayouts.Messages;
import com.example.kenweezy.mytablayouts.Mydata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 1/12/18.
 */

public class GetCounts {



    public int getVlUnsuppressed(){
        int counter=0;


        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for(int x=0;x<bdy.size();x++){

            try{


                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String mychk=bdy.get(x).getChkd();

                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);

                boolean mychkB;
                if(mychk.contentEquals("true")){
                    mychkB=true;
                }
                else{
                    mychkB=false;
                }

                if(!(messbdy.contains("Collect New Sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))) {


                    String[] mymessarray=messbdy.split(":");


                    String splitVal="";

                    if(messbdy.contains("Sex") && messbdy.contains("Age")){
                        splitVal=mymessarray[6];

                    }
                    else{

                        splitVal=mymessarray[3];
                    }




                    String[] splitvalarray=splitVal.split("\\s+");
                    if(splitvalarray[0].contains("<")){
                        System.out.println("i am suppressed "+splitvalarray[0]);



                    }

                    else{



                        int myval=Integer.parseInt(splitvalarray[0]);
                        if(myval>1000){
                            System.out.println("i am unsuppressed with a value "+myval);
                            counter += 1;


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








    public int getVlSuppressed(){
        int counter=0;

        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);


        for(int x=0;x<bdy.size();x++){


            try{


                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();

                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);


                String mychk=bdy.get(x).getChkd();
                boolean mychkB;
                if(mychk.contentEquals("true")){
                    mychkB=true;
                }
                else{
                    mychkB=false;
                }

                String mytimestamp=null;

                if(!(messbdy.contains("Collect New Sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))) {

                    String[] checkSplitdate = ndate.split("/");


                    String[] mymessarray = messbdy.split(":");
                    String splitVal = "";

                    if (messbdy.contains("Sex") && messbdy.contains("Age")) {
                        splitVal = mymessarray[6];

                    } else {

                        splitVal = mymessarray[3];
                    }

//            System.out.println("the split array suppresed::::: is "+mymessarray[3]);

                    String[] splitvalarray = splitVal.split("\\s+");
                    if (checkSplitdate.length > 1) {

                    } else {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    if (splitvalarray[0].contains("<")) {
                        System.out.println("i am suppressed " + splitvalarray[0]);
                        counter += 1;



                    } else {

                        int myval = Integer.parseInt(splitvalarray[0]);
                        if (myval > 1000) {
                            System.out.println("i am unsuppressed with a value " + myval);

                        } else {
                            System.out.println("i am suppressed with a value " + myval);
                            counter += 1;

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












    public int getInvalidCount(){
        int counter=0;

        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);



        for(int x=0;x<bdy.size();x++){


            String messbdy=bdy.get(x).getmBody();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();

            String mvcnt=bdy.get(x).getViralCount();
            int vcount=Integer.parseInt(mvcnt);

            String mychk=bdy.get(x).getChkd();
            boolean mychkB;
            if(mychk.contentEquals("true")){
                mychkB=true;
            }
            else{
                mychkB=false;
            }

            if((messbdy.contains("Collect New Sample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){



                counter += 1;

                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }




            }




        }
        return counter;
    }
}
