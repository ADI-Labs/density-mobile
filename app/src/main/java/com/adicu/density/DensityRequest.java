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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.adicu.density.RequestParameter.*;

/**
 * Creates an HTTP request Density's API found at <a href="http://density.adicu.com">.
 *
 * To make a Density API request, create an instance of DensityRequest with the desired parameters
 * (see the constructors for available URL parameter templates). To execute the request, call the
 * execute() method of AsyncTask.
 */
public class DensityRequest extends AsyncTask<Object, Void, Boolean>{
    // Density base url.
    private static final String BASE_URL = "http://density.adicu.com";
    // API key
    private static final String API_KEY = "6WMDXJOM7WDP6NBZVOATYH9PUGIYA1I8";

    // Choices: latest, window, or day.
    private TimeInterval mTimeInterval;
    // Start and end time for window time interval.
    private Date mStartTime;
    private Date mEndTime;
    // Day for date time interval
    private Date mDay;

    // Choices: building or group.
    private Grouping mGrouping;
    // Group to get info from.
    private Group mGroup;
    // Building to get info from.
    private Building mBuilding;

    /**
     * Creates an HTTP request with the desired parameters.
     * @param timeInterval the time interval to fetch data from.
     */
    public DensityRequest(TimeInterval timeInterval, Grouping grouping) {
        mTimeInterval = timeInterval;
        mGrouping = grouping;
    }

    /**
     * Executes the request.
     * @param params unused.
     * @return true if the information was successfully retrieved; false otherwise.
     */
    protected Boolean doInBackground(Object... params) {
        Boolean isFetchSuccess = false;

        try {
            String urlString = toString();
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            // Copy all input stream into StringBuilder.
            String inputString;
            while((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }
            Log.i("DensityRequest", sb.toString());
            isFetchSuccess = true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return isFetchSuccess;
    }

    // Getters and setters for request parameters.

    /**
     * Returns the start time or null if start time has not been set.
     * @return the start time.
     */
    public Date getStartTime() {
        return mStartTime;
    }

    /**
     * Sets the start time.
     * @param startTime the start time.
     */
    public void setStartTime(Date startTime) {
        mStartTime = startTime;
    }

    /**
     * Returns the end time or null if end time has not been set.
     * @return the end time.
     */
    public Date getEndTime() {
        return mEndTime;
    }

    /**
     * Sets the end time.
     * @param endTime the end time.
     */
    public void setEndTime(Date endTime) {
        mEndTime = endTime;
    }

    /**
     * Returns the day or null if day has not been set.
     * @return the day.
     */
    public Date getDay() {
        return mDay;
    }

    /**
     * Sets the day.
     * @param day the day.
     */
    public void setDay(Date day) {
        mDay = day;
    }

    /**
     * Returns the grouping.
     * @return the grouping.
     */
    public Grouping getGrouping() {
        return mGrouping;
    }

    /**
     * Sets the grouping.
     * @param grouping the grouping.
     */
    public void setGrouping(Grouping grouping) {
        mGrouping = grouping;
    }

    /**
     * Returns the group or null if group has not been set.
     * @return the group.
     */
    public Group getGroup() {
        return mGroup;
    }

    /**
     * Sets the group.
     * @param group the group.
     */
    public void setGroup(Group group) {
        mGroup = group;
    }

    /**
     * Returns the building or null if the building has not been set.
     * @return the building.
     */
    public Building getBuilding() {
        return mBuilding;
    }

    /**
     * Sets the building.
     * @param building the building.
     */
    public void setBuilding(Building building) {
        mBuilding = building;
    }

    /**
     * Returns the time interval.
     * @return the time interval.
     */
    public TimeInterval getTimeInterval() {
        return mTimeInterval;
    }

    /**
     * Sets the time interval.
     * @param timeInterval the time interval.
     */
    public void setTimeInterval(TimeInterval timeInterval) {
        mTimeInterval = timeInterval;
    }

    /**
     * Returns the full URL for the request.
     * @return the full URL for the request.
     */
    public String toString() {
        // Start with base URL.
        StringBuilder sb = new StringBuilder(BASE_URL);

        // Append time interval.
        sb.append(mTimeInterval);

        // Append additional time interval arguments (required for window/day).
        if (mTimeInterval == TimeInterval.WINDOW) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            sb.append("/" + dateFormat.format(mStartTime));
            sb.append("/" + dateFormat.format(mEndTime));
        }
        else if (mTimeInterval == TimeInterval.DAY) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            sb.append("/" + dateFormat.format(mDay));
        }

        // Append grouping
        sb.append(mGrouping);

        // Append additional grouping arguments (required).
        if (mGrouping == Grouping.GROUP) {
            sb.append(mGroup);
        }
        else if (mGrouping == Grouping.BUILDING) {
            sb.append(mBuilding);
        }

        // Add API key info.
        sb.append("?auth_token=" + API_KEY);

        return sb.toString();
    }
}
