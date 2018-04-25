package com.example.cettorre.androidvideoplayerplugin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button diaplayStats;
    Button sendRequest;
    Button playService;
    Button clear;
    static long paused;
    static String restarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        diaplayStats =findViewById(R.id.button);
        diaplayStats.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , SecondActivity.class);
                startActivity(i);
            }
        });
        sendRequest=findViewById(R.id.button2);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        playService=findViewById(R.id.button3);
        playService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playService();
            }
        });

        clear=findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDataFromSharedPref();

            }
        });

    }

    public void removeDataFromSharedPref(){
        Context con = null;
        try {
            con = createPackageContext("com.example.cettorre.androidvideoplayerplugin", Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (con != null) {
               SharedPreferences pref = con.getSharedPreferences(
                        ".preferences", Context.MODE_WORLD_READABLE);

                pref.edit().clear().apply();

            } else {
                Log.i("msg", "Other App Data: Context null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    URL requestUrl;

    private void makeRequest() {



        Context con = null;
        try {
            con = createPackageContext("com.example.cettorre.androidvideoplayerplugin", Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (con != null) {
                SharedPreferences pref = con.getSharedPreferences(
                        ".preferences", Context.MODE_WORLD_READABLE);
                paused = pref.getLong("paused", 99);
                restarted = pref.getString("restarted", "No Value");



            } else {
                Log.i("msg", "Other App Data: Context null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        requestUrl = NetworkUtils.buildUrl(String.valueOf(paused),restarted);
        new SendRequestTask().execute(requestUrl);
        Log.e("req_url","request URL: "+requestUrl.toString());

    }

    public class SendRequestTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String requestResults = null;
            try {
                requestResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.e("req_url","request resultL: "+requestResults.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return requestResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {

            }
        }

    public void playService() {
        Intent i = new Intent(this, HttpService.class);
        startService(i);
    }


}

