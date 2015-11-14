package com.csm117.ridesplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm117.ridesplanner.ridesplanner.R;
import com.csm117.ridesplanner.entities.Person;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class PersonsListAdapter extends ArrayAdapter<Person> {
    public PersonsListAdapter(Context context, List<Person> persons) {
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
        Person person = getItem(position);

        // Lookup view for data population
        LinearLayout personsLinearLayout =
                (LinearLayout) convertView.findViewById(R.id.persons);
        personsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        personsLinearLayout.removeAllViews();
        TextView personTextView = new TextView(super.getContext());
        personTextView.setText(person.toString());
        personTextView.setTypeface(person.getTypeface());
        personsLinearLayout.addView(personTextView);

        // Return the completed view to render on screen
        return convertView;
    }
}
