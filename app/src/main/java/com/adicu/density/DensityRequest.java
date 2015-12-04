package com.adicu.density;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    // ISO 8601 date format required for API calls
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

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

    // JSONObject representing the data from the most recent request made.
    private JSONObject mRequestJSON;

    // Context to return data to.
    private LocationListAdapter mAdapter;

    /**
     * Creates an HTTP request with default time interval set to latest and no grouping.
     */
    public DensityRequest(LocationListAdapter adapter) {
        mTimeInterval = TimeInterval.LATEST;
        mAdapter = adapter;
    }

    /**
     * Creates an HTTP request with the desired parameters.
     * @param timeInterval the time interval to fetch data from.
     */
    public DensityRequest(LocationListAdapter adapter, TimeInterval timeInterval, Grouping grouping) {
        mTimeInterval = timeInterval;
        mGrouping = grouping;
        mAdapter = adapter;
    }

    /**
     * Executes the request.
     * @param params unused.
     * @return true if the information was successfully retrieved; false otherwise.
     */
    @Override
    protected Boolean doInBackground(Object... params) {
        Boolean isFetchSuccess = false;

        try {
            String urlString = toString();
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set timeout
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            InputStream inStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            // Copy all input stream into StringBuilder.
            String inputString;
            while((inputString = br.readLine()) != null) {
                sb.append(inputString);
            }
            mRequestJSON = new JSONObject(sb.toString());

            isFetchSuccess = true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return isFetchSuccess;
    }

    /**
     * When the request has finished execution, extracts the JSON data from the request,
     * encapsulates it in an ArrayList of DensityData, and updates the information in the adapter
     * and list view.
     * @param isSuccessful true if request was successfully executed; false otherwise.
     */
    @Override
    protected void onPostExecute(Boolean isSuccessful) {
        super.onPostExecute(isSuccessful);

        if (!isSuccessful) {
            Log.i("DensityRequest", "Did not fetch successfully.");
        }

        // Make temporary ArrayList of data (just location names for now).
        ArrayList<DensityData> densityData = new ArrayList<>();
        try {
            JSONArray dataArray = mRequestJSON.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject data = dataArray.getJSONObject(i);
                int clientCount = data.getInt("client_count");
                String dumpTime = data.getString("dump_time");
                int groupId = data.getInt("group_id");
                String groupName = data.getString("group_name");
                int parentId = data.getInt("parent_id");
                String parentName = data.getString("parent_name");
                double percentFull = data.getDouble("percent_full");
                DensityData d = new DensityData(clientCount, dumpTime, groupId, groupName, parentId,
                        parentName, percentFull);
                densityData.add(d);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Update the data in the list view.
        mAdapter.update(densityData);
    }

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
    @Override
    public String toString() {
        // Start with base URL.
        StringBuilder sb = new StringBuilder(BASE_URL);

        // Append time interval.
        sb.append(mTimeInterval);

        // Append additional time interval arguments (required for window/day).
        if (mTimeInterval == TimeInterval.WINDOW) {
            sb.append("/" + dateFormatter.format(mStartTime));
            sb.append("/" + dateFormatter.format(mEndTime));
        }
        else if (mTimeInterval == TimeInterval.DAY) {
            sb.append("/" + dateFormatter.format(mDay));
        }

        if (mGrouping != null) {
            // Append grouping
            sb.append(mGrouping);

            // Append additional grouping arguments (required).
            if (mGrouping == Grouping.GROUP) {
                sb.append(mGroup);
            }
            else if (mGrouping == Grouping.BUILDING) {
                sb.append(mBuilding);
            }
        }

        // Add API key info.
        sb.append("?auth_token=" + API_KEY);

        return sb.toString();
    }
}
