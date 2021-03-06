package com.csm117.ridesplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;
import com.csm117.ridesplanner.onClickListeners.DeleteRidersFabListener;
import com.csm117.ridesplanner.onClickListeners.MoveRiderFabListener;
import com.csm117.ridesplanner.onClickListeners.SendCarFabListener;
import com.csm117.ridesplanner.onClickListeners.SwapRidersFabListener;
import com.csm117.ridesplanner.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class ViewRidesActivity extends ViewNavigation {
    //TODO: make getters and setters instead of public
    public ArrayList<Person> selectedPersons_ = new ArrayList<Person>();
    public List<RideGroup> rideGroups_ = Sheet.getUnsentRideGroups();
    public List<List<Person>> riders_ = new ArrayList<List<Person>>();
    public static ArrayAdapter<RideGroup> adapter_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedPersons_ = new ArrayList<Person>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_rides_navigation_activity);
        super.setUpNav();

        Sheet.getDataFromOnlineSheet();
        GridView rideGroups = (GridView) findViewById(R.id.rideGroups);


        adapter_ = new RideGroupAdapter(this, rideGroups_, this);
        rideGroups.setAdapter(adapter_);

        //hook up listeners to buttons
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new DeleteRidersFabListener(this));

        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        fabSwap.setOnClickListener(new SwapRidersFabListener(this));

        FloatingActionButton fabMoveRider = (FloatingActionButton) findViewById(R.id.fabMoveRider);
        fabMoveRider.setOnClickListener(new MoveRiderFabListener(this));

        FloatingActionButton fabSendCar = (FloatingActionButton) findViewById(R.id.fabSendCar);
        fabSendCar.setOnClickListener(new SendCarFabListener(this));
    }
    public void updateButtonVisibility(){
        //get floating action buttons
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabSwap);
        FloatingActionButton fabMoveRider = (FloatingActionButton) findViewById(R.id.fabMoveRider);
        FloatingActionButton fabSendCar = (FloatingActionButton) findViewById(R.id.fabSendCar);

        //set all as invisible
        fabDelete.setVisibility(View.INVISIBLE);
        fabSwap.setVisibility(View.INVISIBLE);
        fabMoveRider.setVisibility(View.INVISIBLE);
        fabSendCar.setVisibility(View.INVISIBLE);

        //set the correct fab as visible if necessary
        if(selectedPersons_.size() == 1 && !RideGroup.checkContainsExactlyOneDriver(selectedPersons_)){ //there is only one person selected and it's a rider
            fabDelete.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 1 && RideGroup.checkContainsExactlyOneDriver(selectedPersons_)){
            fabSendCar.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 2 && RideGroup.checkContainsExactlyOneDriver(selectedPersons_)){ //there are two ppl selected and
            fabMoveRider.setVisibility(View.VISIBLE);
        }
        else if (selectedPersons_.size() == 2){
            fabSwap.setVisibility(View.VISIBLE);
        }
    }

}
