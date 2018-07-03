package com.example.kenweezy.mytablayouts.messagedialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kenweezy.mytablayouts.R;

public class MessageDialog {

    Context ctx;


    public MessageDialog(Context ctx) {
        this.ctx = ctx;
    }





    public void displayMessage(final String MessageBdy,final String mdate){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            final View promptsView = li.inflate(R.layout.message_format, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });



            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final TextView pidF = (TextView) promptsView
                    .findViewById(R.id.patientid);

            final TextView ageF = (TextView) promptsView
                    .findViewById(R.id.age);

            final TextView sexF = (TextView) promptsView
                    .findViewById(R.id.sex);

            final TextView dcF = (TextView) promptsView
                    .findViewById(R.id.datecollected);

            final TextView resF = (TextView) promptsView
                    .findViewById(R.id.results);

            final TextView drF = (TextView) promptsView
                    .findViewById(R.id.datereceived);


            pidF.setText(getPatientId(MessageBdy));
            ageF.setText(getAge(MessageBdy));
            sexF.setText(getSex(MessageBdy));
            dcF.setText(getDateCollected(MessageBdy));

            resF.setText(getResult(MessageBdy));
            drF.setText(getDateReceived(mdate));
            alertDialog.show();

            //set button color of the text
            Button b_pos;
            b_pos=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if(b_pos!=null){
//                b_pos.setTextColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
                b_pos.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.dialog_close_button));
            }
            //set button color of the text



        }
        catch(Exception e){


        }
    }

    public String getPatientId(String message){

        String[] messageArray=message.split(":");
        System.out.println("**********************************message length********************");
        System.out.println(messageArray.length);

        String[] patientIdArr=messageArray[1].split("\\s+");

        return patientIdArr[0];
    }

    public String getAge(String message){

        String[] messageArray=message.split(":");

        String[] ageArr=messageArray[2].split("\\s+");

        return ageArr[0];
    }

    public String getSex(String message){

        String[] messageArray=message.split(":");

        String[] sexArr=messageArray[3].split("\\s+");

        return sexArr[0];
    }


    public String getDateCollected(String message){

        String[] messageArray=message.split(":");

        String[] dcollectedArr=messageArray[4].split("\\s+");

        return dcollectedArr[0];
    }

    public String getResult(String message){

        String[] messageArray=message.split(":");
        String myresult="";
        if(messageArray.length==9){

            myresult=messageArray[8];

        }
        else if(messageArray.length==7){

            myresult=messageArray[6];


        }

        return myresult;


    }

    public String getDateReceived(String dreceived){

        return dreceived;
    }


}
