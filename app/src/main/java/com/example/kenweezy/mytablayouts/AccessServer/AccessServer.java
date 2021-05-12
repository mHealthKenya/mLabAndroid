package com.example.kenweezy.mytablayouts.AccessServer;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.Dialogs.Dialog;
import com.example.kenweezy.mytablayouts.ProcessReceivedMessage.ProcessMessage;
import com.example.kenweezy.mytablayouts.Progressing.Progress;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;


public class AccessServer {

    Context ctx;
    Progress pr;
    Dialog sweetdialog;
    ProcessMessage pm;

    private JSONArray id_result;


    public AccessServer(Context ctx) {
        this.ctx = ctx;

        pr = new Progress(ctx);
        sweetdialog = new Dialog(ctx);
        pm=new ProcessMessage();
    }


    public void submitEidVlData(final String phone,final String message) {

        pr.showProgress("Submitting data.....");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EIDVL_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            pr.dissmissProgress();
                            System.out.println("****response ***");
                            System.out.println(response);

                            if(response.contains("Phone Number not Authorised to send remote samples")){
                                sweetdialog.showErrorDialog("Phone Number not Authorised to send remote samples", "Error");
                            } else {
                                sweetdialog.showSuccessDialog("Sample Remote Login Completed Succesfully", "SUCCESS");
                            }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();
                        System.out.println("*** error ***"+error);

                        sweetdialog.showErrorDialog("Error occured "+error.toString(), "Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("message", message);
                params.put("phone", phone);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public void submitHtsData(final String phone,final String message) {

        pr.showProgress("Submitting Hts data.....");

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

                        sweetdialog.showErrorDialog("Error occured "+error.toString(), "Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("message", message);
                params.put("phone", phone);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }




    public void getVlEidResultsFromDb(final String phone){

//        Toast.makeText(ctx, ""+phone, Toast.LENGTH_SHORT).show();

        try{

            pr.showProgress("getting results...");


            StringRequest stringRequest = new StringRequest(POST,Config.RESULTS_DATA_URL,
                    new Response.Listener<String>() {

//
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************messages*********************");
                            System.out.println(response);
//                            Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();

                            pr.dissmissProgress();
                            if(response.trim().equalsIgnoreCase("Phone Number not attached to any Facility")){

                                Toast.makeText(ctx, ""+response, Toast.LENGTH_LONG).show();

                            }
                            else{

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);
                                    System.out.println("****length****"+id_result.length());
                                    if(id_result.length()==0){

                                        Toast.makeText(ctx, "You do not have any results", Toast.LENGTH_LONG).show();

                                    }
                                    else{

                                        getMyVlEidResultsFromDb(id_result);

                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ctx, "error getting results, try again", Toast.LENGTH_SHORT).show();

                                }

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            System.out.println("******************error*************");
                            System.out.println(error);
                            Toast.makeText(ctx, "error occured, try again", Toast.LENGTH_LONG).show();


                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", Base64Encoder.encryptString(phone));

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting results "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyVlEidResultsFromDb(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String message = json.getString(Config.KEY_MESSAGECODE);

                pm.processReceivedMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "error occured "+ e, Toast.LENGTH_SHORT).show();
                System.out.println("********json error*********");
                System.out.println(e);
            }
        }




    }




    public void getHtsResultsFromDb(final String phone){

        Toast.makeText(ctx, ""+phone, Toast.LENGTH_SHORT).show();

        try{

            pr.showProgress("getting hts results...");


            StringRequest stringRequest = new StringRequest(POST,Config.GETHTSRESULTS_DATA_URL,
                    new Response.Listener<String>() {

                        //
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************messages*********************");
                            System.out.println(response);
//                            Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();

                            pr.dissmissProgress();
                            if(response.trim().equalsIgnoreCase("Phone Number not attached to any Facility")){

                                Toast.makeText(ctx, ""+response, Toast.LENGTH_LONG).show();

                            }
                            else{

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);
                                    System.out.println("****length****"+id_result.length());
                                    if(id_result.length()==0){

                                        Toast.makeText(ctx, "You do not have any results", Toast.LENGTH_LONG).show();

                                    }
                                    else{

                                        getMyHtsResultsFromDb(id_result);

                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ctx, "error getting results, try again", Toast.LENGTH_SHORT).show();

                                }

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            System.out.println("******************error*************");
                            System.out.println(error);
                            Toast.makeText(ctx, "error occured, try again", Toast.LENGTH_LONG).show();


                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", Base64Encoder.encryptString(phone));

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting results, try again ", Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyHtsResultsFromDb(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String message = json.getString(Config.KEY_MESSAGECODE);

                pm.processReceivedMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "error occured "+ e, Toast.LENGTH_SHORT).show();
                System.out.println("********json error*********");
                System.out.println(e);
            }
        }




    }




    public void getHistoricalResultsFromDb(final String phone,final String message){

        Toast.makeText(ctx, "the phone number"+phone, Toast.LENGTH_SHORT).show();
        System.out.println("*********number***********"+phone);
        System.out.println("*********encrypted number**********"+Base64Encoder.encryptString(phone));

        try{

            pr.showProgress("getting historical results...");


            StringRequest stringRequest = new StringRequest(POST,Config.HISTORICALRESULTS_DATA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************historical messages*********************");
                            System.out.println(response);
//                            Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();

                            pr.dissmissProgress();
                            if(response.trim().contains("No results were found for this period")){

                                Toast.makeText(ctx, ""+response, Toast.LENGTH_SHORT).show();

                            }
                            else if(response.trim().contains("Phone Number not Authorised to receive results")){

                                Toast.makeText(ctx, ""+response, Toast.LENGTH_SHORT).show();

                            }
                            else{

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);


                                    if(id_result.length()==0){

                                        Toast.makeText(ctx, "You do not have any results", Toast.LENGTH_SHORT).show();

                                    }
                                    else{

                                        getMyHistoricalResultsFromDb(id_result);

                                    }



                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Toast.makeText(ctx, "error getting results "+e, Toast.LENGTH_LONG).show();

                                }

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();
                            System.out.println("******************error*************");
                            System.out.println(error);
                            Toast.makeText(ctx, "error occured, try again ", Toast.LENGTH_SHORT).show();


                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", Base64Encoder.encryptString(phone));
                    params.put("message", Base64Encoder.encryptString(message));

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting results "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyHistoricalResultsFromDb(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String message=json.getString(Config.KEY_MESSAGECODE);

                pm.processReceivedMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }




    }

}
