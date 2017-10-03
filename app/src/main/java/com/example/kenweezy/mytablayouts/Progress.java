package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-01-13.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class Progress{
    public ProgressDialog pd;


    public void progressing(Context ctx, String message, String title){

        pd=new ProgressDialog(ctx);

        pd.setMessage(message);
        pd.setTitle(title);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setProgress(0);
        pd.show();



    }

    public void progressing2(Context ctx, String message, String title){

        pd=new ProgressDialog(ctx);

        pd.setMessage(message);
        pd.setTitle(title);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setProgress(0);
        pd.show();



    }

    public void DissmissProgress(){

        pd.dismiss();
    }


    public void HorizontalProgress(Context ctx){


        pd=new ProgressDialog(ctx);

        pd.setMessage("approving......");
        pd.setTitle("Approving,please hold on");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setProgress(0);
        pd.show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (pd.getProgress() <= pd
                            .getMax()) {
                        Thread.sleep(50);
                        handle.sendMessage(handle.obtainMessage());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pd.incrementProgressBy(1);
        }
    };


}
