package com.example.cettorre.androidvideoplayerplugin;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class HttpService extends Service {
    public HttpService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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

        requestUrl = NetworkUtils.buildUrl();
        new HttpService.SendRequestTask().execute(requestUrl);
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
}
