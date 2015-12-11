package com.adicu.density;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter that converts data to location list view.
 */
public class LocationListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DensityData> mDensityData;

    // Maximum allowed percentage fullness.
    private static final int MAX_PERCENTAGE = 100;

    private HashMap<String, String> replacedWords;

    public LocationListAdapter(Context context, ArrayList<DensityData> locations) {
        mContext = context;
        mDensityData = locations;

        replacedWords = new HashMap<>();
        replacedWords.put("stk", "Stacks");
        replacedWords.put("301", "Reference");
    }

    public int getCount() {
        return mDensityData.size();
    }

    public Object getItem(int position) {
        return mDensityData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout itemView;
        if (convertView == null) {
            itemView = (RelativeLayout) inflater.inflate(R.layout.location_item, parent, false);
        }
        else {
            itemView = (RelativeLayout) convertView;
        }

        // Makes custom Density font.
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Lato2OFL/Lato-Medium.ttf");
        TextView locationNameView = (TextView) itemView.findViewById(R.id.locationName);
        locationNameView.setTypeface(tf);

        // Set location name.
        String name = mDensityData.get(position).getGroupName();

        // Check for words to replace.
        for (String word : replacedWords.keySet()) {
            if (name.contains(word)) {
                name = name.replace(word, replacedWords.get(word));
            }
        }

        locationNameView.setText(name);

        // Set percentage fullness text.
        int percentageFull = (int) Math.round(mDensityData.get(position).getPercentFull());

        // Handle cases where Density doesn't realize that having more than 100% is silly.
        if (percentageFull > MAX_PERCENTAGE) {
            percentageFull = MAX_PERCENTAGE;
        }

        TextView percentageFullView = (TextView) itemView.findViewById(R.id.percentageFull);
        percentageFullView.setText(Integer.toString(percentageFull) + "%");

        // Set progress bar percentage.
        ProgressBar percentageBar = (ProgressBar) itemView.findViewById(R.id.percentageBar);
        percentageBar.setProgress((int)mDensityData.get(position).getPercentFull());

        return itemView;
    }

    /**
     * Updates list view data and refreshes the view.
     * @param densityData the new location names.
     */
    public void update(ArrayList<DensityData> densityData) {
        mDensityData = densityData;
        notifyDataSetChanged();
    }

}
