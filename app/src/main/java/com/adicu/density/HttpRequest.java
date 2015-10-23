package com.adicu.density;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Used for making HTTP requests to http://density.adicu.com.
 */
public class HttpRequest extends AsyncTask<Object, Void, Boolean>{
    // Density base url.
    private static final String BASE_URL = "http://density.adicu.com";
    // API key
    private static final String API_KEY = "6WMDXJOM7WDP6NBZVOATYH9PUGIYA1I8";

    /**
     * Represents time intervals from which you can get Density data.
     *
     * LATEST gets the most recent data from Density.
     *
     * WINDOW gets the data within a time interval. You must specify a start and end date if using
     * WINDOW. Use the following date format:
     * The date should be given in Eastern Standard Time (EST). Additionally, the date must be
     * in ISO 8601 format: YYYY-MM-DD or YYYY-MM-DDThh:mm
     */
    public enum TimeInterval {
        LATEST, WINDOW
    }

    /**
     * Represents the buildings you can get information from.
     */
    public enum Building {

    }

    public enum Group {

    }

    /**
     * Creates an HTTP request with the desired parameters.
     * @param params all the params to include in the request (see RequestInfo).
     */
    public HttpRequest(String... params) {

    }

    /**
     * Executes the request.
     * @param params
     * @return
     */
    protected Boolean doInBackground(Object... params) {
        Boolean result = false;

        try {
            String foo = "http://density.adicu.com/window/2014-10-10T08:00/2014-10-10T21:30/building/" +
                    "75?auth_token=" + API_KEY;
            URL url = new URL(foo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            // Copy all input stream into StringBuilder.
            String inputString;
            while((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }
            Log.d("HttpRequest", sb.toString());
            result = true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
