package com.csm117.ridesplanner.ridesplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class RideGroupAdapter extends ArrayAdapter<RideGroup> {
    public RideGroupAdapter(Context context, List<RideGroup> rideGroups) {
        super(context, 0, rideGroups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RideGroup rideGroup = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_ridegroup, parent, false);
        }
        // Lookup view for data population
        TextView driverName =
                (TextView) convertView.findViewById(R.id.driverName);
        // Populate the data into the template view using the data object
        driverName.setText(rideGroup.driver);

        String[] riderNames = new String[] {"rider1"};

        LinearLayout riders =
                (LinearLayout) convertView.findViewById(R.id.riders);
        //riders.setOrientation(LinearLayout.VERTICAL);
        for(String rider : riderNames) {
            TextView riderTextView = new TextView(super.getContext());
            riderTextView.setText(rider);
            riders.addView(riderTextView);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
