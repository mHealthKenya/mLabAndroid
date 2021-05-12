package com.example.kenweezy.mytablayouts.printing;

/**
 * Created by root on 11/3/17.
 */


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.util.Log;
import android.os.Environment;


import com.er.bt.BluetoothService;
import com.er.bt.PrintImage;
import com.example.kenweezy.mytablayouts.R;


public class BluetoothDemo extends AppCompatActivity{
    Button btnScan;
    EditText txtSpace;
    Button btnSendTxt;
    Button btnPrintTestPage;
    Button btnDisconnect;
    Button btnOpenImg;// Browse pictures
    Button btnPrintImg;// Print picture
    Button print_1D;
    Button print_2D;
    String imgPath = "";// stored path
    Bitmap imgMap = null;// buffer
    private int printer_type = 48;//
    private static final int REQUEST_EXTERNAL = 3;
    private Spinner barcode_list;
    private Spinner TwoD_list;
    private int barcode_type = 0;//
    private int TwoD_type = 0;//
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private static String[] arr_barcode = { "UPC-A","UPC-E","EAN13", "EAN8","Code39","ITF","Codabar","Code93","Code128" };
    private static String[] arr_2d = { "QRCode", "PDF417" };
    View llay;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    revMsgThread revThred = null;
    private int PaperAvail = 0;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setToolBar();

        //This is the first important step
        mService = new BluetoothService(this, mHandler);

        if( mService.isAvailable() == false ){
            Toast.makeText(this, "Bluetooth is inavailable", Toast.LENGTH_LONG).show();
            finish();
        }
        else  //go to the initiate step if Bluetooth is available.
        {
            initial();
            HideButtons();
        }
    }

    public void HideButtons(){

        try{

//            btnDisconnect.setEnabled(false);
            btnDisconnect.setVisibility(View.GONE);
//            btnSendTxt.setEnabled(false);
            btnSendTxt.setVisibility(View.GONE);
        }
        catch(Exception e){


        }
    }

    public void setToolBar(){

        try{

            Toolbar tb=(Toolbar) findViewById(R.id.mytb);
            setSupportActionBar(tb);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if( mService.isBTopen() == false)
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        else
        {
            if(mService == null)
            {
                initial();
            }
        }
    }

    public void initial() {
        try {
            llay = (View)findViewById(R.id.llay);
            btnScan = (Button) this.findViewById(R.id.btnScan);
            btnScan.setOnClickListener(new ClickEvent());
            txtSpace = (EditText) findViewById(R.id.txtContent);
            btnSendTxt = (Button) this.findViewById(R.id.btnSendTxt);
            btnSendTxt.setOnClickListener(new ClickEvent());
            btnPrintTestPage = (Button) this.findViewById(R.id.btnPrintTestPage);
            btnPrintTestPage.setOnClickListener(new ClickEvent());
            btnDisconnect = (Button) this.findViewById(R.id.btnDisconnect);
            btnDisconnect.setOnClickListener(new ClickEvent());
            btnDisconnect.setEnabled(false);
            btnSendTxt.setEnabled(false);
            btnPrintTestPage.setEnabled(false);

            barcode_list = (Spinner) findViewById(R.id.spinner1);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_barcode);


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            barcode_list.setAdapter(adapter);

            barcode_list.setSelection(0);
            barcode_list.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    if (arg2 == 0) {
                        barcode_type = 65;
                    } else if (arg2 == 1) {
                        barcode_type = 66;
                    } else if (arg2 == 2) {
                        barcode_type = 67;
                    } else if (arg2 == 3) {
                        barcode_type = 68;
                    } else if (arg2 == 4) {
                        barcode_type = 69;
                    } else if (arg2 == 5) {
                        barcode_type = 70;
                    } else if (arg2 == 6) {
                        barcode_type = 71;
                    }else if (arg2 == 7) {
                        barcode_type = 72;
                    }else if (arg2 == 8) {
                        barcode_type = 73;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                    barcode_type = 73;

                }
            });

            print_1D =(Button)this.findViewById(R.id.print_1D);
            print_1D.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String msg = txtSpace.getText().toString();
                    if (msg==null || "".equals(msg)) {
                        Toast.makeText(BluetoothDemo.this, getString(R.string.content_null), Toast.LENGTH_SHORT).show();
                        txtSpace.requestFocus();
                        return;
                    }
                    byte[] cmd_align_content = new byte[3];
                    byte[] cmd_hri_print_position = new byte[3];
                    byte[] cmd_barcode_width = new byte[3];
                    byte[] cmd_hri_font = new byte[3];
                    byte[] cmd_barcode_height = new byte[3];
                    byte[] cmd_barcode_head = new byte[4];
                    byte[] cmd_linefeed = new byte[3];
                    //byte[] cmd_barcode_tail = new byte[4];
                    //mService.sendMessage(arr_barcode[barcode_type], "GBK");
                    cmd_align_content[0] = 0x1B;
                    cmd_align_content[1] = 0x61;
                    cmd_align_content[2] = 0x01;
                    mService.write(cmd_align_content); //center the printed content

                    cmd_hri_print_position[0] = 0x1D;
                    cmd_hri_print_position[1] = 0x48;
                    cmd_hri_print_position[2] = 0x02;
                    mService.write(cmd_hri_print_position); //set hri chars print position

                    cmd_barcode_width[0] = 0x1D;
                    cmd_barcode_width[1] = 0x77;
                    cmd_barcode_width[2] = 0x03;
                    mService.write(cmd_barcode_width); //set barcode width

                    cmd_hri_font[0] = 0x1D;
                    cmd_hri_font[1] = 0x66;
                    cmd_hri_font[2] = 0x00;
                    mService.write(cmd_hri_font); //set HRI font to FontA

                    cmd_barcode_height[0] = 0x1D;
                    cmd_barcode_height[1] = 0x68;
                    cmd_barcode_height[2] = 0x50;
                    mService.write(cmd_barcode_height); //set barcode height

                    cmd_barcode_head[0] = 0x1D;
                    cmd_barcode_head[1] = 0x6B;
                    cmd_barcode_head[2] = (byte)barcode_type;
                    cmd_barcode_head[3] = (byte)msg.length();
                    if(barcode_type==73)
                        cmd_barcode_head[3]+=2;
                    mService.write(cmd_barcode_head);//send reset command in front of each receipt print.
                    if(barcode_type==73){
                        byte[] cmd_ctl_code128= new byte[2];
                        cmd_ctl_code128[0]=123;
                        cmd_ctl_code128[1]=65;           //choose 128 codeset A
                        mService.write(cmd_ctl_code128);
                    }
                    mService.send_1D_2D_Chars(msg, "GBK");

                    cmd_align_content[0] = 0x1B;
                    cmd_align_content[1] = 0x61;
                    cmd_align_content[2] = 0x00;
                    mService.write(cmd_align_content); //center the printed content
                    cmd_linefeed[0] = 0x1B;
                    cmd_linefeed[1] = 0x4A;
                    cmd_linefeed[2] = 0x30;
                    mService.write(cmd_linefeed); //feed blank paper
                    //mService.write(cmd_barcode_tail);
                    return;
                }
            });

            TwoD_list = (Spinner) findViewById(R.id.spinner2);
            adapter1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_2d);


            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TwoD_list.setAdapter(adapter1);
            TwoD_list.setSelection(0);
            TwoD_list.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    if (arg2 == 0) {
                        TwoD_type = 0;
                    } else if (arg2 == 1) {
                        TwoD_type = 1;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                    TwoD_type = 0;

                }
            });

            print_2D =(Button)this.findViewById(R.id.print_2D);
            print_2D.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String msg = txtSpace.getText().toString();
                    if (msg==null || "".equals(msg)) {
                        Toast.makeText(BluetoothDemo.this, getString(R.string.content_null), Toast.LENGTH_SHORT).show();
                        txtSpace.requestFocus();
                        return;
                    }
                    if(TwoD_type==1){
                        byte[] cmd_barcode_width = new byte[3];
                        byte[] cmd_hri_font = new byte[3];
                        byte[] cmd_barcode_height = new byte[3];
                        byte[] cmd_align_content = new byte[3];
                        byte[] cmd_pdf417_head = new byte[8];
                        byte[] cmd_pdf417_tail = new byte[8];
                        byte[] cmd_linefeed = new byte[3];
                        //byte[] cmd_barcode_tail = new byte[4];
                        cmd_align_content[0] = 0x1B;
                        cmd_align_content[1] = 0x61;
                        cmd_align_content[2] = 0x01;
                        mService.write(cmd_align_content); //center the printed content

                        cmd_barcode_width[0] = 0x1D;
                        cmd_barcode_width[1] = 0x77;
                        cmd_barcode_width[2] = 0x02;
                        //mService.write(cmd_barcode_width); //set barcode width

                        cmd_hri_font[0] = 0x1D;
                        cmd_hri_font[1] = 0x66;
                        cmd_hri_font[2] = 0x00;
                        //mService.write(cmd_hri_font); //set HRI font to FontA

                        cmd_barcode_height[0] = 0x1D;
                        cmd_barcode_height[1] = 0x68;
                        cmd_barcode_height[2] = 0x50;
                        //mService.write(cmd_barcode_height); //set barcode height

                        cmd_pdf417_head[0] = 0x1D;
                        cmd_pdf417_head[1] = 0x28;
                        cmd_pdf417_head[2] = 0x6B;
                        cmd_pdf417_head[3] = (byte)((msg.length()+3)%256);
                        cmd_pdf417_head[4] = (byte)((msg.length()+3)/256);
                        cmd_pdf417_head[5] = 48;
                        cmd_pdf417_head[6] = 80;
                        cmd_pdf417_head[7] = 48;
                        mService.write(cmd_pdf417_head);//send reset command in front of each receipt print.
                        mService.send_1D_2D_Chars(msg, "GBK");

                        cmd_pdf417_tail[0] = 0x1D;
                        cmd_pdf417_tail[1] = 0x28;
                        cmd_pdf417_tail[2] = 0x6B;
                        cmd_pdf417_tail[3] = 3;
                        cmd_pdf417_tail[4] = 0;
                        cmd_pdf417_tail[5] = 48;
                        cmd_pdf417_tail[6] = 81;
                        cmd_pdf417_tail[7] = 48;
                        mService.write(cmd_pdf417_tail);
                        cmd_align_content[0] = 0x1B;
                        cmd_align_content[1] = 0x61;
                        cmd_align_content[2] = 0x00;
                        mService.write(cmd_align_content); //center the printed content
                        cmd_linefeed[0] = 0x1B;
                        cmd_linefeed[1] = 0x4A;
                        cmd_linefeed[2] = 0x30;
                        mService.write(cmd_linefeed);     //feed blank paper
                        return;
                    }else{
                        byte[] cmd_align_content = new byte[3];
                        byte[] cmd_barcode_width = new byte[3];
                        byte[] cmd_hri_font = new byte[3];
                        byte[] cmd_barcode_height = new byte[3];
                        byte[] cmd_qr_head = new byte[8];
                        byte[] cmd_qr_tail = new byte[8];
                        byte[] cmd_linefeed = new byte[3];
                        //byte[] cmd_barcode_tail = new byte[4];

                        cmd_align_content[0] = 0x1B;
                        cmd_align_content[1] = 0x61;
                        cmd_align_content[2] = 0x01;
                        mService.write(cmd_align_content); //center the printed content

                        cmd_barcode_width[0] = 0x1D;
                        cmd_barcode_width[1] = 0x77;
                        cmd_barcode_width[2] = 0x02;
                        //mService.write(cmd_barcode_width); //set barcode width

                        cmd_hri_font[0] = 0x1D;
                        cmd_hri_font[1] = 0x66;
                        cmd_hri_font[2] = 0x00;
                        //mService.write(cmd_hri_font); //set HRI font to FontA

                        cmd_barcode_height[0] = 0x1D;
                        cmd_barcode_height[1] = 0x68;
                        cmd_barcode_height[2] = 0x50;
                        //mService.write(cmd_barcode_height); //set barcode height

                        cmd_qr_head[0] = 0x1D;
                        cmd_qr_head[1] = 0x28;
                        cmd_qr_head[2] = 0x6B;
                        cmd_qr_head[3] = (byte)((msg.length()+3)%256);
                        cmd_qr_head[4] = (byte)((msg.length()+3)/256);
                        cmd_qr_head[5] = 49;
                        cmd_qr_head[6] = 80;
                        cmd_qr_head[7] = 48;
                        mService.write(cmd_qr_head);//send reset command in front of each receipt print.
                        mService.send_1D_2D_Chars(msg, "GBK");

                        cmd_qr_tail[0] = 0x1D;
                        cmd_qr_tail[1] = 0x28;
                        cmd_qr_tail[2] = 0x6B;
                        cmd_qr_tail[3] = 3;
                        cmd_qr_tail[4] = 0;
                        cmd_qr_tail[5] = 49;
                        cmd_qr_tail[6] = 81;
                        cmd_qr_tail[7] = 48;
                        mService.write(cmd_qr_tail);
                        cmd_align_content[0] = 0x1B;
                        cmd_align_content[1] = 0x61;
                        cmd_align_content[2] = 0x00;
                        mService.write(cmd_align_content); //center the printed content
                        cmd_linefeed[0] = 0x1B;
                        cmd_linefeed[1] = 0x4A;
                        cmd_linefeed[2] = 0x30;
                        mService.write(cmd_linefeed);     //feed blank paper
                        return;
                    }
                }
            });

            btnOpenImg = (Button) this.findViewById(R.id.browseImg);
            btnOpenImg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_EXTERNAL);
                }
            });
            btnPrintImg =(Button)this.findViewById(R.id.prtImg);
            btnPrintImg.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
					/*
					if (mService.getState() != mService.STATE_CONNECTED) {
						Toast.makeText(
								MainActivity.this,
								MainActivity.this.getResources().getString(
										R.string.str_unconnected), 2000).show();
						return;
					}
					*/
                    if (imgMap != null) {
                        Bitmap bmpOrg = imgMap;// BitmapFactory.decodeFile(picPath);
                        byte[] send;
                        PrintImage image = new PrintImage();
                        int w = bmpOrg.getWidth();
                        int h = bmpOrg.getHeight();
                        //PrintImage.PrintImageNew(
                        send=image.PrintImageNew(resizeImage(bmpOrg, printer_type * 8, h));
                        mService.write(send);
                        return;
                    }
                }
            });

            //btnPrintTestPage.setEnabled(true);
            //llay.setVisibility(View.GONE);
        } catch (Exception ex) {
            Log.e("exception one: ",ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void onBackPressed() {
        if (mService != null) {
            //mBTService.DisConnected();
            mService.disconnected();
            mService = null;
            //revThred.interrupt();
            btnDisconnect.setEnabled(false);
            btnSendTxt.setEnabled(false);
            btnPrintTestPage.setEnabled(false);
            llay.setVisibility(View.GONE);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.exit(0);
    }



    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {

            byte[] cmd_resume = new byte[2];
            byte[] cmd_bold = new byte[3];
            if (v == btnScan) {
                Intent serverIntent = new Intent(BluetoothDemo.this,DeviceListActivity.class);
                startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
            } else if (v == btnSendTxt) {
                
                try{





                    Intent intent=getIntent();
                    ArrayList<String> messageArray = intent.getStringArrayListExtra("printmess");

                    Iterator myit=messageArray.iterator();
                    while(myit.hasNext()){

                        cmd_resume[0] = 0x1B;
                        cmd_resume[1] = 0x40;
                        mService.write(cmd_resume);//send reset command in front of each receipt print.
                        mService.sendMessage(myit.next().toString()+"\n", "utf-16BE");


                    }




                    String msg = txtSpace.getText().toString();


                    if( msg.length() > 0 ){
                        cmd_resume[0] = 0x1B;
                        cmd_resume[1] = 0x40;
                        mService.write(cmd_resume);//send reset command in front of each receipt print.
                        mService.sendMessage(msg, "utf-16BE");
					/*
					cmd_bold[0] = 0x1B;
					cmd_bold[1] = 0x45;
					cmd_bold[2] = 0x00;
					mService.write(cmd_bold);
					//mService.sendMessage(msg, "GBK");
					mService.sendMessage(msg, "utf-16BE");
					cmd_bold[0] = 0x1B;
					cmd_bold[1] = 0x45;
					cmd_bold[2] = 0x01;
					mService.write(cmd_bold);
					mService.sendMessage(msg, "utf-16BE");
					cmd_bold[0] = 0x1B;
					cmd_bold[1] = 0x45;
					cmd_bold[2] = 0x00;
					mService.write(cmd_bold);

					cmd_bold[0] = 0x1D;
					cmd_bold[1] = 0x21;
					cmd_bold[2] = 0x01;
					mService.write(cmd_bold);
					//mService.sendMessage(msg, "GBK");
					mService.sendMessage(msg, "utf-16BE");
					cmd_bold[0] = 0x1B;
					cmd_bold[1] = 0x45;
					cmd_bold[2] = 0x01;
					mService.write(cmd_bold);
					mService.sendMessage(msg, "utf-16BE");
					cmd_bold[0] = 0x1B;
					cmd_bold[1] = 0x45;
					cmd_bold[2] = 0x00;
					mService.write(cmd_bold);
					cmd_bold[0] = 0x1D;
					cmd_bold[1] = 0x21;
					cmd_bold[2] = 0x00;
					mService.write(cmd_bold);
					*/
                    }
                    
                    
                }
                catch(Exception e){

                    Toast.makeText(BluetoothDemo.this, "error occured try again"+e, Toast.LENGTH_SHORT).show();
                    
                    
                }

            }else if (v == btnPrintTestPage){  //this will print a test page which guides you how send data to printer.
                String msg = "";
                String lang = getString(R.string.osLang);
                byte[] cmd_resume1 = new byte[4];
                cmd_resume1[0] = 0x1B;
                cmd_resume1[1] = 0x40;
                mService.write(cmd_resume);       //reset printer command.

                //printImage();                     //Print an image in front of receipt.
                byte[] cmd = new byte[3];

                if((lang.compareTo("en")) == 0){
                    cmd[0] = 0x1b;
                    cmd[1] = 0x45;
                    cmd[2] = 0x01;
                    mService.write(cmd);           //set bold font
                    mService.sendMessage("Thanks for choosing our printer!\n", "utf-16BE"); //print this message with bold font format.
                    cmd[2] = 0x00;
                    mService.write(cmd);           //cancel bold font
                    msg = "You have sucessfully got your\ndevice connected with printer.\n"
                            +"Please feel free to contact us \nif you need any help.\n\n";
                    mService.sendMessage(msg,"utf-16BE");

                }else if((lang.compareTo("ch")) == 0){
                    cmd[0] = 0x1b;
                    cmd[1] = 0x45;
                    cmd[2] = 0x01;
                    mService.write(cmd);           //set bold font
                    mService.sendMessage("Message test on bold!\n", "utf-16BE"); //print this message with bold font format.
                    cmd[2] = 0x00;
                    mService.write(cmd);           //cancel bold font
                    msg = "Bold cancel test,\n"
                            + "Bold cancel test!\n\n";
                    mService.sendMessage(msg,"utf-16BE");
                }
            }else if (v == btnDisconnect) {
                mService.stop();
            }
        }
    }



    private final  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            //Toast.makeText(getApplicationContext(), getString(R.string.success_connect),
                            //		Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), getString(R.string.success_connect),
                                    Toast.LENGTH_LONG).show();

                            btnDisconnect.setVisibility(View.VISIBLE);
                            btnSendTxt.setVisibility(View.VISIBLE);

                            btnDisconnect.setEnabled(true);
                            btnSendTxt.setEnabled(true);

//                            btnPrintTestPage.setEnabled(true);
//                            llay.setVisibility(View.VISIBLE);
                            //revThred = new revMsgThread();
                            //revThred.start();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d("connectstate","connecting...");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            Log.d("nonestate","no state..");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    //Toast.makeText(getApplicationContext(), getString(R.string.connect_lost),
                    //		Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), getString(R.string.connect_lost),
                            Toast.LENGTH_LONG).show();
                    btnDisconnect.setEnabled(false);
                    btnSendTxt.setEnabled(false);
                    btnPrintTestPage.setEnabled(false);
                    llay.setVisibility(View.GONE);
                    //revThred.interrupt();
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    //Toast.makeText(getApplicationContext(), getString(R.string.unable_connect),
                    //		Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), getString(R.string.unable_connect), Toast.LENGTH_LONG).show();
                    break;
                case BluetoothService.MESSAGE_READ:
                    byte printStatus = (byte)Integer.parseInt(msg.obj.toString());
                    if((printStatus & 0x60) == 0x60){
                        PaperAvail = 1;
                        //Toast.makeText(getApplicationContext(),getString(R.string.paper_stat),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),getString(R.string.paper_stat), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, getString(R.string.bt_stat), Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
                break;
            case  REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);
                    mService.connect(con_dev);
                }
                break;
            case  REQUEST_EXTERNAL:
                if (resultCode == RESULT_OK	&& null != data) {
                    Uri selectedImg = data.getData();
                    // String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Cursor cursor = getContentResolver().query(selectedImage,
                    // filePathColumn, null, null, null);
                    // cursor.moveToFirst();
                    // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    // String picturePath = cursor.getString(columnIndex);
                    // picPath = picturePath;
                    // // iv.setImageURI(selectedImage);
                    // btMap = BitmapFactory.decodeFile(picPath);
                    ContentResolver cr = this.getContentResolver();

                    try {
                        imgMap = BitmapFactory.decodeStream(cr
                                .openInputStream(selectedImg));
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (imgMap.getHeight() > 384) {
                        // btMap = BitmapFactory.decodeFile(picPath);
                        imgMap = resizeImage(imgMap, 384, 384);
                        //iv.setImageBitmap(btMap);

                    } else {
                        //iv.setImageBitmap(btMap);
                    }
                    // cursor.close();
                }
                break;
        }
    }


    //this thread is not used.
    class revMsgThread extends Thread {
        @Override
        public void run() {
            int revData = 0;
            try{
                while(true){
                    revData = mService.revByte();
                    if( revData != -1 ){
                        mHandler.obtainMessage(BluetoothService.MESSAGE_READ, 1, -1, revData)
                                .sendToTarget();
                    }
                    Thread.sleep(2000);
                    //Thread.sleep(1);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
                Log.d("revmsgthread",""+e.getMessage());
            }
        }
    }

    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] imgData = null;
        PrintImage image = new PrintImage();
        image.initCanvas(384);
        image.initPaint();
        String path = Environment.getExternalStorageDirectory() + "/icon2.jpg";//654.jpg
        Log.w("BluetoothDemo", "path is=="+path);
        image.drawImage(0, 0, path);
        imgData = image.printDraw();
        mService.write(imgData);

    }
}

