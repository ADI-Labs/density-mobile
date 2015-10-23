package com.adicu.density;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kim on 10/23/2015.
 */
public class HttpRequest extends AsyncTask<Object, Void, Boolean>{
    private static final String API_KEY = "6WMDXJOM7WDP6NBZVOATYH9PUGIYA1I8";

    protected Boolean doInBackground(Object... params) {
        Boolean result = false;
//        Log.d("HttpRequest", );
        try {
            String foo = "http://density.adicu.com/window/2014-10-10T08:00/2014-10-10T21:30/building/" +
                    "75?auth_token=" + API_KEY;

//            String foo = "https://google.com";
            URL url = new URL(foo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inStream = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            String inputString;
            while((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }
            Log.d("HttpRequest", sb.toString());
            result = true;
        }
        catch (MalformedURLException e) {
            System.out.println("ERROR 1");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("ERROR 2");
            e.printStackTrace();
        }
        return result;
    }
}
