package com.example.kenweezy.mytablayouts.AccessServer;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.Dialogs.Dialog;
import com.example.kenweezy.mytablayouts.Progressing.Progress;

import java.util.HashMap;
import java.util.Map;


public class AccessServer {

    Context ctx;
    Progress pr;
    Dialog sweetdialog;


    public AccessServer(Context ctx) {
        this.ctx = ctx;

        pr = new Progress(ctx);
        sweetdialog = new Dialog(ctx);
    }





    public void signupUser(final String firstname, final String lastname, final String username , final String phone_no, final String password,
                           final String security, final String answer,final String selectedQn) {

        pr.showProgress("Signing up user.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SIGNUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        if(response.contains("Oops")){

                            sweetdialog.showErrorDialog(" "+response,"Error signing up");



                        }
                        else{


                            sweetdialog.showSuccessDialog("Success signing up "+response,"SUCCESS");




                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        sweetdialog.showErrorDialog("Error occured "+error.getMessage(), "Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.KEY_SIGNUP_FNAME, firstname);
                params.put(Config.KEY_SIGNUP_LNAME, lastname);
                params.put(Config.KEY_SIGNUP_UNAME, username);
                params.put(Config.KEY_SIGNUP_PWD, password);
                params.put(Config.KEY_SIGNUP_SECQN, security);
                params.put(Config.KEY_SIGNUP_ANS, answer);
                params.put(Config.KEY_SIGNUP_PHONE, phone_no);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }

    public void submitEidVlData(final String message) {

        pr.showProgress("Submitting data.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EIDVL_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();

                            sweetdialog.showSuccessDialog("Submit response "+response,"SUCCESS");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        sweetdialog.showErrorDialog("Error occured "+error.getMessage(), "Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("message", message);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public void submitHtsData(final String message) {

        pr.showProgress("Submitting data.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.HTS_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();

                        sweetdialog.showSuccessDialog("Submit response "+response,"SUCCESS");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        sweetdialog.showErrorDialog("Error occured "+error.getMessage(), "Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("message", message);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }

}
