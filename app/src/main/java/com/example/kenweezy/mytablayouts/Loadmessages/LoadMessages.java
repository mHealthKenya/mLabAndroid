package com.example.kenweezy.mytablayouts.Loadmessages;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.kenweezy.mytablayouts.GetViralCounts;
import com.example.kenweezy.mytablayouts.Messages;
import com.example.kenweezy.mytablayouts.Myshortcodes;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.tables.Htsresults;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoadMessages {

    Context ctx;
    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();

    public LoadMessages(Context ctx) {
        this.ctx = ctx;
    }

    public void getMessages() {
        try {

            int count=0;
            ContentResolver contentResolver = ctx.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),null, null, null,null);
            int indexBody = smsInboxCursor.getColumnIndex("body");

            int indexDate = smsInboxCursor.getColumnIndex("date");

            String mId="";
            String pID="";



            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                return;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String addr = smsInboxCursor.getString(2);
                String datee = smsInboxCursor.getString(indexDate);
                Long mydate=Long.parseLong(datee);

                if(addr.contentEquals(msc.mainShortcode)){


                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(mydate);
                    String mytimestamp=formatter.format(calendar.getTime());



                    GetViralCounts gvc=new GetViralCounts();

                    //get hts messages

                    String[] htsmessage=str.split("\\*");


                    //process hts messages here
//                    if(htsmessage.length>0){
//
//                        if(htsmessage[0].contentEquals("HTS")){
//
//                            String htsdecryptedmess = Base64Encoder.decryptedString(htsmessage[1]);
//                            String[] htsMessages=htsdecryptedmess.split("//*");
//                            String clientCodeS=htsMessages[0];
//                            String genderS=htsMessages[1];
//                            String ageS=htsMessages[2];
//                            String resultS=htsMessages[3];
//                            String submittedS=htsMessages[4];
//                            String releasedS=htsMessages[5];
//                            String sampleid=htsMessages[6];
//
//
//                            List<Htsresults> myl = Htsresults.find(Htsresults.class, "sampleid=?", sampleid);
//
//                            if(myl.isEmpty())
//                            {
//
//                                Htsresults hr=new Htsresults(clientCodeS,genderS,ageS,resultS,submittedS,releasedS,sampleid);
//                                hr.save();
//                            }
//                            else
//                            {
//
//                            }
//
//
//
//                        }
//
//                    }

                    //process normal eid vl results messages
//                    else{

                        String decryptedmess = new String( mcrypt.decrypt( str ) );
                        count++;
                        System.out.println("***message****::"+decryptedmess);
                        System.out.println("***message count***::"+count);
                        StringBuilder newMessage=new StringBuilder();
                        String mymess="";






//                new code here

                        String[] originalArray=decryptedmess.split(":");

                        String[] firstpart=originalArray[0].split("\\s+");

                        String[] pidpart=originalArray[1].split("\\s+");
                        pID=pidpart[0];

                        if(firstpart[0].contentEquals("EID")){

                            newMessage.append("FFEID Results");
                            mymess+="FFEID Results";

                        }

                        else if(firstpart[0].contentEquals("VL")){

                            newMessage.append("FFViral Load Results");
                            mymess+="FFViral Load Results";



                        }

                        if(firstpart[1].contentEquals("PID")){

                            newMessage.append(" KDOD NO");
                            mymess+=" KDOD NO";
                        }

                        String[] secondpart=originalArray[1].split("\\s+");

//                    for(int x=0;x<secondpart.length;x++){
                        newMessage.append(":"+secondpart[0]);
                        mymess+=":"+secondpart[0];

                        if(secondpart[1].contentEquals("A")){

                            newMessage.append(" Age:");

                        }

//                    }

                        String[] thirdpart=originalArray[2].split("\\s+");

//                    for(int x=0;x<thirdpart.length;x++){
                        newMessage.append(thirdpart[0]);

                        if(thirdpart[1].contentEquals("S")){

                            newMessage.append(" Sex:");

                        }

//                    }

                        String[] fourthpart=originalArray[3].split("\\s+");

//                    for(int x=0;x<fourthpart.length;x++){
                        newMessage.append(fourthpart[0]);

                        if(fourthpart[1].contentEquals("DC")){

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
                    else if(originalArray.length==9){

                        newMessage.append(originalArray[4]+":");
                        newMessage.append(originalArray[5]+":");
                        String[] sixthpart=originalArray[6].split("\\s+");
                        newMessage.append(sixthpart[0]+" Result::");
                        newMessage.append(originalArray[8]);
                        mId="n/a";


                    }

                    else if(originalArray.length==8){

                        String[] seventhpart=originalArray[4].split("\\s+");
                        newMessage.append(seventhpart[0]+" Result::");
                        newMessage.append(originalArray[6]);
                        mId=originalArray[7];
                    }
                    else if(originalArray.length==7){

                        String[] seventhpart=originalArray[4].split("\\s+");
                        newMessage.append(seventhpart[0]+" Result::");
                        newMessage.append(originalArray[6]);
                        mId="n/a";
                    }

                        System.out.println("****************************RECEIVED MESSAGE************************");
                        System.out.println(newMessage);

//                new code here



                        String vcounts=Integer.toString(gvc.getViralCount(newMessage.toString()));
//                String vcounts="12";


//                    Messages(String mAddress, String mBody, String mTimeStamp, String read, String dateRead, String chkd, String viralCount, String messageId, String patientid)


                    Messages ms=new Messages(addr,newMessage.toString(),mytimestamp,"unread","unread","false",vcounts,mId,pID);
//                        Messages ms=new Messages("false",addr,newMessage.toString(),mytimestamp,"unread","null",vcounts,mId,pID);
                        ms.save();
                        newMessage.setLength(0);

                    }





//                }



            } while (smsInboxCursor.moveToNext());

        }
        catch(Exception e){

        }


    }
}
