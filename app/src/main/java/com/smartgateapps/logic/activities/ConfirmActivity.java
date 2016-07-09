package com.smartgateapps.logic.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.smartgateapps.logic.MyApplication;
import com.smartgateapps.logic.R;
import com.smartgateapps.logic.model.FeulRequest;
import com.smartgateapps.logic.model.RequestType;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.List;


public class ConfirmActivity extends AppCompatActivity {

    private Location location;
    private ImageView mapImageView;
    private FeulRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        mapImageView = (ImageView) findViewById(R.id.mapImgView);
        TextView fullNameTxtV = (TextView) findViewById(R.id.fullNameTxtV);
        TextView phoneTxtV = (TextView) findViewById(R.id.phoneNumberTxtV);
        TextView emailTxtV = (TextView) findViewById(R.id.emailTxtV);
        TextView ariaTxtV = (TextView) findViewById(R.id.areaTxtV);
        TextView buildingTxtV = (TextView) findViewById(R.id.buildingTxtV);
        TextView streetTxtV = (TextView) findViewById(R.id.streetTxtV);
        TextView quantityTxtV = (TextView) findViewById(R.id.quantityTxtV);
        TextView infoTxtV = (TextView) findViewById(R.id.infoTxtV);
        TextView typeTxtV = (TextView) findViewById(R.id.typeTxtV);
        TextView notesTxtV = (TextView) findViewById(R.id.notesTxtV);
        final ProgressBar loading = (ProgressBar)findViewById(R.id.mapLoadingPB);
        final Button confirmAndSendBtn = (Button) findViewById(R.id.confirmBtn);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        request = ((FeulRequest) getIntent().getSerializableExtra("REQUST"));


        location = MyApplication.gpsTracker.getLocation();
        String url;
        if (location != null) {
            url = MyApplication.mapUrl + location.getLatitude() + "," + location.getLongitude();
            Picasso.with(this)
                    .load(url)
                    .error(android.R.drawable.stat_notify_error)
                    .into(mapImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
            request.setLat(location.getLatitude() + "");
            request.setLng(location.getLongitude() + "");

        }


        fullNameTxtV.setText(request.getName());
        phoneTxtV.setText(request.getPhone());
        emailTxtV.setText(request.getEmail());
        ariaTxtV.setText(request.getArea());
        buildingTxtV.setText(request.getBuilding());
        streetTxtV.setText(request.getStreet());
        quantityTxtV.setText(request.getQuantity());
        infoTxtV.setText(request.getInfo());
        notesTxtV.setText(request.getNotes());
        typeTxtV.setText((request.getType() == RequestType.GREEN) ? " Diesel Oil (Green)" : "Gas Oil (Red)");


        confirmAndSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = request.prepareUrl();
                confirmAndSendBtn.setEnabled(false);
                JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast toast = Toast.makeText(ConfirmActivity.this, "Your Order Request has been sent successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        ConfirmActivity.this.setResult(Activity.RESULT_OK);
                        ConfirmActivity.this.finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(ConfirmActivity.this, "Error", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        confirmAndSendBtn.setEnabled(true);
                    }
                });

                MyApplication.queue.add(request);

            }
        });


    }



    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                onBackPressed();
                return true;
        }
        return false;
    }
}
