package com.example.kenweezy.mytablayouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by KENWEEZY on 2017-01-26.
 */

public class options2 extends AppCompatActivity {
    Button btnanim;
    boolean play;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options2);
        btnanim=(Button) findViewById(R.id.myanim);

//        animate();




    }

    public void animate(){

        Button resultsb = (Button)findViewById(R.id.myanim);
        Button dashboardb = (Button)findViewById(R.id.myanim2);
        Button aboutb = (Button)findViewById(R.id.myanim3);
        Button contactb = (Button)findViewById(R.id.myanim4);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        myAnim.setRepeatCount(Animation.INFINITE);
        resultsb.startAnimation(myAnim);
        dashboardb.startAnimation(myAnim);
        aboutb.startAnimation(myAnim);
        contactb.startAnimation(myAnim);

//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//        myAnim.setInterpolator(interpolator);


    }

    public void Dashboard(View v){


        Button dashboardb = (Button)findViewById(R.id.myanim2);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setRepeatCount(Animation.INFINITE);
        dashboardb.startAnimation(myAnim);


        Toast.makeText(this, "dashboard", Toast.LENGTH_SHORT).show();

    }

    public void Results(View v){

        Button resultsb = (Button)findViewById(R.id.myanim);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setRepeatCount(Animation.INFINITE);
        resultsb.startAnimation(myAnim);


        Thread th=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(750);
                }
                catch(Exception e){

                }
                finally{
                    Intent myint=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(myint);


                }
            }
        };
        th.start();


    }

    public void About(View v){


        Button aboutb = (Button)findViewById(R.id.myanim3);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setRepeatCount(Animation.INFINITE);
        aboutb.startAnimation(myAnim);


        Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();


    }

    public void Contact(View v){

        Button contactb = (Button)findViewById(R.id.myanim4);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setRepeatCount(Animation.INFINITE);
        contactb.startAnimation(myAnim);


        Toast.makeText(this, "contact", Toast.LENGTH_SHORT).show();


    }
}
