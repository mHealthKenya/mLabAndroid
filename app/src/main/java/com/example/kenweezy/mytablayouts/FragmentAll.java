package com.example.kenweezy.mytablayouts;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;
import com.example.kenweezy.mytablayouts.printing.BluetoothDemo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentAll extends Fragment  implements AdapterView.OnItemSelectedListener{

    EditText frmweek,toweek;

    List myprintlist;

    MCrypt mcrypt=new MCrypt();
    BroadcastReceiver broadcastReceiver;
    MessageDialog mdialog;


    View v;
    Myshortcodes msc=new Myshortcodes();
    boolean dateVisible,weekVisible;
    Button allr,printResBut;

    boolean allSelected;

    ImageView imvall;
    DatePickerDialog datePickerDialog;

    String[] otpions = {"please select an Option","filter by date","Filter by week"};
    SpinnerAdapter optionsAdapter;
    String optionsSelected;
    Spinner filterspinner;

    public FragmentAll(){



    }


    private List<Mydata> mymesslist;
    private static FragmentAll inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int counter=0;
    String smsMessage = "";

    Progress pr=new Progress();



    public static FragmentAll instance() {
        return inst;
    }

    public static FragmentAll newInstance() {

        return (new FragmentAll());

    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }



    @Override
    public void onResume() {
        super.onResume();
        inst = this;

    }




    private MessagesAdapter myadapter;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmentallall, container, false);
        lv=(ListView) v.findViewById(R.id.lvallall);

        initialise();
        inst=this;


//        checkIfMessagesChecked();

        printResultsListener();
        checkAllListener();
        allr.setEnabled(false);
//        dateListener();
//        populateFilterSpinner();
//        setSpinnerListeners();



        mymesslist=new ArrayList<>();
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for(int x=0;x<bdy.size();x++){

            counter += 1;
            String messbdy=bdy.get(x).getmBody();
            String messId=bdy.get(x).getMessageId();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();
            String mychk=bdy.get(x).getChkd();
            String mvcnt=bdy.get(x).getViralCount();
            int vcount=Integer.parseInt(mvcnt);
            boolean mychkB;
            if(mychk.contentEquals("true")){
                mychkB=true;
            }
            else{
                mychkB=false;
            }

            String[] checkSplitdate=ndate.split("/");


            if(checkSplitdate.length>1){

            }
            else{
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(ndate));
                ndate = formatter.format(calendar.getTime());

            }



            mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));


        }
        Collections.sort(mymesslist,Mydata.VlcountComparator);

        myadapter=new MessagesAdapter(getActivity(),mymesslist);
        lv.setAdapter(myadapter);

        setClickListener();
        setLongClickListener();

        setDatePickerFrm();
        checkFrmDateListener();
        getAllMessages();

        //until here



//        refreshSmsInbox();



        printVirals();


        return v;
    }




    public void checkIfMessagesChecked(){

        try{

            myprintlist=new ArrayList();

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);
            for(int d=0;d<bdy.size();d++){


//                System.out.println("********list*****"+bdy.get(d).getChkd());
                myprintlist.add(bdy.get(d).getChkd());



            }
            if(myprintlist.contains("true")){
//                Toast.makeText(getActivity(), "has a checked item", Toast.LENGTH_SHORT).show();

                printResBut.setVisibility(View.VISIBLE);


            }
            else{

                printResBut.setVisibility(View.GONE);

//                Toast.makeText(getActivity(), "has no checked item", Toast.LENGTH_SHORT).show();


            }


//            if(bdy.contains("true")){
//                Toast.makeText(getActivity(), "messages checked", Toast.LENGTH_SHORT).show();
//            }
//
//            else{
//
//                Toast.makeText(getActivity(), "not checked", Toast.LENGTH_SHORT).show();
//            }
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "exception occured "+e, Toast.LENGTH_SHORT).show();
            System.out.println("*****error occured***"+e);


        }

    }

    public void UncheckAllMessages(){


        try{

//            Toast.makeText(getActivity(), "all messages checked", Toast.LENGTH_SHORT).show();




            //new code here


            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

            for(int x2=0;x2<bdy.size();x2++){



                Mydata model = mymesslist.get(x2);

                if (model.isSelected()) {

                    model.setSelected(false);
//                   for(int x2=0;x2<myl.size();x2++){

                    Messages ms=(Messages) bdy.get(x2);


                    ms.getId();

                    ms.setChkd("false");
                    ms.save();

//                   }

                }
                else{

                    model.setSelected(false);
//                   for(int x=0;x<myl.size();x++){

                    Messages ms=(Messages) bdy.get(x2);


                    ms.getId();

                    ms.setChkd("false");
                    ms.save();

//                   }

                }
                mymesslist.set(x2, model);


            }

            myadapter.notifyDataSetChanged();

            //new code here






            checkIfMessagesChecked();





        }
        catch(Exception e){


        }
    }




    public void checkAllMessages(){


       try{

//           Toast.makeText(getActivity(), "all messages checked", Toast.LENGTH_SHORT).show();



           //new code here


           List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

           for(int x2=0;x2<bdy.size();x2++){



               Mydata model = mymesslist.get(x2);

               if (model.isSelected()) {

                   model.setSelected(true);
//                   for(int x2=0;x2<myl.size();x2++){

                   Messages ms=(Messages) bdy.get(x2);


                   ms.getId();

                   ms.setChkd("true");
                   ms.save();

//                   }

               }
               else{

                   model.setSelected(true);
//                   for(int x=0;x<myl.size();x++){

                   Messages ms=(Messages) bdy.get(x2);


                   ms.getId();

                   ms.setChkd("true");
                   ms.save();

//                   }

               }
               mymesslist.set(x2, model);


           }

           myadapter.notifyDataSetChanged();

           //new code here







           checkIfMessagesChecked();




       }
       catch(Exception e){


       }
    }


    public void setSelectAll(){

        try{

            List myl=AllMessagesChecked.findWithQuery(AllMessagesChecked.class,"Select * from All_messages_checked",null);


            if(myl.size()==0){



                imvall.setBackgroundResource(R.drawable.check);
            }

            else{

                imvall.setBackgroundResource(R.drawable.checked);


            }

        }
        catch(Exception e){


        }
    }

    public void checkAllListener(){

        try{

            imvall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    pr.progressing(getActivity(),"loading..","selecting all results");

                    Handler mHand  = new Handler();
                    mHand.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub




                            List myl=AllMessagesChecked.findWithQuery(AllMessagesChecked.class,"Select * from All_messages_checked",null);


                            if(myl.size()==0){



                                imvall.setBackgroundResource(R.drawable.checked);

                                checkAllMessages();

                                AllMessagesChecked alc=new AllMessagesChecked("checked");
                                alc.save();



                            }

                            else{


                                imvall.setBackgroundResource(R.drawable.check);
                                UncheckAllMessages();
                                AllMessagesChecked.deleteAll(AllMessagesChecked.class);


                            }
                            pr.DissmissProgress();




                            //Dismiss progressBar here

                        }
                    }, 2000);



//
////                    if(allSelected){
//
////                        allSelected=false;
//
//                        if((Integer)imvall.getTag()==R.drawable.check){
//
////                            Toast.makeText(getActivity(), "am not checked tag"+imvall.getTag().toString(), Toast.LENGTH_SHORT).show();
//
//                            imvall.setTag(R.drawable.checked);
//                            imvall.setBackgroundResource(R.drawable.checked);
//
//                            checkAllMessages();
//
//
//
//
//                        }
//                        else{
//
//                            imvall.setTag(R.drawable.check);
//                            imvall.setBackgroundResource(R.drawable.check);
//                            UncheckAllMessages();
//
////                            Toast.makeText(getActivity(), "am checked tag"+imvall.getTag().toString(), Toast.LENGTH_SHORT).show();
//
//                        }

//                        imvall.setBackgroundResource(R.drawable.check);
//
//
//                        checkAllMessages();

//                    }
//                    else{
//
//                        allSelected=true;
//                        imvall.setBackgroundResource(R.drawable.checked);
//                        checkAllMessages();
//                    }


                }
            });
        }
        catch(Exception e){


        }
    }


    List<Messages> mymess;
    public void printResultsListener(){

        try{

            printResBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> myarr=new ArrayList<String>();

//                    Toast.makeText(getActivity(), "coming soonest", Toast.LENGTH_SHORT).show();
                    mymess=Messages.findWithQuery(Messages.class,"select * from Messages where chkd=? group by m_body","true");
                    System.out.println("*****checked**"+mymess.size());

                    for(int mess=0;mess<mymess.size();mess++){

                        System.out.println("***queued message body****"+mymess.get(mess).getmBody());
                        myarr.add(mymess.get(mess).getmBody());
                    }
//                    System.out.println("****print queue size is****"+myprintlist.size());
//                    for(int my=0;my<myprintlist.size();my++){
//
//                        System.out.println("***print queues***"+myprintlist.get(my));
//                    }

                    imvall.setBackgroundResource(R.drawable.check);
                    UncheckAllMessages();
                    AllMessagesChecked.deleteAll(AllMessagesChecked.class);
                    mymess.clear();

                    Intent myint=new Intent(getActivity(), BluetoothDemo.class);
                    myint.putStringArrayListExtra("printmess",myarr);
                    startActivity(myint);


                }
            });
        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            mdialog=new MessageDialog(getActivity());
            frmweek=(EditText) v.findViewById(R.id.filter_frmweek);
            toweek=(EditText) v.findViewById(R.id.filter_toweek);
            allr=(Button) v.findViewById(R.id.allres);
            printResBut=(Button) v.findViewById(R.id.printres);
            imvall=(ImageView) v.findViewById(R.id.iv_check_all);

            allSelected=false;

            setSelectAll();


        }
        catch(Exception e){


        }
    }

    public void setDatePickerFrm(){

        try{

            frmweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    frmweek.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
        catch(Exception e){


        }


    }

    public void ValidateToDate(){

        try{

            String frmweeks=frmweek.getText().toString().trim();
            if(!frmweeks.isEmpty()){

                toweek.setEnabled(true);
                allr.setEnabled(false);
                setDatePickerTo();
                checkToDateListener();

            }
            else{
                toweek.setText("");
                toweek.setEnabled(false);
                RetrieveAllResults();
                allr.setEnabled(false);
            }
        }
        catch(Exception e){


        }
    }


    public void checkFrmDateListener(){

        try{

            frmweek.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    Toast.makeText(getActivity(), "before change", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "on change", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void afterTextChanged(Editable s) {

//                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                    Calendar cal = Calendar.getInstance();
//                    System.out.println("**current date is**"+dateFormat.format(cal)); //2016/11/16 12:08:43


                    try{


                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
                        String currentArray[]=timeStamp.split("\\.");
                        String currentDate=currentArray[2];
                        String currentMonth=currentArray[1];
                        String currentYear=currentArray[0];
                        System.out.println("**** the current date is *****"+timeStamp);

                        String frmweeks=frmweek.getText().toString().trim();

                        System.out.println("***from weeks is**"+frmweeks);
                        String[] weekarray=frmweeks.split("/");
                        String edate=weekarray[0];
                        String emnth=weekarray[1];
                        String eyear=weekarray[2];

                        int edateI=Integer.parseInt(edate);
                        int emnthI=Integer.parseInt(emnth);
                        int eyearI=Integer.parseInt(eyear);

                        int cdateI=Integer.parseInt(currentDate);
                        int cmnthI=Integer.parseInt(currentMonth);
                        int cyearI=Integer.parseInt(currentYear);

                        if(eyearI==cyearI && emnthI==cmnthI && edateI>cdateI){

                            frmweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        }

                        else if(eyearI==cyearI && emnthI>cmnthI){

                            frmweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        }

                        else if(eyearI>cyearI){

                            frmweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();
                            ValidateToDate();


                        }
                        else{

                            ValidateToDate();

                        }


                    }
                    catch(Exception e){


                    }








                }
            });
        }
        catch(Exception e){


        }
    }



    public void checkToDateListener(){

        try{

            toweek.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    Toast.makeText(getActivity(), "before change", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Toast.makeText(getActivity(), "on change", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void afterTextChanged(Editable s) {

//                    Toast.makeText(getActivity(), "after change", Toast.LENGTH_SHORT).show();

                    try{



                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
                        String currentArray[]=timeStamp.split("\\.");
                        String currentDate=currentArray[2];
                        String currentMonth=currentArray[1];
                        String currentYear=currentArray[0];

                        int cdateI=Integer.parseInt(currentDate);
                        int cmnthI=Integer.parseInt(currentMonth);
                        int cyearI=Integer.parseInt(currentYear);






                        String frmweeks=frmweek.getText().toString().trim();

                        System.out.println("***from weeks is**"+frmweeks);
                        String[] weekarray=frmweeks.split("/");
                        String edate=weekarray[0];
                        String emnth=weekarray[1];
                        String eyear=weekarray[2];

                        int edateI=Integer.parseInt(edate);
                        int emnthI=Integer.parseInt(emnth);
                        int eyearI=Integer.parseInt(eyear);


                        String toweeks=toweek.getText().toString().trim();

                        System.out.println("***to weeks is**"+toweeks);
                        String[] toweekarray=toweeks.split("/");
                        String toedate=toweekarray[0];
                        String toemnth=toweekarray[1];
                        String toeyear=toweekarray[2];

                        int toedateI=Integer.parseInt(toedate);
                        int toemnthI=Integer.parseInt(toemnth);
                        int toeyearI=Integer.parseInt(toeyear);






                        if(toeyearI==cyearI && toemnthI==cmnthI && toedateI>cdateI){

                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();


                        }

                        else if(toeyearI==cyearI && toemnthI>cmnthI){

                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();



                        }

                        else if(toeyearI>cyearI){

                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date less than today", Toast.LENGTH_SHORT).show();

                        }


                        else if(toeyearI==eyearI && toemnthI==emnthI && toedateI<edateI){
                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        }

                        else if(toeyearI==eyearI && toemnthI<emnthI){
                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        }
                        else if(toeyearI<eyearI){

                            toweek.setText("");
                            Toast.makeText(getActivity(), "choose a date greater than from week", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            filterMessagesByDate();
                            allr.setEnabled(true);


                        }



                    }
                    catch(Exception e){


                    }


                }
            });
        }
        catch(Exception e){


        }
    }

    public void setDatePickerTo(){

        try{

            toweek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    toweek.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

        }
        catch(Exception e){


        }


    }


    public void setLongClickListener(){

        try{

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {





                                        try{

                        boolean txtChkd;

                        String msgbdy=mymesslist.get(position).getMsgbody();




                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);

                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);



                        if (bdy.isEmpty())
                            return false;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String messId=bdy.get(x).getMessageId();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();
                            String chkds=bdy.get(x).getChkd();
                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;


//                                AllMessagesChecked.deleteAll(AllMessagesChecked.class);
//                                setSelectAll();
//                                imvall.setBackgroundResource(R.drawable.check);

                            }


                            String[] checkSplitdate=ndate.split("/");


                            if(checkSplitdate.length>1){

                            }
                            else{
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }




                            mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));





                        }

                        Collections.sort(mymesslist,Mydata.VlcountComparator);

                        Mydata model = mymesslist.get(position);

                        if (model.isSelected()) {

                            model.setSelected(false);
                            for(int x=0;x<myl.size();x++){

                                Messages ms=(Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("false");
                                ms.save();

                            }

                        }
                        else{

                            model.setSelected(true);
                            for(int x=0;x<myl.size();x++){

                                Messages ms=(Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("true");
                                ms.save();

                            }

                        }
                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();

                        checkIfMessagesChecked();
                    }
                    catch(Exception e){


                    }

                    return true;


                }
            });
        }
        catch(Exception e){


        }
    }


    public void setClickListener(){

        try{



            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgId=mymesslist.get(position).getMsgId();
                        String msgdate=mymesslist.get(position).getDate();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);
                        mdialog.displayMessage(msgbdy,msgdate);

//                    System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);
                        for(int x=0;x<myl.size();x++){

                            Messages ms=(Messages) myl.get(x);
                            if(ms.getRead().contentEquals("read")){
                                sending=false;

                            }
                            else{

                                sending=true;
                                ms.getId();
                                ms.setRead("read");
                                ms.setDateRead(mytime);
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                                ms.save();
                            }
                        }


                        if(sending){

                            String sendMessage=msgId;
                            SmsManager sm = SmsManager.getDefault();
//                            sm.sendTextMessage(msc.sendSmsShortcode, null,sendMessage, null, null);

                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendMessage));

                            ArrayList<String> parts = sm.divideMessage(encrypted);
                            sm.sendMultipartTextMessage(msc.sendSmsShortcode, null, parts, null, null);

                        }





                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String messId=bdy.get(x).getMessageId();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();
                            String chkds=bdy.get(x).getChkd();
                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;
                            }


                            String[] checkSplitdate=ndate.split("/");


                            if(checkSplitdate.length>1){

                            }
                            else{
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }




                            mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));





                        }

                        Collections.sort(mymesslist,Mydata.VlcountComparator);

                        Mydata model = mymesslist.get(position);

                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}




                }
            });



        }
        catch(Exception e){


        }
    }



    public void MydialogBuilder(final String message,final String date){

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setMessage(message+"\n"+date);
        b.setCancelable(false);

        b.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        b.setNeutralButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message+"\n"+date);
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, date);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void test(){
//        Toast.makeText(getActivity(), "testing calls", Toast.LENGTH_SHORT).show();
    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {
            myadapter.getFilter().filter(s.toString());
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }





    }


    public void refreshSmsInbox() {
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

            if (bdy.isEmpty())
                return;
//            myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();
                String messId=bdy.get(x).getMessageId();



                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String mychkd=bdy.get(x).getChkd();

                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);
                boolean txtChkd;

                if(mychkd.contentEquals("true")){

                    txtChkd=true;
                }
                else{
                    txtChkd=false;


                }


                String bdycont=messbdy+"@"+ndate;


                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }


                mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));

//                myadapter.add(bdycont);



            }

        }
        catch(Exception e){

        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


//        switch(parent.getId()){
//
//            case R.id.filter_spinner:
//
//                optionsSelected=optionsAdapter.getItem(filterspinner.getSelectedItemPosition()).toString();
//
//                try{
//                    if(optionsSelected.contentEquals("please select an Option")){
//                        dayl.setVisibility(View.GONE);
//                        weekl.setVisibility(View.GONE);
//                        dateVisible=false;
//                        weekVisible=false;
//                        checkListeners();
//
//
//                    }
//                    else if(optionsSelected.contentEquals("filter by date")){
//                        weekl.setVisibility(View.GONE);
//                        dayl.setVisibility(View.VISIBLE);
//                        dateVisible=true;
//                        weekVisible=false;
//                        checkListeners();
//
//
//                    }
//                   else{
//
//                        dayl.setVisibility(View.GONE);
//                        weekl.setVisibility(View.VISIBLE);
//                        dateVisible=false;
//                        weekVisible=true;
//
//                        checkListeners();
//
//
//
//                    }
//                }
//                catch(Exception e){
//
//
//                }
//                break;
//
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }





    public void filterMessagesByDate(){

        try{



            String frmweekdate=frmweek.getText().toString();
            String toweekdate=toweek.getText().toString();
            mymesslist.clear();
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();
                String messId=bdy.get(x).getMessageId();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String mychk=bdy.get(x).getChkd();
                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);
                boolean mychkB;
                if(mychk.contentEquals("true")){
                    mychkB=true;
                }
                else{
                    mychkB=false;
                }

                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }

                System.out.println("*******the ndate is "+ndate+"my to date is "+toweekdate);

                String mynewndate[]=ndate.split("\\s+");
                String thendate=mynewndate[0];
                String thendatesplit[]=thendate.split("/");

                String theday=thendatesplit[0];
                String themnth=thendatesplit[1];
                String theyear=thendatesplit[2];

                String fromthedate[]=frmweekdate.split("/");
                String tothedate[]=toweekdate.split("/");

                String frmday=fromthedate[0];
                String frmmnth=fromthedate[1];
                String frmyear=fromthedate[2];

                String today=tothedate[0];
                String tomnth=tothedate[1];
                String toyear=tothedate[2];

                int thedayi=Integer.parseInt(theday);
                int themnthi=Integer.parseInt(themnth);
                int theyeari=Integer.parseInt(theyear);

                int frmdayi=Integer.parseInt(frmday);
                int frmmnthi=Integer.parseInt(frmmnth);
                int frmyeari=Integer.parseInt(frmyear);

                int todayi=Integer.parseInt(today);
                int tomnthi=Integer.parseInt(tomnth);
                int toyeari=Integer.parseInt(toyear);

//                if(theyeari<=toyeari && theyeari>=frmyeari){
//
//                    mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount));
//
//                }
//                else if(theyeari==toyeari && theyeari==frmyeari && themnthi<=tomnthi && themnthi>=frmmnthi){
//
//                    mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount));
//
//                }
                if(theyeari==toyeari && theyeari==frmyeari && themnthi==tomnthi && themnthi==frmmnthi && thedayi<=todayi && thedayi>=frmdayi){

                    mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));


                }
                else if(theyeari==toyeari && theyeari==frmyeari && themnthi<=tomnthi && themnthi>=frmmnthi){

                    mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));

                }

                else if(theyeari<toyeari && theyeari>frmyeari){

                    mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));

                }


            }
            Collections.sort(mymesslist,Mydata.VlcountComparator);



            myadapter=new MessagesAdapter(getActivity(),mymesslist);
            lv.setAdapter(myadapter);



        }
        catch(Exception e){


        }
    }





    public void getAllMessages(){

        try{

            allr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RetrieveAllResults();
                    allr.setEnabled(false);


                }
            });





        }
        catch(Exception e){


        }
    }


    public void RetrieveAllResults(){

        try{


            String frmweekdate=frmweek.getText().toString();
            String toweekdate=toweek.getText().toString();
            mymesslist.clear();
            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String messId=bdy.get(x).getMessageId();
                String mychk=bdy.get(x).getChkd();
                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);
                boolean mychkB;
                if(mychk.contentEquals("true")){
                    mychkB=true;
                }
                else{
                    mychkB=false;
                }

                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }

                mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));


            }
            Collections.sort(mymesslist,Mydata.VlcountComparator);



            myadapter=new MessagesAdapter(getActivity(),mymesslist);
            lv.setAdapter(myadapter);

            frmweek.setText("");
            toweek.setText("");
            toweek.setEnabled(false);
        }
        catch(Exception e){


        }
    }


    public void printVirals(){

        try{

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages group by m_body", null);

            for(int d=0;d<bdy.size();d++){

                System.out.println("****body:**"+bdy.get(d).getmBody()+"****count****"+bdy.get(d).getViralCount());
            }


        }
        catch(Exception e){


        }
    }


}
