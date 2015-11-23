package com.csm117.ridesplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm117.ridesplanner.R;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class PersonsListAdapter extends ArrayAdapter<RideGroup> {
    public PersonsListAdapter(Context context, List<RideGroup> persons) {
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_persons_list_item, parent, false);
            convertView.setPadding(5, 20, 5, 20);
        }

        // Get the data item for this position
        RideGroup unsentRideGroup = getItem(position);

        // Lookup view for data population
        LinearLayout personsLinearLayout =
                (LinearLayout) convertView.findViewById(R.id.persons);
        personsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        personsLinearLayout.removeAllViews();
        TextView driverTextView = new TextView(super.getContext());
        driverTextView.setText(unsentRideGroup.driver.toString());
        driverTextView.setTypeface(unsentRideGroup.driver.getTypeface());
        personsLinearLayout.addView(driverTextView);
        unsentRideGroup.driver.setPersonListView_(driverTextView);

        for(Person p: unsentRideGroup.riders){
            TextView personTextView = new TextView(super.getContext());
            personTextView.setText(p.toString());
            personTextView.setTypeface(p.getTypeface());
            personsLinearLayout.addView(personTextView);
            p.setPersonListView_(personTextView);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
