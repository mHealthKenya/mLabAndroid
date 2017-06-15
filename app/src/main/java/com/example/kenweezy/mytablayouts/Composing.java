package com.example.kenweezy.mytablayouts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Composing extends AppCompatActivity  implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    EditText addr,msg;
    AutoCompleteTextView cont;
    SmsManager smsManager = SmsManager.getDefault();
    private static MainActivity inst;

    public static final String LOGGED_IN = "logged_in";

    public static final String SETTING_INFOS = "SETTING_Infos";

    private static final int READ_SMS_PERMISSIONS_REQUEST = 2;

    // Initialize variables

    AutoCompleteTextView textView = null;
    private ArrayAdapter<String> adapter;

    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();

    EditText toNumber = null;
    String toNumberValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Messages");

//        addr=(EditText) findViewById(R.id.myaddr1);
        msg=(EditText) findViewById(R.id.mybdy1);



        // Initialize AutoCompleteTextView values

        textView = (AutoCompleteTextView) findViewById(R.id.toNumber);

        //Create adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        textView.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);

        // Read contact data and add data to ArrayAdapter
        // ArrayAdapter used by AutoCompleteTextView

        readContactData();

    }



    private void readContactData() {

        try {

            /*********** Reading Contacts Name And Number **********/

            String phoneNumber = "";
            ContentResolver cr = getBaseContext()
                    .getContentResolver();

            //Query to get contact name

            Cursor cur = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

            // If data data found in contacts
            if (cur.getCount() > 0) {

                Log.i("AutocompleteContacts", "Reading   contacts........");

                int k = 0;
                String name = "";

                while (cur.moveToNext()) {

                    String id = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //Check contact have phone number
                    if (Integer
                            .parseInt(cur
                                    .getString(cur
                                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        //Create query to get phone number by contact id
                        Cursor pCur = cr
                                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + " = ?",
                                        new String[]{id},
                                        null);
                        int j = 0;

                        while (pCur
                                .moveToNext()) {
                            // Sometimes get multiple data
                            if (j == 0) {
                                // Get Phone number
                                phoneNumber = "" + pCur.getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                // Add contacts names to adapter
                                adapter.add(name);

                                // Add ArrayList names to adapter
                                phoneValueArr.add(phoneNumber.toString());
                                nameValueArr.add(name.toString());

                                j++;
                                k++;
                            }
                        }  // End while loop
                        pCur.close();
                    } // End if

                }  // End while loop

            } // End Cursor value check
            cur.close();


        } catch (Exception e) {
            Log.i("AutocompleteContacts", "Exception : " + e);
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub
        //Log.d("AutocompleteContacts", "onItemSelected() position " + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        // Get Array index value for selected name
        int i = nameValueArr.indexOf("" + arg0.getItemAtPosition(arg2));

        // If name exist in name ArrayList
        if (i >= 0) {

            // Get Phone Number
            toNumberValue = phoneValueArr.get(i);

            InputMethodManager imm = (InputMethodManager) getSystemService(
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            textView.setText(toNumberValue);

            // Show Alert
            Toast.makeText(getBaseContext(), "Position:" + arg2 + " Name:" + arg0.getItemAtPosition(arg2) + " Number:" + toNumberValue,
                    Toast.LENGTH_LONG).show();

            Log.d("AutocompleteContacts", "Position:" + arg2 + " Name:" + arg0.getItemAtPosition(arg2) + " Number:" + toNumberValue);

        }

    }


//    public void getAutoConts(){
//
//        try{
//
//            String[] from = new String[] {
//                    ContactsContract.Contacts.DISPLAY_NAME,
//                    ContactsContract.CommonDataKinds.Phone.NUMBER
//            };
//
//            Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//            startManagingCursor(emailCursor);
//            SimpleCursorAdapter adapter =new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, emailCursor, from, new int[] {android.R.id.text1});
//            adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
//                @Override
//                public CharSequence convertToString(Cursor cursor) {
//                    final int columnIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    final String str = cursor.getString(columnIndex);
//                    return str;
//                }
//            });
//            cont.setAdapter(adapter);
//            cont.setThreshold(0);
//
//        }
//        catch(Exception e){
//
//
//
//
//        }
//
//    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendSms(View v){

        try{

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                getPermissionToReadSMS();
            } else {
                if(textView.getText().toString().trim().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please Provide the message Address", Toast.LENGTH_LONG).show();
                }
                else if(msg.getText().toString().trim().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please Provide the message Body", Toast.LENGTH_LONG).show();
                }

                else {
                    smsManager.sendTextMessage(textView.getText().toString().trim(), null, msg.getText().toString().trim(), null, null);
                    Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
//                    addr.setText("");
                    textView.setText("");
                    msg.setText("");
                }
            }


        }
        catch(Exception e){


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }



    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        long now = new Date().getTime();
        List<UserTimeOut> ml = UserTimeOut.findWithQuery(UserTimeOut.class, "Select * from User_time_out");
        if (ml.size() == 0) {
            UserTimeOut ut = new UserTimeOut(Long.toString(now));
            ut.save();

        } else {
            String mytime = "";
            /******get the current stored interaction time*/
            for (int x = 0; x < ml.size(); x++) {
                mytime = ml.get(x).getLasttime();

            }

            /******get the current stored interaction time*/

            /****insert the new interaction time***/
            UserTimeOut myut = UserTimeOut.findById(UserTimeOut.class, 1);
            myut.lasttime = Long.toString(now);
            myut.save();
            /****insert the new interaction time***/

            long mytimelong = Long.parseLong(mytime);
            long diff = now - mytimelong;

            if ((diff / 1000) > 30) {
//                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(), Mylogin.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
                SharedPreferences.Editor myedit = settings.edit();
                myedit.putString(LOGGED_IN, "false");
                myedit.commit();

                startActivity(i);
                finish();

//                return true;

            } else {

//                Toast.makeText(this, "interaction difference is " + diff, Toast.LENGTH_SHORT).show();
            }


        }

    }

}
