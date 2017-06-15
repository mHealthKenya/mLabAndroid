package com.example.kenweezy.mytablayouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-03-30.
 */

public class SendReportReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "my report alarm called", Toast.LENGTH_SHORT).show();
////        sendReadReport();
//        intent = new Intent();
//        intent.setClass(context, Mylogin.class); //Test is a dummy class name where to redirect
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.putExtra("msg", str);
//        context.startActivity(intent);
//        finish();
//        sendReadReport();

      try{

          intent = new Intent();
          intent.setClass(context, SmsActivity.class); //Test is a dummy class name where to redirect
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra("msg", "my alarm");
          context.startActivity(intent);
      }


        catch(Exception e){
            Toast.makeText(context, "alarm receiver error "+e, Toast.LENGTH_SHORT).show();

        }

    }










    public void sendReadReport(){

        try{

            int read=0;
            int todayReceived=0;
            int todayRead=0;
            String unread="";
            String tstamp="";
            int allmesages=0;
            String dt="";
            String mth="";
            String yr="";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
            String x=timestamp.toString();
            String[] spliting=x.split("\\s+");
            System.out.println("testing new "+spliting[0]);
            String mydate=spliting[0];
            String[] splitedMydate=mydate.split("-");
            dt=splitedMydate[2];
            mth=splitedMydate[1];
            yr=splitedMydate[0];


            List<Messages> myl2=Messages.findWithQuery(Messages.class,"Select * from Messages group by m_body",null);
            for(int y=0;y<myl2.size();y++){
                allmesages=myl2.size();
                tstamp=myl2.get(y).getmTimeStamp();

                String[] splitedDate=tstamp.split("/");
                String yeararr=splitedDate[2];//returns the string like 2017 11:10:30.366
                String[] myyeararr=yeararr.split("\\s+");//split the white space to get only the year e.g 2017


                String mymnth=splitedDate[1];
                String myyear=myyeararr[0];
                String myday=splitedDate[0];

                if(mymnth.contentEquals(mth)&& myyear.contentEquals(yr)&& myday.contentEquals(dt)){

                    todayReceived+=1;
                }



            }

            List<Messages> myl3=Messages.findWithQuery(Messages.class,"Select * from Messages where read=? group by m_body", "read");
            for(int y=0;y<myl3.size();y++){
                read=myl3.size();

                tstamp=myl3.get(y).getDateRead();


                String[] spliting2=tstamp.split("\\s+");
                System.out.println("testing new "+spliting2[0]);
                String mydate1=spliting2[0];

                String[] splitedMydate1=mydate1.split("-");
                dt=splitedMydate1[2];
                mth=splitedMydate1[1];
                yr=splitedMydate1[0];



                Timestamp timestampnow = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                String xnow=timestampnow.toString();
                String[] splitingnow=xnow.split("\\s+");
                System.out.println("testing new "+splitingnow[0]);
                String mydatenow=spliting[0];
                String[] splitedMydatenow=mydatenow.split("-");
                String dt1=splitedMydatenow[2];
                String mth1=splitedMydatenow[1];
                String yr1=splitedMydatenow[0];







                if(mth1.contentEquals(mth)&& yr1.contentEquals(yr)&&dt1.contentEquals(dt)){

                    todayRead+=1;
                }

            }
            String messReport="mlab"+"*"+mydate+"*"+todayReceived+"*"+todayRead+"*"+allmesages+"*"+read;
//            RegistrationConf("todayread is: "+todayRead+" today received is "+todayReceived+" total read is "+read+" all messages "+allmesages+" date is "+x,"message test");
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage("40147", null,messReport, null, null);


        }
        catch(Exception e){

        }
    }







}
