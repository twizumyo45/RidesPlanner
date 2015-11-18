package com.csm117.ridesplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.onClickListeners.OnClickPersonListener;
import com.csm117.ridesplanner.R;
import com.csm117.ridesplanner.entities.Person;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */

public class RideGroupAdapter extends ArrayAdapter<RideGroup>{

    ViewRidesActivity viewRidesActivity_;

    public RideGroupAdapter(Context context, List<RideGroup> rideGroups, ViewRidesActivity vra) {
        super(context, 0, rideGroups);
        viewRidesActivity_ = vra;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RideGroup rideGroup = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_rides_ridegroup_item, parent, false);
            convertView.setPadding(5, 20, 5, 20);
        }

        // Lookup view for data population
        TextView driverTextView =
                (TextView) convertView.findViewById(R.id.driverName);

        // Populate the data into the template view using the data object
        driverTextView.setText(rideGroup.driver.toString());
        driverTextView.setOnClickListener(new OnClickPersonListener(viewRidesActivity_, rideGroup.driver));
        driverTextView.setTypeface(rideGroup.driver.getTypeface());
        //give driver a reference to it's textview
        rideGroup.driver.setRideGroupView(driverTextView);

        // further populate the rest of the TextViews in this LinearLayout
        List<Person> riderNames = rideGroup.riders;
        LinearLayout riders =
                (LinearLayout) convertView.findViewById(R.id.riders);
        riders.setOrientation(LinearLayout.VERTICAL);
        riders.removeAllViews();

        for(Person rider : riderNames) {
            TextView riderTextView = new TextView(super.getContext());
            riderTextView.setSelected(true);
            OnClickPersonListener onClickPersonListener = new OnClickPersonListener(viewRidesActivity_, rider);
            riderTextView.setOnClickListener(onClickPersonListener);
            riderTextView.setText(rider.toString());
            riders.addView(riderTextView);
            rider.setRideGroupView(riderTextView);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
