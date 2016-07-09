package com.smartgateapps.logic.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.smartgateapps.logic.MyApplication;
import com.smartgateapps.logic.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        WebView webView = (WebView) findViewById(R.id.aboutUsWebV);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        boolean isAboutus = getIntent().getBooleanExtra("IS_ABOUT_US", true);
        try {
            if (isAboutus) {
                webView.loadData(MyApplication.aboutUs.getReturnData().getHTML(), "text/html", "utf-8");
                setTitle(MyApplication.aboutUs.getReturnData().getTitle());
            } else {
                webView.loadData(MyApplication.ourServices.getReturnData().getHTML(), "text/html", "utf-8");
                setTitle(MyApplication.ourServices.getReturnData().getTitle());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
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
