package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by root on 10/16/17.
 */

public class Forgotpassword extends AppCompatActivity {

    EditText mysecqn,mysecans,mynewpass,mynewcpass;
    CheckBox cb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        initialise();
        showPasswordListener();
        populateSecQn();
    }

    public void initialise(){

        try{

            mysecqn=(EditText) findViewById(R.id.secqn);
            mysecans=(EditText) findViewById(R.id.secans);
            mynewpass=(EditText) findViewById(R.id.newpass);
            mynewcpass=(EditText) findViewById(R.id.newpassconfirm);
            cb=(CheckBox) findViewById(R.id.cbShowPwdf);
        }
        catch(Exception e){


        }
    }

    public void showPasswordListener(){

        try{

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(!isChecked){

                        mynewpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mynewcpass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                    else{

                        mynewpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mynewcpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    }

                }
            });
        }
        catch(Exception e){


        }
    }

    public void populateSecQn(){

        try{

            List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1",null);
            for(int x=0;x<myl.size();x++){
                String mysecqnS=myl.get(x).getSecurityqn();
                mysecqn.setText(mysecqnS);


            }
        }
        catch(Exception e){


        }
    }

    public void Recover(View v){

        try{

            String mysecqnS=mysecqn.getText().toString();
            String mysecansS=mysecans.getText().toString();
            String mynewpassS=mynewpass.getText().toString();
            String mynewcpassS=mynewcpass.getText().toString();

            if(mysecansS.trim().isEmpty()){
                Toast.makeText(this, "please provide security question answer", Toast.LENGTH_SHORT).show();
            }
            else if(mynewpassS.trim().isEmpty()){

                Toast.makeText(this, "please provide the new password", Toast.LENGTH_SHORT).show();
            }
            else if(mynewcpassS.trim().isEmpty()){
                Toast.makeText(this, "please provide the new confirm password", Toast.LENGTH_SHORT).show();
            }
            else if(!mynewcpassS.contentEquals(mynewpassS)){

                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
            else{

                List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1",null);
                for(int x=0;x<myl.size();x++){
                    String storedAns=myl.get(x).getSecurityans();
                    if(storedAns.contentEquals(mysecansS)){

//                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();


                        UsersTable ut = UsersTable.findById(UsersTable.class, 1);
                        ut.password = mynewcpassS;
                        ut.save();

                        ForgotPassConf("Choose Your Action ?","PASSWORD UPDATED SUCCESSFULLY");


                    }
                    else{
//                        Toast.makeText(this, "E", Toast.LENGTH_SHORT).show();
                        LogindisplayDialog("WRONG SECURITY QUESTION ANSWER.");
                    }


                }
            }




        }
        catch(Exception e){
            Toast.makeText(this, "error "+e, Toast.LENGTH_SHORT).show();
            System.out.println("error "+e);


        }
    }


    public void ForgotPassConf(final String message,final String title){

        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setMessage(message+"\n");
        b.setTitle(title);
        b.setCancelable(false);

        b.setNegativeButton("CONTINUE TO LOGIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                dialog.cancel();
            }
        });

        b.setPositiveButton("EXIT APP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                finish();

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button bP = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(Color.GREEN);
        bn.setTextColor(Color.BLUE);
        bP.setTextColor(Color.RED);

    }



    public void LogindisplayDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("ERROR UPDATING PASSWORD");
            adb.setIcon(R.mipmap.error);
            adb.setMessage(message.toUpperCase());
            adb.setCancelable(false);

            adb.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });





            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }


}
