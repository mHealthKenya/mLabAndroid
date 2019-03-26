package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;

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

public class FragmentVlSuppressed extends Fragment {

    private List<Mydata> mymesslist;
    private static FragmentVlSuppressed inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int counter = 0;
    String smsMessage = "";
    MessageDialog mdialog;

    Myshortcodes msc = new Myshortcodes();

    MCrypt mcrypt = new MCrypt();

    Base64Encoder encoder;
    SendMessage sm;

    public static FragmentVlSuppressed instance() {
        return (new FragmentVlSuppressed());
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    private void initialise() {

        encoder = new Base64Encoder();
        sm = new SendMessage(getActivity());
    }

    private MessagesAdapter myadapter;
    ListView lv;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentvlsuppressed, container, false);
        lv = (ListView) v.findViewById(R.id.lvvlsuppressed);
//        fl=(FrameLayout) v.findViewById(R.id.eid);


        initialise();

        mymesslist = new ArrayList<>();
        mdialog = new MessageDialog(getActivity());
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for (int x = 0; x < bdy.size(); x++) {


            try {


                String messbdy = bdy.get(x).getmBody();
                String ndate = bdy.get(x).getmTimeStamp();
                String read = bdy.get(x).getRead();
                String messId = bdy.get(x).getMessageId();
                String mvcnt = bdy.get(x).getViralCount();
                int vcount = Integer.parseInt(mvcnt);


                String mychk = bdy.get(x).getChkd();
                boolean mychkB;
                if (mychk.contentEquals("true")) {
                    mychkB = true;
                } else {
                    mychkB = false;
                }

                String mytimestamp = null;

                if (!(messbdy.contains("Collect New Sample") || messbdy.contains("Collect new sexample") || messbdy.contains("Invalid") || messbdy.contains("Failed"))) {

                    String[] checkSplitdate = ndate.split("/");


                    String[] mymessarray = messbdy.split(":");
                    String splitVal = "";

                    if (messbdy.contains("Sex") && messbdy.contains("Age")) {
                        splitVal = mymessarray[6];

                    } else {

                        splitVal = mymessarray[3];
                    }

//            System.out.println("the split array suppresed::::: is "+mymessarray[3]);

                    String[] splitvalarray = splitVal.split("\\s+");
                    if (checkSplitdate.length > 1) {

                    } else {

                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(ndate));
                        ndate = formatter.format(calendar.getTime());

                    }

                    if (splitvalarray[0].contains("<")) {
                        System.out.println("i am suppressed " + splitvalarray[0]);
                        counter += 1;
                        mymesslist.add(new Mydata(mychkB, messbdy, ndate, read, vcount, messId));


                    } else {

                        int myval = Integer.parseInt(splitvalarray[0]);
                        if (myval > 1000) {
                            System.out.println("i am unsuppressed with a value " + myval);

                        } else {
                            System.out.println("i am suppressed with a value " + myval);
                            counter += 1;


                            mymesslist.add(new Mydata(mychkB, messbdy, ndate, read, vcount, messId));


                        }


                    }

                }
            } catch (Exception e) {

                System.out.println("exception occured " + e);
            }


        }

        Collections.sort(mymesslist, Mydata.VlcountComparator);
        myadapter = new MessagesAdapter(getActivity(), mymesslist);
//        myadapter.notifyDataSetChanged();
        lv.setAdapter(myadapter);


        onclickListener();
        onLongClick();


//        refreshSmsInbox();


        return v;
    }


    public void onLongClick() {

        try {


            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread = (TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending = false;
                    boolean txtChkd;

                    try {

                        String msgbdy = mymesslist.get(position).getMsgbody();
                        String msgdate = mymesslist.get(position).getDate();

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime = timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);

                        System.out.println("/*****///// " + msgbdy);
                        List myl = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body=?", msgbdy);
                        for (int x = 0; x < myl.size(); x++) {


                        }


                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);
                        if (bdy.isEmpty())
                            return false;


                        for (int x = 0; x < bdy.size(); x++) {


                            String messbdy = bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read = bdy.get(x).getRead();
                            String messId = bdy.get(x).getMessageId();
                            String mvcnt = bdy.get(x).getViralCount();
                            int vcount = Integer.parseInt(mvcnt);


                            String chkds = bdy.get(x).getChkd();
                            if (chkds.contentEquals("true")) {

                                txtChkd = true;
                            } else {

                                txtChkd = false;
                            }


                            String[] checkSplitdate = ndate.split("/");

                            if (!(messbdy.contains("Collect New Sample") || messbdy.contains("Collect new sexample") || messbdy.contains("Invalid") || messbdy.contains("Failed"))) {

                                try {


                                    String[] mymessarray = messbdy.split(":");


                                    String splitVal = "";

                                    if (messbdy.contains("Sex") && messbdy.contains("Age")) {
                                        splitVal = mymessarray[6];

                                    } else {

                                        splitVal = mymessarray[3];
                                    }


                                    String[] splitvalarray = splitVal.split("\\s+");
                                    int myval = 0;

                                    if (checkSplitdate.length > 1) {

                                    } else {
                                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTimeInMillis(Long.parseLong(ndate));
                                        ndate = formatter.format(calendar.getTime());

                                    }


                                    if (!splitvalarray[0].contains("<")) {
                                        myval = Integer.parseInt(splitvalarray[0]);
                                    }
                                    if (splitvalarray[0].contains("<") || myval < 1000) {

                                        System.out.println("i am suppressed " + splitvalarray[0]);
                                        counter += 1;
                                        mymesslist.add(new Mydata(txtChkd, messbdy, ndate, read, vcount, messId));

                                        Collections.sort(mymesslist, Mydata.VlcountComparator);

                                        myadapter.notifyDataSetChanged();


                                    }
                                } catch (Exception e) {

//                                    Toast.makeText(getActivity(), "these is exception "+e, Toast.LENGTH_SHORT).show();
                                }


                            }


                        }


                        Mydata model = mymesslist.get(position);

                        if (model.isSelected()) {

                            model.setSelected(false);
                            for (int x = 0; x < myl.size(); x++) {

                                Messages ms = (Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("false");
                                ms.save();

                            }

                        } else {

                            model.setSelected(true);
                            for (int x = 0; x < myl.size(); x++) {

                                Messages ms = (Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("true");
                                ms.save();

                            }

                        }
                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();


                    } catch (Exception e) {
                    }

                    return true;


                }
            });
        } catch (Exception e) {


        }
    }


    public void onclickListener() {

        try {


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread = (TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending = false;
                    boolean txtChkd;


                    String msgbdy = mymesslist.get(position).getMsgbody();
                    String msgdate = mymesslist.get(position).getDate();
                    String msgId = mymesslist.get(position).getMsgId();

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                    String mytime = timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                    MydialogBuilder(msgbdy,msgdate);
                    mdialog.displayMessage(msgbdy, msgdate);

                    System.out.println("/*****///// " + msgbdy);
                    List myl = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body=?", msgbdy);
                    for (int x = 0; x < myl.size(); x++) {

                        Messages ms = (Messages) myl.get(x);

                        if (ms.getRead().contentEquals("read")) {
                            sending = false;

                        } else {
                            sending = true;
                            ms.getId();
                            ms.setRead("read");
                            ms.setDateRead(mytime);
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                            ms.save();
                        }

                    }

                    if (sending) {

                        String sendMessage = "read*" + msgId;

                        sm.sendMessageApi(encoder.encryptString(sendMessage), msc.sendSmsShortcode);

                    }


                    mymesslist.clear();

                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFViral%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


                    for (int x = 0; x < bdy.size(); x++) {

                        try {


                            String messbdy = bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read = bdy.get(x).getRead();
                            String mychk = bdy.get(x).getChkd();
                            String messId = bdy.get(x).getMessageId();
                            String mvcnt = bdy.get(x).getViralCount();
                            int vcount = Integer.parseInt(mvcnt);

                            boolean mychkB;
                            if (mychk.contentEquals("true")) {
                                mychkB = true;
                            } else {
                                mychkB = false;
                            }

                            if (!(messbdy.contains("Collect New Sample") || messbdy.contains("Collect new sexample") || messbdy.contains("Invalid") || messbdy.contains("Failed"))) {


                                String[] mymessarray = messbdy.split(":");


                                String splitVal = "";

                                if (messbdy.contains("Sex") && messbdy.contains("Age")) {
                                    splitVal = mymessarray[6];

                                } else {

                                    splitVal = mymessarray[3];
                                }


                                String[] splitvalarray = splitVal.split("\\s+");
                                if (splitvalarray[0].contains("<")) {
                                    System.out.println("i am suppressed " + splitvalarray[0]);

                                    counter += 1;
                                    mymesslist.add(new Mydata(mychkB, messbdy, ndate, read, vcount, messId));


                                } else {


                                    int myval = Integer.parseInt(splitvalarray[0]);
                                    if (myval < 1000) {
//                                        System.out.println("i am unsuppressed with a value "+myval);
                                        counter += 1;
                                        mymesslist.add(new Mydata(mychkB, messbdy, ndate, read, vcount, messId));

                                    } else {
//                                        System.out.println("i am suppressed with a value "+myval);


                                    }


                                }


                            }


                        } catch (Exception e) {

                            System.out.println("exception occured " + e);

                        }


                    }


                    Collections.sort(mymesslist, Mydata.VlcountComparator);

                    myadapter = new MessagesAdapter(getActivity(), mymesslist);
                    lv.setAdapter(myadapter);


                }
            });


        } catch (Exception e) {


        }
    }


    public void MydialogBuilder(final String message, final String date) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setMessage(message + "\n" + date);
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, message + "\n" + date);
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, date);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

            }
        });

        AlertDialog a = b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
    }


}
