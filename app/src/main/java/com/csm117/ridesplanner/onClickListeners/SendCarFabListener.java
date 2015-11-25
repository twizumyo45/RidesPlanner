package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;

/**
 * Created by Roger on 11/21/2015.
 */
//if there is exactly one driver selected
    //then the sendcar button appears
    //sending a car grays out everyone in the car
public class SendCarFabListener implements View.OnClickListener{
    ViewRidesActivity viewRidesActivity_;

    public SendCarFabListener(ViewRidesActivity vra){
        viewRidesActivity_ = vra;
    }

    public void onClick(View view) {
        if (viewRidesActivity_.selectedPersons_.size() == 1 && RideGroup.checkContainsExactlyOneDriver(viewRidesActivity_.selectedPersons_)){
            Person driver = viewRidesActivity_.selectedPersons_.get(0);
            driver.getRideGroupView().setBackgroundColor(Color.TRANSPARENT);
            RideGroup rideGroup = RideGroup.getRideGroupByDriver(driver, viewRidesActivity_.rideGroups_);

            Sheet.getUnsentRideGroups().remove(rideGroup);
            Sheet.getSentRideGroups().add(rideGroup);
            /*driver.getRideGroupView().setVisibility(View.INVISIBLE);
            for (Person p: rideGroup.riders)
                p.getRideGroupView().setVisibility(View.INVISIBLE);*/


            Snackbar.make(view, "Highlighting people to indicate car has been sent", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            viewRidesActivity_.adapter_.notifyDataSetChanged();
            viewRidesActivity_.selectedPersons_.clear();
            viewRidesActivity_.updateButtonVisibility();
            Sheet.pushDataToOnlineSheet();
        }
    }
}
