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

public class FragmentEidInvalid extends Fragment {

    private List<Mydata> mymesslist;
    private static FragmentEidInvalid inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int counter=0;
    String smsMessage = "";

    Myshortcodes msc=new Myshortcodes();
    MCrypt mcrypt=new MCrypt();

    public static FragmentEidInvalid instance() {
        return (new FragmentEidInvalid());
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
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragmenteidinvalid, container, false);
//        lv=(ListView) v.findViewById(R.id.lveidinvalid);
//        myadapter=new ArrayAdapter<String>(getActivity(),R.layout.listview_row,R.id.mytext,smsMessagesList);
//        lv.setAdapter(myadapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                try {
//
//                    final String[] smsMessages = smsMessagesList.get(position).split("\n");
//                    final String address = smsMessages[0];
//
//                    new AlertDialog.Builder(view.getContext())
//
//                            // .setNeutralButton("Share",null)
//                            // .setPositiveButton("Print",null)
//                            .setNeutralButton("Print", new DialogInterface.OnClickListener()
//
//                            {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent sendIntent = new Intent();
//                                    sendIntent.setAction(Intent.ACTION_SEND);
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, address);
//                                    sendIntent.setType("text/plain");
//                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
//
//
//                                }
//                            })
//                            .setMessage(address)
//                            .setNegativeButton("Close", null).show();
//
//
//
//
//
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//
//        refreshSmsInbox();
//
//
//
//
//        return v;
//    }
//



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragmenteidinvalid, container, false);
        lv=(ListView) v.findViewById(R.id.lveidinvalid);
//        fl=(FrameLayout) v.findViewById(R.id.eid);

        mymesslist=new ArrayList<>();
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for(int x=0;x<bdy.size();x++){


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

            String[] checkSplitdate=ndate.split("/");


            if(checkSplitdate.length>1){

            }
            else{
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(ndate));
                ndate = formatter.format(calendar.getTime());

            }

            if(messbdy.contains("Collect new sample") || messbdy.contains("Invalid")||messbdy.contains("Failed")){
                counter += 1;
                mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount));

            }





        }

//        myadapter=new ArrayAdapter<String>(getActivity(),R.layout.listview_row,R.id.mytext,smsMessagesList){
//
//            @NonNull
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                View v=super.getView(position, convertView, parent);
//
//                int[] positions={0,1,4,5,8};
//                int x=0;
//                int x2=1;
//                int x3=5;
//
//                TextView tv1=(TextView) v.findViewById(R.id.mytext);
//               if(position==2){
//
////                   Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
//               }
//               else{}
//
//
//                return v;
//
//            }
//        };


        myadapter=new MessagesAdapter(getActivity(),mymesslist);
        lv.setAdapter(myadapter);


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                try {
//
//                    String[] msgbdy=smsMessagesList.get(position).split("@");
//                    String date=msgbdy[1];
//                    String bdycont=msgbdy[0];
////                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
//
//                    final String[] smsMessages = smsMessagesList.get(position).split("\n");
//                    final String address = smsMessages[0];
//
//
//
//                    List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",bdycont);
//                    for(int x=0;x<myl.size();x++){
//
//                        Messages ms=(Messages) myl.get(x);
//                        ms.getId();
//                        ms.setRead("read");
////                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
//                        ms.save();
//                    }
//
//
//                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);
//
//                    for(int x=0;x<bdy.size();x++) {
//                        String messbdy = bdy.get(x).getmBody();
//                        String read=bdy.get(x).getRead();
//                        System.out.println(messbdy+" /*** "+read);
//                    }
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });



//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                TextView tvread=(TextView) view.findViewById(R.id.mstitle);
//                tvread.setText("read");
//
//                try{
//
//                    String msgbdy=mymesslist.get(position).getMsgbody();
//                    String msgdate=mymesslist.get(position).getDate();
//
////                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
//
//
//                    MydialogBuilder(msgbdy,msgdate);
//
//                    System.out.println("/*****///// "+msgbdy);
//                    List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=? group by m_body",msgbdy);
//                    for(int x=0;x<myl.size();x++){
//
//                        Messages ms=(Messages) myl.get(x);
//                        ms.getId();
//                        ms.setRead("read");
////                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
//                        ms.save();
//                    }
//
//
//                    mymesslist.clear();
//                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative' group by m_body", null);
//
//                    if (bdy.isEmpty())
//                        return;
////        myadapter.clear();
//
//
//                    for(int x=0;x<bdy.size();x++){
//
//                        counter += 1;
//                        String messbdy=bdy.get(x).getmBody();
//                        String ndate = bdy.get(x).getmTimeStamp();
//                        String read=bdy.get(x).getRead();
//
//                        mymesslist.add(new Mydata(messbdy,ndate,read));
//
//
//                    }
////                    myadapter.notifyDataSetChanged();
//
//
//
//                }
//
//                catch(Exception e){}
//
//
//            }
//        });
//
////        CheckVisibility();
//
//
////        refreshSmsInbox();
//
//
//
//
//        return v;
//    }


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


                    MydialogBuilder(msgbdy,msgdate);

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
                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

                    if (bdy.isEmpty())
                        return;
//        myadapter.clear();


                    for(int x=0;x<bdy.size();x++){


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

                        String[] checkSplitdate=ndate.split("/");


                        if(checkSplitdate.length>1){

                        }
                        else{
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(ndate));
                            ndate = formatter.format(calendar.getTime());

                        }

                        if(messbdy.contains("Collect new sample") || messbdy.contains("Invalid")||messbdy.contains("Failed")){
                            counter += 1;
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


            }
        });



//        refreshSmsInbox();




        return v;
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






//
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
//                if (stw.contains("FFEID")&& stw.contains("Invalid")) {
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

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty())
                return;
//            myadapter.clear();


            for(int x=0;x<bdy.size();x++){


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

                if(messbdy.contains("Collect new sample") || messbdy.contains("Invalid")||messbdy.contains("Failed")){
                    counter += 1;
                    mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount));

                }


                //                myadapter.add(bdycont);
                myadapter=new MessagesAdapter(getActivity(),mymesslist);


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
