package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;

/**
 * Created by Roger on 11/15/2015.
 */
public class MoveRiderFabListener implements View.OnClickListener {

    ViewRidesActivity viewRidesActivity_;

    public MoveRiderFabListener(ViewRidesActivity vra){
        viewRidesActivity_ = vra;
    }

    @Override
    public void onClick(View view) {
        if (viewRidesActivity_.selectedPersons_.size() == 2 && RideGroup.checkContainsDriver(viewRidesActivity_.selectedPersons_)){
            Snackbar.make(view, "Moving selected rider!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            RideGroup.moveRider(viewRidesActivity_.selectedPersons_, viewRidesActivity_.rideGroups_);



            viewRidesActivity_.adapter_.notifyDataSetChanged();
            viewRidesActivity_.selectedPersons_.clear();
            viewRidesActivity_.updateButtonVisibility();
            Sheet.sortNames();
            Sheet.pushDataToOnlineSheet();
        }
    }
}