package com.csm117.ridesplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Rider;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.Sheet;
import com.csm117.ridesplanner.onClickListeners.AddCarFabListener;
import com.csm117.ridesplanner.onClickListeners.MoveRiderFabListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPersonsListActivity extends ViewNavigation{
    List<RideGroup> unsentRidesGroup_ = Sheet.getUnsentRideGroups();
    ArrayList<Person> selectedPersons_ = new ArrayList<Person>();
    private String m_Text = "";
    public static ArrayAdapter<Person> adapter_;
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
                                        if(!userInput.getText().toString().isEmpty()){
                                            if (riderRadio.isChecked()) {
                                                unsentPersons_.add(new Rider(userInput.getText().toString()));
                                            } else {
                                                unsentPersons_.add(new Driver(userInput.getText().toString()));
                                            }
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

        FloatingActionButton fabAddCar = (FloatingActionButton) findViewById(R.id.fabAddCar);
        fabAddCar.setOnClickListener(new AddCarFabListener(this));

        //TODO: link with real sheets
        ListView listview = (ListView) findViewById(R.id.listView);
/*
        adapter_ = new PersonsListAdapter(this, unsentPersons_, this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Person person = (Person) parent.getItemAtPosition(position);
                if (!selectedPersons_.contains(person)) {
                    view.setBackgroundColor(0x6F00FF80);
                    selectedPersons_.add(person);
                    Log.d("listener", "adding: " + person.toString());
                } else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                    selectedPersons_.remove(person);
                    Log.d("listener", "removing: " + person.toString());
                }
                updateButtonVisibility();
                return;
            }
        });
        listview.setAdapter(adapter_);

    }
=======*/
        List<Person> unsentPersons = new ArrayList<Person>();
        for (RideGroup rg : unsentRidesGroup_) {
            unsentPersons.add(rg.driver);
            for (Person rider : rg.riders)
                unsentPersons.add(rider);
        }
        Collections.sort(unsentPersons);

        ArrayAdapter<Person> adapter = new PersonsListAdapter(context, unsentPersons, this);
        listview.setAdapter(adapter);
    }

    public void updateButtonVisibility(){
        //get floating action buttons
        FloatingActionButton fabAddCar = (FloatingActionButton) findViewById(R.id.fabAddCar);

        //set all as invisible
        fabAddCar.setVisibility(View.INVISIBLE);
        //set the correct fab as visible if necessary
        if (RideGroup.checkSingleDriver(selectedPersons_)){ //there is only one person selected and it's a rider
            fabAddCar.setVisibility(View.VISIBLE);
        }
    }
}
