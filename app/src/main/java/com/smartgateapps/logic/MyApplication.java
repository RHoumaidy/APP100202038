package com.smartgateapps.logic;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.smartgateapps.logic.activities.GPSTracker;
import com.smartgateapps.logic.model.AboutUs;
import com.smartgateapps.logic.model.FeulPrice;
import com.smartgateapps.logic.model.OurServices;
import com.smartgateapps.logic.model.PhonNums;

/**
 * Created by Raafat on 28/02/2016.
 */
public class MyApplication extends Application {

    public static RequestQueue queue;
    public static FeulPrice feulPrice;
    public static AboutUs aboutUs;
    public static AboutUs intro;
    public static PhonNums phonNums;
    public static OurServices ourServices;
    public static Context APP_CTX;
    public static GPSTracker gpsTracker;
    public static ConnectivityManager connectivityManager;
    public static String mapUrl = "http://maps.googleapis.com/maps/api/staticmap?autoscale=false&" +
            "size=600x300&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C";
    public static LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        APP_CTX = getApplicationContext();
        queue = Volley.newRequestQueue(APP_CTX);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        gpsTracker = new GPSTracker();
    }

    public static boolean isNetworkAvailable() {

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return true;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
