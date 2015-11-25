package com.csm117.ridesplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.Rider;
import com.csm117.ridesplanner.entities.Sheet;
import com.csm117.ridesplanner.onClickListeners.SendCarFromListFabListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class ViewPersonsListActivity extends ViewNavigation{

    List<RideGroup> unsentRidesGroup_;
    List<RideGroup> sentRidesGroup_;
    List<Person> unsentPersons_;
    public static ArrayAdapter<Person> adapter_;
    private String m_Text = "";
    public ArrayList<Person> selectedPersons_ = new ArrayList<Person>();
    public static ArrayAdapter<RideGroup> rideGroupAdapter_;
    public static ArrayAdapter<Person> personsListAdapter_;

    public void addToUnsent(Rider p){
        for(RideGroup r : unsentRidesGroup_){
            r.add(p);
            return;
        }

        ArrayList<Person> ridersList = new ArrayList<Person>();
        ridersList.add(p);
                //no
        // current
        // rideGroups
        unsentRidesGroup_.add(new RideGroup(new Driver(""), ridersList));
    }

    public void addDriverToUnsent(Driver d){
        ArrayList<Person> ridersList = new ArrayList<Person>();
        unsentRidesGroup_.add(new RideGroup(d, ridersList));
    }

    public void refresh(){
        unsentPersons_.clear();
        for(RideGroup rg: unsentRidesGroup_){
            unsentPersons_.add(rg.driver);
            for(Person rider: rg.riders)
                unsentPersons_.add(rider);
        }
        Collections.sort(unsentPersons_);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedPersons_ = new ArrayList<Person>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_persons_list_navigation_activity);
        super.setUpNav();
        Sheet.getDataFromOnlineSheet();
        unsentRidesGroup_ = Sheet.getUnsentRideGroups();
        sentRidesGroup_ = Sheet.getSentRideGroups();
        final Context context = this;

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
                                            addToUnsent(new Rider(userInput.getText().toString()));
                                        } else {
                                            addDriverToUnsent(new Driver(userInput.getText().toString()));
                                        }
                                    }
                                    refresh();
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

        });

        // Setup LHS of the view (list of unsent people)
        ListView personsList = (ListView) findViewById(R.id.personsList);
        unsentPersons_ = new ArrayList<>();
        for(RideGroup rg: unsentRidesGroup_){
            unsentPersons_.add(rg.driver);
            for(Person rider: rg.riders)
                unsentPersons_.add(rider);
        }
        Collections.sort(unsentPersons_);

        personsListAdapter_ = new PersonsListAdapter(this, unsentPersons_, this);
        personsList.setAdapter(personsListAdapter_);


        // Setup RHS of the view (list of sent rideGroups)
        Log.d("SplitView", "Size of sentRidesGroup: " + sentRidesGroup_.size());
        for (RideGroup r : sentRidesGroup_) {
            Log.d("SplitView", r.driver.toString());
            for (Person p : r.riders) {
                Log.d("SplitView", p.toString());
            }
        }
        GridView sentRideGroups = (GridView) findViewById(R.id.rideGroups);
        sentRideGroups.setNumColumns(1);

        rideGroupAdapter_ =
                new RideGroupAdapter(this, sentRidesGroup_, null);
        sentRideGroups.setAdapter(rideGroupAdapter_);

        //listener for button
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new SendCarFromListFabListener(this));
        updateButtonVisibility();

    }
    public void updateButtonVisibility(){
        //get floating action buttons
        FloatingActionButton fabSendCar = (FloatingActionButton) findViewById(R.id.fab2);
        //set all as invisible
        fabSendCar.setVisibility(View.INVISIBLE);
        //set the correct fab as visible if necessary
        if(selectedPersons_.size() >= 1 && RideGroup.checkContainsExactlyOneDriver(selectedPersons_)){ //there is only one person selected and it's a rider
            fabSendCar.setVisibility(View.VISIBLE);
        }
    }
}
