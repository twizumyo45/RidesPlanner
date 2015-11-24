package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewPersonsListActivity;
import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;
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
        if(viewPersonsListActivity_.selectedPersons_.size()>=1 && RideGroup.checkContainsDriver(viewPersonsListActivity_.selectedPersons_)) {
            RideGroup rg = RideGroup.createRideGroupFromSelected(Sheet.getUnsentRideGroups(), viewPersonsListActivity_.selectedPersons_);
            //if(rg!=null) {
                Sheet.getSentRideGroups().add(rg);
                for (int i = 0; i < viewPersonsListActivity_.selectedPersons_.size(); i++) {
                    Sheet.getUnsentRideGroups().remove(viewPersonsListActivity_.selectedPersons_.get(i));
                    //remove sent riders/driver
                }

                viewPersonsListActivity_.adapter_.notifyDataSetChanged();
                viewPersonsListActivity_.selectedPersons_.clear();
                Sheet.pushDataToOnlineSheet();
            //}
        }
    }
}
