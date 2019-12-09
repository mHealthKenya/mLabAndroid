package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.MakeCalls.makeCalls;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;
import com.example.kenweezy.mytablayouts.sendmessages.SendMessage;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentEidNegative extends Fragment {

    private List<Mydata> mymesslist;

    View v;


    private static FragmentEidNegative inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    TextView tvcontent;
    TextView tvRead;
    int counter = 0;
    String smsMessage = "";
    Myshortcodes msc = new Myshortcodes();
    MCrypt mcrypt = new MCrypt();
    MessageDialog mdialog;

    public static final String READ_SETTINGS = "READ_SETTINGS";

    public static FragmentEidNegative instance() {
        return (new FragmentEidNegative());
    }

    makeCalls mc;
    SendMessage sm;
    Base64Encoder encoder;

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    ListView lv;
    private MessagesAdapter myadapter;

    private void initialise() {

        mc = new makeCalls(getActivity());
        sm = new SendMessage(getActivity());
        encoder = new Base64Encoder();

    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmenteidsuppressed, container, false);
        lv = (ListView) v.findViewById(R.id.lveidsuppressed);
//        fl=(FrameLayout) v.findViewById(R.id.eid);
        initialise();

        mymesslist = new ArrayList<>();
        mdialog = new MessageDialog(getActivity());
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for (int x = 0; x < bdy.size(); x++) {

            counter += 1;
            String messbdy = bdy.get(x).getmBody();
            String messId = bdy.get(x).getMessageId();
            String ndate = bdy.get(x).getmTimeStamp();
            String read = bdy.get(x).getRead();
            String mvcnt = bdy.get(x).getViralCount();
            int vcount = Integer.parseInt(mvcnt);

            String mychk = bdy.get(x).getChkd();
            boolean mychkB;
            if (mychk.contentEquals("true")) {
                mychkB = true;
            } else {
                mychkB = false;
            }

            String[] checkSplitdate = ndate.split("/");


            if (checkSplitdate.length > 1) {

            } else {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(ndate));
                ndate = formatter.format(calendar.getTime());

            }

            mymesslist.add(new Mydata(mychkB, messbdy, ndate, read, vcount, messId));


        }


        myadapter = new MessagesAdapter(getActivity(), mymesslist);
        lv.setAdapter(myadapter);
        onclick();
        onLongClick();


        return v;
    }


    public void onLongClick() {

        try {

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread = (TextView) view.findViewById(R.id.mstitle);
//                tvread.setText("read");
                    boolean sending = false;
                    boolean txtChkd;

                    try {

                        String msgbdy = mymesslist.get(position).getMsgbody();
                        String msgdate = mymesslist.get(position).getDate();

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime = timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                    MydialogBuilder(msgbdy,msgdate);

                        System.out.println("/*****///// " + msgbdy);
                        List myl = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body=?", msgbdy);
                        for (int x = 0; x < myl.size(); x++) {

                            Messages ms = (Messages) myl.get(x);

                        }


                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

                        if (bdy.isEmpty())
                            return false;
//        myadapter.clear();


                        for (int x = 0; x < bdy.size(); x++) {

                            counter += 1;
                            String messbdy = bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String messId = bdy.get(x).getMessageId();
                            String read = bdy.get(x).getRead();
                            String mvcnt = bdy.get(x).getViralCount();
                            int vcount = Integer.parseInt(mvcnt);

                            String chkds = bdy.get(x).getChkd();
                            if (chkds.contentEquals("true")) {

                                txtChkd = true;
                            } else {

                                txtChkd = false;
                            }


                            String[] checkSplitdate = ndate.split("/");


                            if (checkSplitdate.length > 1) {

                            } else {
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }

                            mymesslist.add(new Mydata(txtChkd, messbdy, ndate, read, vcount, messId));


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


//
                    return true;
                }
            });
        } catch (Exception e) {


        }
    }

    public void onclick() {

        try {


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread = (TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending = false;
                    boolean txtChkd;

                    try {

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
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%Negative%' group by m_body", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();


                        for (int x = 0; x < bdy.size(); x++) {

                            counter += 1;
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


                            if (checkSplitdate.length > 1) {

                            } else {
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }

                            mymesslist.add(new Mydata(txtChkd, messbdy, ndate, read, vcount, messId));


                        }

                        myadapter.notifyDataSetChanged();


                    } catch (Exception e) {
                    }


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setHasOptionsMenu(true);
    }


    public void doSearching(CharSequence s) {
        //refreshSmsInbox();
        try {
            myadapter.getFilter().filter(s.toString());
        } catch (Exception e) {

            Toast.makeText(getActivity(), "unable to filter: " + e, Toast.LENGTH_SHORT).show();
        }


    }
}
