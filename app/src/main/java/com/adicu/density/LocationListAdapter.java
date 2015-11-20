package com.adicu.density;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter that converts data to location list view.
 */
public class LocationListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DensityData> mDensityData;

    public LocationListAdapter(Context context, ArrayList<DensityData> locations) {
        mContext = context;
        mDensityData = locations;
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

        TextView locationNameView = (TextView) itemView.findViewById(R.id.locationName);
        String name = mDensityData.get(position).getGroupName();
        locationNameView.setText(name);
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
