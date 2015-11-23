package com.csm117.ridesplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Rider;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.Sheet;

import java.util.List;

public class ViewPersonsListActivity extends ViewNavigation{
    List<RideGroup> unsentRidesGroup_ = Sheet.getUnsentRideGroups();
    private String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_persons_list_navigation_activity);
        super.setUpNav();

        final Context context = this;
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alert dialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                final RadioButton riderRadio = (RadioButton) promptsView.findViewById(R.id.radio_riders);
                final RadioButton driverRadio = (RadioButton) promptsView.findViewById(R.id.radio_drivers);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //m_Text = userInput.getText());
                                        if (riderRadio.isChecked()) {
                                            unsentPersons_.add(new Rider(userInput.getText().toString()));
                                        } else {
                                            unsentPersons_.add(new Driver(userInput.getText().toString()));
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

        });*/

        //TODO: link with real sheets
        ListView listview = (ListView) findViewById(R.id.listView);

        ArrayAdapter<RideGroup> adapter = new PersonsListAdapter(this, unsentRidesGroup_);
        listview.setAdapter(adapter);

    }
}
