package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentVlInvalid extends Fragment {

    private List<Mydata> mymesslist;
    private static FragmentVlInvalid inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    Myshortcodes msc=new Myshortcodes();
    MessageDialog mdialog;

    MCrypt mcrypt=new MCrypt();
    int counter=0;
    String smsMessage = "";
    public static FragmentVlInvalid instance() {
        return (new FragmentVlInvalid());
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    private MessagesAdapter myadapter;
    ListView lv;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragmentvlinvalid, container, false);
        lv=(ListView) v.findViewById(R.id.lvvlinvalid);
//        fl=(FrameLayout) v.findViewById(R.id.eid);

        mymesslist=new ArrayList<>();

        mdialog=new MessageDialog(getActivity());

        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);



        for(int x=0;x<bdy.size();x++){

            counter += 1;
            String messbdy=bdy.get(x).getmBody();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();

            String mvcnt=bdy.get(x).getViralCount();
            int vcount=Integer.parseInt(mvcnt);

            String mychk=bdy.get(x).getChkd();
            boolean mychkB;
            if(mychk.contentEquals("true")){
                mychkB=true;
            }
            else{
                mychkB=false;
            }

            if((messbdy.contains("Collect New Sample")||messbdy.contains("Collect new sexample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){




                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }

                mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount));


            }




        }



        myadapter=new MessagesAdapter(getActivity(),mymesslist);
        lv.setAdapter(myadapter);


         onclickListener();
        onLongclickListener();




        return v;
    }





  public void onclickListener(){

      try{


          lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                      TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgdate=mymesslist.get(position).getDate();

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);
                        mdialog.displayMessage(msgbdy,msgdate);

                        System.out.println("/*****///// "+msgbdy);
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

                            String sendMessage=msgbdy+"*"+mytime;
                            SmsManager sm = SmsManager.getDefault();
                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendMessage));

                            ArrayList<String> parts = sm.divideMessage(encrypted);
                            sm.sendMultipartTextMessage(msc.sendSmsShortcode, null, parts, null, null);

                        }


                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();

                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);

                            String chkds=bdy.get(x).getChkd();
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;
                            }

                            if((messbdy.contains("Collect New Sample")||messbdy.contains("Collect new sexample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){


                                String[] checkSplitdate=ndate.split("/");


                                if(checkSplitdate.length>1){

                                }
                                else{
                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(Long.parseLong(ndate));
                                    ndate = formatter.format(calendar.getTime());

                                }

                                mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount));



                            }



                        }




                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}








              }
          });


      }
      catch(Exception e){


      }
  }

    public void onLongclickListener(){

        try{

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {





                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
//                  tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgdate=mymesslist.get(position).getDate();

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                      MydialogBuilder(msgbdy,msgdate);

                        System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",msgbdy);




                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

                        if (bdy.isEmpty())
                            return false;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();

                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);

                            String chkds=bdy.get(x).getChkd();
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;
                            }

                            if((messbdy.contains("Collect New Sample")||messbdy.contains("Collect new sexample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){


                                String[] checkSplitdate=ndate.split("/");


                                if(checkSplitdate.length>1){

                                }
                                else{
                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(Long.parseLong(ndate));
                                    ndate = formatter.format(calendar.getTime());

                                }

                                mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount));



                            }



                        }

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



                    }

                    catch(Exception e){}


                    return true;
                }
            });

        }

        catch (Exception e){



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





//    public void refreshSmsInbox() {
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40147'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return;
//            myadapter.clear();
//
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//                String msgaddr = smsInboxCursor.getString(indexAddress);
//
//                if (stw.contains("FFViral")&& stw.contains("Invalid")) {
//                    counter += 1;
//
//                    myadapter.add(mystrbdy);
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//        catch(Exception e){
//
//        }
//
//
//    }


    public void refreshSmsInbox() {
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

            if (bdy.isEmpty())
                return;
//            myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();




                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();

                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);

                String mychkd=bdy.get(x).getChkd();
                boolean txtChkd;

                if(mychkd.contentEquals("true")){

                    txtChkd=true;
                }
                else{
                    txtChkd=false;


                }



                if((messbdy.contains("Collect New Sample")||messbdy.contains("Collect new sexample")||messbdy.contains("Invalid")||messbdy.contains("Failed"))){





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
                    mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount));

//                myadapter.add(bdycont);
                    myadapter=new MessagesAdapter(getActivity(),mymesslist);



                }



            }

        }
        catch(Exception e){

        }


    }

    public void updateList(final String smsMessage) {
        try {
            refreshSmsInbox();
            myadapter.notifyDataSetChanged();
        }
        catch(Exception e){

        }
    }
}
