package com.csm117.ridesplanner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm117.ridesplanner.R;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.onClickListeners.OnClickPersonsListListener;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class PersonsListAdapter extends ArrayAdapter<Person> {
    ViewPersonsListActivity viewPersonsListActivity_;
    public PersonsListAdapter(Context context, List<Person> persons, ViewPersonsListActivity vra) {
        super(context, 0, persons);
        viewPersonsListActivity_ = vra;
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
        final Person person = getItem(position);

        // Lookup view for data population
//        LinearLayout personsLinearLayout =
//                (LinearLayout) convertView.findViewById(R.id.persons);
//        personsLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        personsLinearLayout.removeAllViews();

        Log.d("person", person.toString());
        TextView personTextView =(TextView) convertView.findViewById(R.id.persons);
//        personTextView.setOnClickListener(new OnClickPersonsListListener(viewPersonsListActivity_, person));
        personTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "click");
                if (!viewPersonsListActivity_.selectedPersons_.contains(person)) {
                    v.setBackgroundColor(0x6F00FF80);
                    viewPersonsListActivity_.selectedPersons_.add(person);
                    Log.d("listener", "adding: " + person.toString());
                } else{
                    v.setBackgroundColor(Color.TRANSPARENT);
                    viewPersonsListActivity_.selectedPersons_.remove(person);
                    Log.d("listener", "removing: " + person.toString());
                }
                viewPersonsListActivity_.updateButtonVisibility();
                return;
            }
        });
        personTextView.setText(person.toString());
        personTextView.setTypeface(person.getTypeface());
//        personTextView.setClickable(true);
//        personsLinearLayout.addView(personTextView);

        // Return the completed view to render on screen
        return convertView;
    }
}
