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

import com.example.kenweezy.mytablayouts.encryption.MCrypt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();





    public static final String SMS_BUNDLE = "pdus";

   

    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();


       Bundle intentExtras = intent.getExtras();

      


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


                    //new code here

                    String[] originalArray=decryptedmess.split(":");

                    String[] firstpart=originalArray[0].split("\\s+");

                    if(firstpart[0].contentEquals("EID")){
                        firstpart[0].replace("EID","FFEID Results");
                        decryptedmess=decryptedmess.replace("EID","FFEID Results");

                    }
                    else if(firstpart[0].contentEquals("VL")){
                        firstpart[0].replace("VL","FFViral Load Results");
                        decryptedmess=decryptedmess.replace("VL","FFViral Load Results");


                    }

                    if(firstpart[1].contentEquals("PID")){
                        firstpart[1].replace("PID","Patient ID");
                        decryptedmess=decryptedmess.replace("PID","Patient ID");
                    }

                    String[] secondpart=originalArray[1].split("\\s+");

                    for(int x=0;x<secondpart.length;x++){

                        if(secondpart[x].contentEquals("A")){
                            secondpart[x].replace("A","Age");
                            decryptedmess=decryptedmess.replace("A","Age");

                        }

                    }

                    String[] thirdpart=originalArray[2].split("\\s+");

                    for(int x=0;x<thirdpart.length;x++){

                        if(thirdpart[x].contentEquals("S")){
                            thirdpart[x].replace("S","Sex");
                            decryptedmess=decryptedmess.replace("S","Sex");

                        }

                    }

                    String[] fourthpart=originalArray[3].split("\\s+");

                    for(int x=0;x<fourthpart.length;x++){

                        if(fourthpart[x].contentEquals("DC")){
                            fourthpart[x].replace("DC","Date Collected");
                            decryptedmess=decryptedmess.replace("DC","Date Collected");

                        }

                    }

                    String[] fifthpart=originalArray[4].split("\\s+");

                    for(int x=0;x<fifthpart.length;x++){

                        if(fifthpart[x].contentEquals("R")){
                            fifthpart[x].replace("R","Result");
                            decryptedmess=decryptedmess.replace("R:","Result:");

                        }

                    }
                    //new code here


                    String vcounts=Integer.toString(gvc.getViralCount(decryptedmess));

                    Messages ms = new Messages("false",getAdd,decryptedmess,mytimestamp,"unread","null",vcounts);
                    ms.save();


//                    context.getContentResolver().delete(Uri.parse("content://sms"), "address=?", new String[] {msc.mainShortcode});


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

