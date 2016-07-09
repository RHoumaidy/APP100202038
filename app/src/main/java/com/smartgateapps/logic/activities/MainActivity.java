package com.smartgateapps.logic.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.smartgateapps.logic.MyApplication;
import com.smartgateapps.logic.R;
import com.smartgateapps.logic.model.FeulPrice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DigitTextView priceTitleTxtV;
    private DigitTextView priceValueTxtV;
    private FlipTextView callUsButton;
    private Timer timer = new Timer(true);
    private TimerTask timerTask1;
    private TimerTask timerTask2;
    private int idx = 0;
    private int idx2 = 0;
    private JsonArrayRequest request;
    private String viewedNumber;

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateFeulValues();
                    }
                });
            }
        };

        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updatePhoneNumbers();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask1, 2, 4000);
        timer.scheduleAtFixedRate(timerTask2, 2, 6000);

    }

    @Override
    protected void onStop() {
        timer.cancel();
        timerTask1.cancel();
        timerTask2.cancel();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        requestLocationPermission();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/digital-7.ttf");
        priceValueTxtV = (DigitTextView) findViewById(R.id.digitalTextV);
        priceTitleTxtV = (DigitTextView) findViewById(R.id.priceTitleTxtV);
        callUsButton = (FlipTextView) findViewById(R.id.callUsButton);





        request = new JsonArrayRequest(FeulPrice.GET_FUEL_PRICE_URL,
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApplication.APP_CTX, "error ", Toast.LENGTH_LONG).show();

            }
        });
        request.setTag("getPrice");
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void updateFeulValues() {
        String fuelTitleTxt = MyApplication.feulPrice.getReturnData().get(idx).getTitle();
        String fuelPriceTxt = MyApplication.feulPrice.getReturnData().get(idx).getPrice();
        idx++;
        if (idx >= MyApplication.feulPrice.getReturnData().size()) {
            idx = 0;
            MyApplication.queue.add(request);
        }

        priceTitleTxtV.setValue(fuelTitleTxt);
        priceValueTxtV.setValue(Integer.parseInt(fuelPriceTxt));
    }

    public void updatePhoneNumbers() {
        String landNumber = MyApplication.phonNums.getReturnData().getLandLine();
        String mobileNumber = MyApplication.phonNums.getReturnData().getMobile();
        if (idx2 == 0) {
            callUsButton.setValue(landNumber);
            viewedNumber = landNumber;
        } else {
            callUsButton.setValue(mobileNumber);
            viewedNumber = mobileNumber;
        }
        idx2++;
        idx2 %= 2;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.aboutUsButton:
                intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
            case R.id.sendOrderButton:
                intent = new Intent(this, SendRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.ourServicesButton:
                intent = new Intent(this, AboutUs.class);
                intent.putExtra("IS_ABOUT_US", false);
                startActivity(intent);
                break;
            case R.id.callUsButton:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + viewedNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

        }

    }


    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
        }
    }
}
