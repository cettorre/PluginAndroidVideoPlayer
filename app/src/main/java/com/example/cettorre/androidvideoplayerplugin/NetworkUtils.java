package com.example.cettorre.androidvideoplayerplugin;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL =
            "https://url.fakeurl.com/";


    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("param_1", "value_1")
                .appendQueryParameter("param_2", "value_2")
                .appendQueryParameter("param_2", "value_3")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("urlcatch: " ,url.toString());



        return url;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}