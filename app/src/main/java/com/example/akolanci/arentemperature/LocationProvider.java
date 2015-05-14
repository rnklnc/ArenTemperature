package com.example.akolanci.arentemperature;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;

public class LocationProvider implements LocationListener,ConnectionCallbacks,OnConnectionFailedListener{
    private static String longitude="",latitude="";
    LocationClient mLocationClient;
    String provider;
    Activity activity;
    public static Timer timer;
    public static Handler handler;
    String username;

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(1000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    public LocationProvider(Activity activity) {
        this.activity = activity;
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }

    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    activity,
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
        Location location = mLocationClient.getLastLocation();
        if (location != null) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            setLongitude(longitude);
            setLatitude(latitude);
        }
        startTimer();
    }

    private void startTimer(){
        if(timer!=null){
            timer.cancel();
            timer = new Timer();
        }else{
            timer = new Timer();
        }
        handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            new PrintJsonData(activity);
                        }
                        catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {}
    @Override
    public void onDisconnected() {}
    @Override
    public void onLocationChanged(Location location) {
        longitude = String.valueOf(location.getLongitude());
        latitude = String.valueOf(location.getLatitude());
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(String longitude) {
        LocationProvider.longitude = longitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        LocationProvider.latitude = latitude;
    }



}