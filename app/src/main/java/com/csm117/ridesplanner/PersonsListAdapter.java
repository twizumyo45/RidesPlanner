package com.csm117.ridesplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm117.ridesplanner.R;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.onClickListeners.OnClickPersonViewListener;


import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class PersonsListAdapter extends ArrayAdapter<Person> {

    ViewPersonsListActivity viewPersonsListActivity_;
    public PersonsListAdapter(Context context, List<Person> persons, ViewPersonsListActivity vpla) {
        super(context, 0, persons);
        viewPersonsListActivity_ = vpla;
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
        Person unsentPerson = getItem(position);

        // Lookup view for data population
        LinearLayout personsLinearLayout =
                (LinearLayout) convertView.findViewById(R.id.persons);
        personsLinearLayout.setOrientation(LinearLayout.VERTICAL);
        personsLinearLayout.setBackgroundColor(Color.TRANSPARENT);

        personsLinearLayout.removeAllViews();
        TextView personTextView = new TextView(super.getContext());
        personTextView.setText(unsentPerson.toString());
        personTextView.setTypeface(unsentPerson.getTypeface());
        personTextView.setBackgroundColor(Color.TRANSPARENT);

        OnClickPersonViewListener onClickPersonViewListener = new OnClickPersonViewListener(viewPersonsListActivity_, unsentPerson);
        personsLinearLayout.setOnClickListener(onClickPersonViewListener);
        //personTextView.setOnClickListener(onClickPersonViewListener);
        personsLinearLayout.addView(personTextView);
        unsentPerson.setPersonListView_(personsLinearLayout);

        // Return the completed view to render on screen
        return convertView;
    }
}
