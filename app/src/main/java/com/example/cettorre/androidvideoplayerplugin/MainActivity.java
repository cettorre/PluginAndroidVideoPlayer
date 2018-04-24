package com.example.cettorre.androidvideoplayerplugin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button diaplayStats;
    Button sendRequest;
    Button playService;


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




    }

    URL requestUrl;

    private void makeRequest() {

        requestUrl = NetworkUtils.buildUrl();
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

