package com.example.kenweezy.mytablayouts;

/**
 * Created by kennedy on 8/16/17.
 */

public class GetViralCounts {

    public int getViralCount(String mesbdy){

        int mescnt=0;

        try{

            if(mesbdy.contains("FFViral") && (!(mesbdy.contains("Collect New Sample")||mesbdy.contains("Collect new sexample")||mesbdy.contains("Invalid")||mesbdy.contains("Failed")))) {


                String[] mymessarray=mesbdy.split(":");


                String splitVal="";

                if(mesbdy.contains("Sex") && mesbdy.contains("Age")){
                    splitVal=mymessarray[6];

                }
                else{

                    splitVal=mymessarray[3];
                }




                String[] splitvalarray=splitVal.split("\\s+");
                if(splitvalarray[0].contains("<")){

                    mescnt=0;


                }

                else{



                    int myval=Integer.parseInt(splitvalarray[0]);
                    mescnt=myval;


                }


            }
            else{

                mescnt=0;
            }


        }
        catch(Exception e){


        }

        return mescnt;
    }

}
