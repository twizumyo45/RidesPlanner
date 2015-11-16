package com.csm117.ridesplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.onClickListeners.DeleteRidersFabListener;
import com.csm117.ridesplanner.onClickListeners.MoveRiderFabListener;
import com.csm117.ridesplanner.onClickListeners.SwapRidersFabListener;
import com.csm117.ridesplanner.ridesplanner.R;
import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.Rider;

import java.util.ArrayList;
import java.util.List;

public class ViewRidesActivity extends ViewNavigation {
    //TODO: make getters and setters instead of public
    public ArrayList<Person> selectedPersons_ = new ArrayList<Person>();
    public List<RideGroup> rideGroups_ = new ArrayList<RideGroup>();
    public List<List<Person>> riders_ = new ArrayList<List<Person>>();
    public ArrayAdapter<RideGroup> adapter_;

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

        //TODO: lol clean dis up
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new DeleteRidersFabListener(this));

        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        fabSwap.setOnClickListener(new SwapRidersFabListener(this));

        FloatingActionButton fabMoveRider = (FloatingActionButton) findViewById(R.id.fabMoveRider);
        fabMoveRider.setOnClickListener(new MoveRiderFabListener(this));
    }
    public void updateButtonVisibility(){
        //get floating action buttons
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        FloatingActionButton fabMoveRider = (FloatingActionButton) findViewById(R.id.fabMoveRider);

        //set all as invisible
        fabDelete.setVisibility(View.INVISIBLE);
        fabSwap.setVisibility(View.INVISIBLE);
        fabMoveRider.setVisibility(View.INVISIBLE);

        //set the correct fab as visible if necessary
        if(selectedPersons_.size() == 1 && !RideGroup.checkContainsDriver(selectedPersons_)){ //there is only one person selected and it's a rider
            fabDelete.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 2 && RideGroup.checkContainsDriver(selectedPersons_)){ //there are two ppl selected and
            fabMoveRider.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 2){
            fabSwap.setVisibility(View.VISIBLE);
        }
    }

}
