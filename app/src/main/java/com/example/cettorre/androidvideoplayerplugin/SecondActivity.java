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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    ListView lv;
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

                paused = pref.getLong("paused", 0);
                restarted = pref.getString("restarted", "0");


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
        ArrayList<Integer> dataIntentElapsedList =i.getIntegerArrayListExtra("dataIntentElapsedList");

        if(dataIntentElapsedList!=null)
        Log.i("arrayList",dataIntentElapsedList.toString());


        //textView = findViewById(R.id.tv1);
        //textView.setText(data);


        textView3 = findViewById(R.id.pausedTimes);
        //textView3.setText(dataIntentPaused);//data from intent extras
        textView3.setText(String.valueOf(paused));//data from shared preferences

        textView2 = findViewById(R.id.restartedTimes);
        //textView2.setText(dataIntentRestarted);
        textView2.setText(restarted);


        //textView4 = findViewById(R.id.tv5);
       // if(dataIntentElapsedList!=null)
       // textView4.setText(dataIntentElapsedList.toString());

        lv=findViewById(R.id.elapsedListView);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,dataIntentElapsedList);

        if(dataIntentElapsedList!=null)

            lv.setAdapter(adapter);



    }



}