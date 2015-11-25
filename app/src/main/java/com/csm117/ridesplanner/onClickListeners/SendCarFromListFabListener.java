package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.csm117.ridesplanner.ViewPersonsListActivity;
import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Driver;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;

import java.util.ArrayList;

/**
 * Created by Roger on 11/23/2015.
 */
//if there is a driver and >= 0 riders selected, send car
public class SendCarFromListFabListener implements View.OnClickListener{
    ViewPersonsListActivity viewPersonsListActivity_;

    public SendCarFromListFabListener(ViewPersonsListActivity vpla) {
        viewPersonsListActivity_ = vpla;

    }

    public void onClick(View view) {
        Log.d("send", "SENTCLICK!");
        if(viewPersonsListActivity_.selectedPersons_.size()>=1 && RideGroup.checkContainsDriver(viewPersonsListActivity_.selectedPersons_)) {
            for(Person p: viewPersonsListActivity_.selectedPersons_)
                p.getPersonListView().setBackgroundColor(Color.BLUE);


            RideGroup newRideGroup = RideGroup.createRideGroupFromSelected(viewPersonsListActivity_.selectedPersons_);
            Sheet.getSentRideGroups().add(newRideGroup);

            for(Person p: viewPersonsListActivity_.selectedPersons_){
                for(RideGroup rg: Sheet.getUnsentRideGroups()){
                    rg.remove(p);
                }
            }

            //look for the index of empty driver
            int indexRgToRmv = -1;
            int k = -1;
            for(int i = 0; i < Sheet.getUnsentRideGroups().size(); i++){
                RideGroup rg = Sheet.getUnsentRideGroups().get(i);
                if (rg.driver.toString().compareTo("") == 0){
                    k = (i+1)%(Sheet.getUnsentRideGroups().size());
                    indexRgToRmv = i;
                }
            }

            //having found the empty driver, remove all his riders
            //add these riders elsewhere
            //then remove the ridegroup
            RideGroup rg = Sheet.getUnsentRideGroups().get(indexRgToRmv);
            if (Sheet.getUnsentRideGroups().size() == 1) {
                Sheet.getUnsentRideGroups().add(new RideGroup(new Driver(""), rg.riders));
            }
            else {
                for (Person rider : rg.riders) {
                    Sheet.getUnsentRideGroups().get(k).add(rider);
                }
            }
            Sheet.getUnsentRideGroups().remove(indexRgToRmv);

            viewPersonsListActivity_.refresh();
            viewPersonsListActivity_.rideGroupAdapter_.notifyDataSetChanged();
            viewPersonsListActivity_.personsListAdapter_.notifyDataSetChanged();

            viewPersonsListActivity_.selectedPersons_.clear();
            Sheet.pushDataToOnlineSheet();
        }
    }
}
