package com.example.cettorre.androidvideoplayerplugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class HttpService extends Service {
    static String data;
    static long paused;
    static String restarted;
    static String elapsed;
    String pausedS;

    public HttpService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    SharedPreferences pref;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Context con = null;
        try {
            con = createPackageContext("com.example.cettorre.androidvideoplayerplugin", Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (con != null) {
                 pref = con.getSharedPreferences(
                        ".preferences", Context.MODE_WORLD_READABLE);

                data = pref.getString("shared_token", "No Value");
                paused = pref.getLong("paused", 99);
                restarted = pref.getString("restarted", "No Value");
                elapsed = pref.getString("elapsed", "No Value");
                 pausedS=String.valueOf(paused);


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



        play();
        makeRequest();
        return START_NOT_STICKY;

    }

    //falta implementar stop() y stopMusic();



    private void play(){
        Log.i("v_service","Service_play");
    }

    private void stop(){
        Log.i("v_service","Service_stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(),"Service_destroy");
        stopSelf();
    }

    URL requestUrl;

    private void makeRequest() {

        requestUrl = NetworkUtils.buildUrl(pausedS,restarted);
        new HttpService.SendRequestTask().execute(requestUrl);
        Intent i = new Intent();
        String paused=i.getStringExtra("dataIntentPaused");
        Log.e("req_url","request URL: "+requestUrl.toString());
        Log.e("req_url","value"+paused);
        Toast t=Toast.makeText(this,"A request has been sent to the following Http address: \n"+requestUrl.toString(),Toast.LENGTH_LONG);
        t.show();

    }

    public class SendRequestTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String requestResults = null;
            try {
                requestResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.e("req_url2","request resultL: "+requestResults.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return requestResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            Log.e("req_url","request resultL: ");


        }
    }
}
