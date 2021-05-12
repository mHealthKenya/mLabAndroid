package com.example.kenweezy.mytablayouts.sendmessages;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SendMessage {

    Context ctx;

    public SendMessage(Context ctx){

        this.ctx=ctx;
    }

    public void sendMessageApi(String message,String destination){

        try{



            Intent intent = new Intent(Intent.ACTION_SENDTO);

            intent.putExtra("sms_body", message);
            intent.setData(Uri.parse("sms:"+destination));

            if (intent.resolveActivity(ctx.getPackageManager()) != null) {
                ctx.startActivity(intent);
            }
        }
        catch(Exception e){



        }
    }
}

