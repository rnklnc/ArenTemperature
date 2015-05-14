package com.example.akolanci.arentemperature;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class MainActivity extends ActionBarActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        if (android.os.Build.VERSION.SDK_INT > 9) { // for solving
            // android.os.networkonmainthreadexception
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LocationProvider(this);

    }
}
