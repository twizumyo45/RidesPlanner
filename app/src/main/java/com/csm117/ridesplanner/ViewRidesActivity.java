package com.csm117.ridesplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.ridesplanner.R;
import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.Rider;

import java.util.ArrayList;
import java.util.List;

public class ViewRidesActivity extends ViewNavigation {
    //TODO: make getters and setters instead of public
    public ArrayList<Person> selectedPersons_ = new ArrayList<Person>();
    private List<RideGroup> rideGroups_ = new ArrayList<RideGroup>();
    private List<List<Person>> riders_ = new ArrayList<List<Person>>();
    private ArrayAdapter<RideGroup> adapter_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_rides_navigation_activity);
        super.setUpNav();

        //TODO: link with real sheets
        GridView gridview = (GridView) findViewById(R.id.gridView);

        for (int k = 0; k < 5; k++) {
            riders_.add(new ArrayList<Person>());
            for (int i = 0; i < 4; i++) {
                riders_.get(k).add(new Rider("rider" + (k * 4 + i)));
            }
        }
        for (int i = 0; i < 5; i++) {
            Person driver = new Driver("driver" + i);
            rideGroups_.add(new RideGroup(driver, riders_.get(i)));
        }
        adapter_ = new RideGroupAdapter(this, rideGroups_, this);
        gridview.setAdapter(adapter_);

        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPersons_.size() == 1) {
                    Snackbar.make(view, "Deleting selected rider!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    for (RideGroup rg : rideGroups_) {
                        rg.remove(selectedPersons_.get(0));
                    }
                    adapter_.notifyDataSetChanged();
                    selectedPersons_.clear();
                }
            }
        });

        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        fabSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check the correct number of ppl to be swapped are selected (2 ppl)
                if (selectedPersons_.size() == 2) {
                    //check that the ppl are in different groups
                    boolean inDiffGroups = true;
                    for (RideGroup rg: rideGroups_){
                        if (rg.contains(selectedPersons_.get(0)) && rg.contains(selectedPersons_.get(1)))
                            inDiffGroups = false;
                    }

                    if (inDiffGroups) {
                        for (RideGroup rg : rideGroups_) {
                            if (rg.remove(selectedPersons_.get(0))) {
                                rg.add(selectedPersons_.get(1));
                            } else if (rg.remove(selectedPersons_.get(1))) {
                                rg.add(selectedPersons_.get(0));
                            }
                        }
                    }
                    else{
                        for (RideGroup rg : rideGroups_) {
                            if (rg.contains(selectedPersons_.get(0)) && rg.contains(selectedPersons_.get(0))){
                                rg.remove(selectedPersons_.get(0));
                                rg.remove(selectedPersons_.get(1));
                                rg.add(selectedPersons_.get(0));
                                rg.add(selectedPersons_.get(1));
                            }
                        }
                    }
                    adapter_.notifyDataSetChanged();
                    Snackbar.make(view, "Swapping selected riders!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    selectedPersons_.clear();
                }
            }
        });
    }
    public void updateButtonVisibility(){
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        fabDelete.setVisibility(View.INVISIBLE);
        fabSwap.setVisibility(View.INVISIBLE);
        if(selectedPersons_.size() == 1){
            fabDelete.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 2){
            fabSwap.setVisibility(View.VISIBLE);
        }
    }

}
