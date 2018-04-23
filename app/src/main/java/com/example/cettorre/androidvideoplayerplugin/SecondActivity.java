package com.example.cettorre.androidvideoplayerplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    String data1;
    Context con;
    static String data;
    static long paused;
    static String restarted;
    static String elapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



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

                data = pref.getString("shared_token", "No Value");
                paused = pref.getLong("paused", 99);
                restarted = pref.getString("restarted", "No Value");
                elapsed = pref.getString("elapsed", "No Value");


                Log.i("msg", "Other App Data: " + data);
                Log.i("msg", "Other App Data: " + paused);
                Log.i("msg", "Other App Data: " + restarted);
                Log.i("msg", "Other App Data: " + elapsed);
            } else {
                Log.i("msg", "Other App Data: Context null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent i = getIntent();
        String dataIntentPaused = i.getStringExtra("dataIntentPaused");
        String dataIntentRestarted = i.getStringExtra("dataIntentRestarted");
        String dataIntentElapsed = i.getStringExtra("dataIntentElapsed");


        textView = findViewById(R.id.tv1);
        textView.setText(data);

        textView1 = findViewById(R.id.tv2);
        textView1.setText(String.valueOf(dataIntentElapsed));

        textView2 = findViewById(R.id.tv3);
        textView2.setText(dataIntentRestarted);

        textView3 = findViewById(R.id.tv4);
        textView3.setText(dataIntentPaused);


    }



}