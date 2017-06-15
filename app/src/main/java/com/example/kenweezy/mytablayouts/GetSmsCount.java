package com.example.kenweezy.mytablayouts;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by KENWEEZY on 2017-01-25.
 */

public class GetSmsCount {
    Activity myAct;

public GetSmsCount(Activity myact){
    myAct=myact;

}


    public int getFfeidCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFEID")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

return counter;
    }


    public int getVlCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFViral")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }

    public int getAllCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                    counter += 1;




            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }


    public int getFfeidNegativeCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFEID") && stw.contains("Negative")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }

    public int getFfeidPositiveCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFEID")&&(stw.contains("Positive"))) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }


    public int getVlAllCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFViral")) {

                    counter += 1;

                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }




    public int getVlSuppressedCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFViral") && stw.contains("LDL")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }

    public int getVlUnsuppressedCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFViral")&& (!stw.contains("LDL"))&&!stw.contains("Invalid")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }

    public int getVlInvalidCount() {
        int counter=0;
        try {
            ContentResolver contentResolver = myAct.getContentResolver();
            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
            int indexBody = smsInboxCursor.getColumnIndex("body");
            int indexAddress = smsInboxCursor.getColumnIndex("address");

            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
                counter=0;

            do {
                String str = smsInboxCursor.getString(indexBody);
                String mystrbdy = smsInboxCursor.getString(indexBody);
                String stw = new String(mystrbdy);
                String msgaddr = smsInboxCursor.getString(indexAddress);

                if (stw.contains("FFViral")&& stw.contains("Invalid")) {
                    counter += 1;



                }


            } while (smsInboxCursor.moveToNext());
        }
        catch(Exception e){

        }

        return counter;
    }
}
