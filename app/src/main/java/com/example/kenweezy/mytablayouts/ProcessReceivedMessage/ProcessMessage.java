package com.example.kenweezy.mytablayouts.ProcessReceivedMessage;


import android.widget.Toast;

import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.GetViralCounts;
import com.example.kenweezy.mytablayouts.Messages;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.tables.Htsresults;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProcessMessage {


    Base64Encoder encoder;

    public ProcessMessage() {
        encoder=new Base64Encoder();
    }

    public void processReceivedMessage(String str){

        try{


            //***********message starts here

            GetViralCounts gvc=new GetViralCounts();
            StringBuilder newMessage=new StringBuilder();
            String mId="";
            String pID="";

            String pattern = "dd/MM/yyyy hh:mm:ss.SSS";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String mytimestamp = simpleDateFormat.format(new Date());

            Htsresults htsres=new Htsresults();


            String decryptedmess = encoder.decryptedString(str);

            System.out.println("*********the original message***************");
            System.out.println(decryptedmess);


            String[] originalArray=decryptedmess.split(":");

            String[] firstpart=originalArray[0].split("\\s+");


            if(firstpart[0].contentEquals("HTS")){

//                HTS PID:1389118447 A:36 S:F T:HTS PCR R:Negative SB:2018-08-28 REL:2018-09-11 SID:MB1873108


                //************start process hts logic here
                String[] pidarr=originalArray[1].split("\\s+");
                String pid=pidarr[0];

                String[] agearr=originalArray[2].split("\\s+");
                String age=agearr[0];

                String[] sexarr=originalArray[3].split("\\s+");
                String sex=sexarr[0];

                String[] resultarr=originalArray[5].split("\\s+");
                String result=resultarr[0];

                String[] datesubmittedarr=originalArray[6].split("\\s+");
                String datesubmitted=datesubmittedarr[0];

                String[] datereleasearr=originalArray[7].split("\\s+");
                String daterelease=datereleasearr[0];


                String sampleid=originalArray[8];

                List<Htsresults> mylh=Htsresults.findWithQuery(Htsresults.class,"select * from Htsresults where sampleid=?",sampleid);
                if(mylh.size()>0){


                }
                else{

                    htsres.setAge(age);
                    htsres.setResult(result);
                    htsres.setGender(sex);
                    htsres.setReleased(daterelease);
                    htsres.setSubmitted(datesubmitted);
                    htsres.setClientcode(pid);
                    htsres.setSampleid(sampleid);

                    htsres.save();

                }




                //*************end process hts logic here


            }
            else if(firstpart[0].contentEquals("EID") || firstpart[0].contentEquals("VL")){


                //************start process eid vl logic here

                String[] pidpart=originalArray[1].split("\\s+");
                pID=pidpart[0];

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
                    firstpart[1].replace("PID","KDOD NO");
                    decryptedmess=decryptedmess.replace("PID","KDOD NO");
                    newMessage.append(" KDOD NO");
                }

                String[] secondpart=originalArray[1].split("\\s+");
//                EID PID:13805 - 2020 - 2708 A:1.5 S:Female DC:2020-01-06 R: :Negative
                String lastItem=secondpart[secondpart.length-1];
                String thePatientId=originalArray[1].replace(lastItem,"");

//                    for(int x=0;x<secondpart.length;x++){
                newMessage.append(":"+thePatientId);

                if(lastItem.contentEquals("A")){
                    lastItem.replace("A","Age");
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



                String vcounts=Integer.toString(gvc.getViralCount(newMessage.toString()));


                Messages ms=new Messages(Config.mainShortcode,newMessage.toString(),mytimestamp,"unread","unread","false",vcounts,mId,pID);
//
//                Messages ms = new Messages("false", Config.mainShortcode,newMessage.toString(),mytimestamp,"unread","null",vcounts,mId,pID);
                ms.save();


                //*************end process eid vl logic here
            }





        }
        catch(Exception e){

            System.out.println("******exception****"+e);
        }


    }
}

