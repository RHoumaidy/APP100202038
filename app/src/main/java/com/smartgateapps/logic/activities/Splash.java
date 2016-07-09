package com.smartgateapps.logic.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.smartgateapps.logic.MyApplication;
import com.smartgateapps.logic.R;
import com.smartgateapps.logic.model.AboutUs;
import com.smartgateapps.logic.model.FeulPrice;
import com.smartgateapps.logic.model.OurServices;
import com.smartgateapps.logic.model.PhonNums;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {

    private JsonArrayRequest priceRequest;
    private JsonArrayRequest aboutUsRequest;
    private JsonArrayRequest introRequest;
    private JsonArrayRequest phoneNumsRequest;
    private JsonArrayRequest ourServicesRequest;
    private RubberLoaderView rubberLoaderView;
    private LinearLayout splashLinearLayout;
    private TextView loadingTxtV;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private int done = 0;
    private final int MAX_DONE = 5;
    private final int SPLASH_TIME = 3000;
    private int idx = 0;
    private String[] waitStr = new String[]{"Loading","Loading.","Loading..","Loading..."};


    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(i);
                Splash.this.finish();
            }
        };


        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null)
            actionBar.hide();

        mVisible = true;
        rubberLoaderView = (RubberLoaderView) findViewById(R.id.loader);
        splashLinearLayout = (LinearLayout) findViewById(R.id.splashLinearLayout);
        loadingTxtV = (TextView) findViewById(R.id.loadingTxtV);

        // Set up the user interaction to manually show or hide the system UI.
        rubberLoaderView.startLoading();

        priceRequest = new JsonArrayRequest(FeulPrice.GET_FUEL_PRICE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            MyApplication.feulPrice = new FeulPrice();
                            JSONObject jsonObject = response.getJSONObject(0);
                            MyApplication.feulPrice.setHasError(jsonObject.getBoolean("HasError"));
                            MyApplication.feulPrice.setErrorMessage(jsonObject.getString("ErrorMessage"));
                            if (MyApplication.feulPrice.isHasError()) {
                                Toast.makeText(MyApplication.APP_CTX, "Error" + MyApplication.feulPrice.getErrorMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                JSONArray ReturnData = jsonObject.getJSONArray("ReturnData");
                                List<FeulPrice.FeulPriceReturnd> returndsList = new ArrayList<>();
                                for (int i = 0; i < ReturnData.length(); ++i) {
                                    JSONObject fuelPriceObject = ReturnData.getJSONObject(i);
                                    FeulPrice.FeulPriceReturnd fuelPriceItem = new FeulPrice.FeulPriceReturnd(
                                            fuelPriceObject.getString("Title"),
                                            fuelPriceObject.getString("Price")
                                    );
                                    returndsList.add(fuelPriceItem);
                                }
                                MyApplication.feulPrice.setReturnData(returndsList);
                            }

                            if(++done == MAX_DONE)
                                timer.schedule(timerTask,SPLASH_TIME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(splashLinearLayout, "Sorry Error Fetching Some Data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                featchData(priceRequest);
                                featchData(aboutUsRequest);
                                featchData(introRequest);
                                featchData(phoneNumsRequest);
                                featchData(ourServicesRequest);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();

            }
        });
        priceRequest.setTag("getPrice");
        priceRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        aboutUsRequest = new JsonArrayRequest(AboutUs.SERVICE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            MyApplication.aboutUs = new AboutUs();
                            JSONObject returnedObject = response.getJSONObject(0);
                            Gson gson = new Gson();
                            MyApplication.aboutUs = gson.fromJson(returnedObject.toString(),AboutUs.class);

                            if(++done == MAX_DONE)
                                timer.schedule(timerTask,SPLASH_TIME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(splashLinearLayout, "Sorry Error Fetching Some Data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                featchData(priceRequest);
                                featchData(aboutUsRequest);
                                featchData(introRequest);
                                featchData(phoneNumsRequest);
                                featchData(ourServicesRequest);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();

            }
        });

        introRequest = new JsonArrayRequest("http://www.logic.com.lb/ajax/AppHandler/get_form_intro ",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            MyApplication.aboutUs = new AboutUs();
                            JSONObject returnedObject = response.getJSONObject(0);
                            Gson gson = new Gson();
                            MyApplication.intro = gson.fromJson(returnedObject.toString(),AboutUs.class);

                            if(++done == MAX_DONE)
                                timer.schedule(timerTask,SPLASH_TIME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(splashLinearLayout, "Sorry Error Fetching Some Data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                featchData(priceRequest);
                                featchData(aboutUsRequest);
                                featchData(introRequest);
                                featchData(phoneNumsRequest);
                                featchData(ourServicesRequest);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();

            }
        });

        phoneNumsRequest = new JsonArrayRequest(PhonNums.GET_PHONE_NUMS_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            MyApplication.phonNums = new PhonNums();
                            JSONObject returnedObject = response.getJSONObject(0);
                            Gson gson = new Gson();
                            MyApplication.phonNums = gson.fromJson(returnedObject.toString(),PhonNums.class);

                            if(++done == MAX_DONE)
                                timer.schedule(timerTask,SPLASH_TIME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(splashLinearLayout, "Sorry Error Fetching Some Data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                featchData(priceRequest);
                                featchData(aboutUsRequest);
                                featchData(introRequest);
                                featchData(phoneNumsRequest);
                                featchData(ourServicesRequest);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });

        ourServicesRequest = new JsonArrayRequest(OurServices.GET_OUR_SERVICES_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            MyApplication.ourServices = new OurServices();
                            JSONObject returnedObject = response.getJSONObject(0);
                            Gson gson = new Gson();
                            MyApplication.ourServices = gson.fromJson(returnedObject.toString(),OurServices.class);

                            if(++done == MAX_DONE)
                                timer.schedule(timerTask,SPLASH_TIME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(splashLinearLayout, "Sorry Error Fetching Some Data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                featchData(priceRequest);
                                featchData(aboutUsRequest);
                                featchData(introRequest);
                                featchData(phoneNumsRequest);
                                featchData(ourServicesRequest);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });


//        priceRequest.setTag("aboutUsRequest");
//        priceRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        aboutUsRequest.setTag("aboutUsRequest");
//        aboutUsRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        introRequest.setTag("introRequest");
//        introRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        phoneNumsRequest.setTag("phoneNumsRequest");
//        phoneNumsRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        ourServicesRequest.setTag("ourServicesRequest");
//        ourServicesRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        if(MyApplication.isNetworkAvailable()) {
            featchData(priceRequest);
            featchData(aboutUsRequest);
            featchData(introRequest);
            featchData(phoneNumsRequest);
            featchData(ourServicesRequest);
        }else{
            Snackbar snackbar = Snackbar.make(splashLinearLayout, "No Internet Connection !", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            featchData(priceRequest);
                            featchData(aboutUsRequest);
                            featchData(introRequest);
                            featchData(phoneNumsRequest);
                            featchData(ourServicesRequest);
                        }
                    });
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

    }

    private void featchData(final Request request) {
            MyApplication.queue.add(request);


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingTxtV.setText(waitStr[idx++]);
                        idx%=4;
                    }
                });

            }
        },0,600);

    }

    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();

    }
}
