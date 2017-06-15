package com.example.kenweezy.mytablayouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by KENWEEZY on 2017-01-26.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        DisplaySplash();
//
//        Button button = (Button)findViewById(R.id.myanim2);
//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
//        myAnim.setRepeatCount(Animation.INFINITE);
//        button.startAnimation(myAnim);
//
////        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
////        myAnim.setInterpolator(interpolator);
//
//        button.startAnimation(myAnim);


        Button button = (Button)findViewById(R.id.myanim2);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setRepeatCount(Animation.INFINITE);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);


    }

    public void DisplaySplash(){

        Thread th=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                }
                catch(Exception e){

                }
                finally{
                    Intent myint=new Intent(getApplicationContext(),Mylogin.class);
                    startActivity(myint);

                }
            }
        };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
