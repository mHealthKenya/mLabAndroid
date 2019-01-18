package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-01-13.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.tables.Htsresults;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();





    public static final String SMS_BUNDLE = "pdus";

   

    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();


       Bundle intentExtras = intent.getExtras();
       StringBuilder newMessage=new StringBuilder();
       String mId="";

      


        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String getAdd="";
            Long mydate=null;
            String mytimestamp=null;



            for (int i = 0; i < sms.length; ++i) {
                try {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    getAdd = smsMessage.getOriginatingAddress();
                    mydate = smsMessage.getTimestampMillis();


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    mytimestamp = formatter.format(calendar.getTime());


                    smsMessageStr += smsBody;
//                    Toast.makeText(context, ""+smsMessageStr, Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){

                    Toast.makeText(context, "error occured"+e, Toast.LENGTH_SHORT).show();
                    System.out.println("smsbroadcastreceiver error "+e);
                    Log.v("SMSRECEIVER BROADCAST..",e.getMessage());
                }
//

            }
            try {
                if(getAdd.contentEquals(msc.mainShortcode)){

                    context.sendBroadcast(new Intent("MESSAGE RECEIVED"));

                    GetViralCounts gvc=new GetViralCounts();

                    String decryptedmess = new String( mcrypt.decrypt( smsMessageStr ) );
//                    Toast.makeText(context, ""+decryptedmess, Toast.LENGTH_SHORT).show();


                    String[] htsmessage=smsMessageStr.split("\\*");


                    //process hts messages here
                    if(htsmessage.length>0){

                        if(htsmessage[0].contentEquals("HTS")){

                            String htsdecryptedmess = Base64Encoder.decryptedString(htsmessage[1]);
                            String[] htsMessages=htsdecryptedmess.split("//*");
                            String clientCodeS=htsMessages[0];
                            String genderS=htsMessages[1];
                            String ageS=htsMessages[2];
                            String resultS=htsMessages[3];
                            String submittedS=htsMessages[4];
                            String releasedS=htsMessages[5];
                            String sampleid=htsMessages[6];


                            List<Htsresults> myl = Htsresults.find(Htsresults.class, "sampleid=?", sampleid);

                            if(myl.isEmpty())
                            {

                                Htsresults hr=new Htsresults(clientCodeS,genderS,ageS,resultS,submittedS,releasedS,sampleid);
                                hr.save();
                            }
                            else
                            {

                            }



                        }

                    }


                    //process normal eid vl results here
                    else{


                        //new code here

                        String[] originalArray=decryptedmess.split(":");

                        String[] firstpart=originalArray[0].split("\\s+");

                        if(firstpart[0].contentEquals("EID")){
                            firstpart[0].replace("EID","FFEID Results");
                            decryptedmess=decryptedmess.replace("EID","FFEID Results");
                            newMessage.append("FFEID Results");

                        }
                        else if(firstpart[0].contentEquals("VL")){
                            firstpart[0].replace("VL","FFViral Load Results");
                            decryptedmess=decryptedmess.replace("VL","FFViral Load Results");
                            newMessage.append("FFViral Load Results");


                        }

                        if(firstpart[1].contentEquals("PID")){
                            firstpart[1].replace("PID","Patient ID");
                            decryptedmess=decryptedmess.replace("PID","Patient ID");
                            newMessage.append(" Patient ID");
                        }

                        String[] secondpart=originalArray[1].split("\\s+");

//                    for(int x=0;x<secondpart.length;x++){
                        newMessage.append(":"+secondpart[0]);

                        if(secondpart[1].contentEquals("A")){
                            secondpart[1].replace("A","Age");
                            decryptedmess=decryptedmess.replace("A","Age");

                            newMessage.append(" Age:");

                        }

//                    }

                        String[] thirdpart=originalArray[2].split("\\s+");

//                    for(int x=0;x<thirdpart.length;x++){
                        newMessage.append(thirdpart[0]);

                        if(thirdpart[1].contentEquals("S")){
                            thirdpart[1].replace("S","Sex");
                            decryptedmess=decryptedmess.replaceFirst("S","Sex");
                            newMessage.append(" Sex:");

                        }

//                    }

                        String[] fourthpart=originalArray[3].split("\\s+");

//                    for(int x=0;x<fourthpart.length;x++){
                        newMessage.append(fourthpart[0]);

                        if(fourthpart[1].contentEquals("DC")){
                            fourthpart[1].replace("DC","Date Collected");
                            decryptedmess=decryptedmess.replace("DC","Date Collected");
                            newMessage.append(" Date Collected:");

                        }

//                    }
                        if(originalArray.length==10){

                            newMessage.append(originalArray[4]+":");
                            newMessage.append(originalArray[5]+":");
                            String[] sixthpart=originalArray[6].split("\\s+");
                            newMessage.append(sixthpart[0]+" Result::");
                            newMessage.append(originalArray[8]);
                            mId=originalArray[9];


                        }
                        else{

                            String[] seventhpart=originalArray[4].split("\\s+");
                            newMessage.append(seventhpart[0]+" Result::");
                            newMessage.append(originalArray[6]);
                            mId=originalArray[7];

                        }

                        System.out.println("****************************RECEIVED MESSAGE************************");
                        System.out.println(newMessage);
//                    String[] fifthpart=originalArray[4].split("\\s+");
//
//                    for(int x=0;x<fifthpart.length;x++){
//
//                        if(fifthpart[x].contentEquals("R")){
//                            fifthpart[x].replace("R","Result");
//                            decryptedmess=decryptedmess.replace("R:","Result:");
//
//                        }
//
//                    }
                        //new code here


                        String vcounts=Integer.toString(gvc.getViralCount(newMessage.toString()));
//
                        Messages ms = new Messages("false",getAdd,newMessage.toString(),mytimestamp,"unread","null",vcounts,mId);
                        ms.save();


//                    context.getContentResolver().delete(Uri.parse("content://sms"), "address=?", new String[] {msc.mainShortcode});



                    }



                }


            }
            catch (Exception e){
                Toast.makeText(context, ""+e.toString(), Toast.LENGTH_LONG).show();
                System.out.println("smsbroadcastreceiver error "+e);
                Log.v("SMSRECEIVER BROADCAST..",e.getMessage());

            }
        }


    }




}

