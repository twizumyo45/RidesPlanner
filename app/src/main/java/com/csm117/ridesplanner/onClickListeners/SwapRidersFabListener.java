package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;

/**
 * Created by Roger on 11/15/2015.
 */
public class SwapRidersFabListener implements View.OnClickListener {

    ViewRidesActivity viewRidesActivity_;

    public SwapRidersFabListener(ViewRidesActivity vra){
        viewRidesActivity_ = vra;
    }

    public void onClick(View view) {
        //check the correct number of ppl to be swapped are selected (2 ppl)
        if (viewRidesActivity_.selectedPersons_.size() == 2) {
            Person person1 = viewRidesActivity_.selectedPersons_.get(0);
            Person person2 = viewRidesActivity_.selectedPersons_.get(1);
            //check that the ppl are in different groups
            boolean inDiffGroups = true;
            for (RideGroup rg: viewRidesActivity_.rideGroups_){
                if (rg.contains(person1) && rg.contains(person2))
                    inDiffGroups = false;
            }

            if (inDiffGroups) {
                for (RideGroup rg : viewRidesActivity_.rideGroups_) {
                    if (rg.remove(person1)) {
                        rg.add(person2);
                    } else if (rg.remove(person2)) {
                        rg.add(person1);
                    }
                }
            }

            person1.getRideGroupView().setBackgroundColor(Color.TRANSPARENT);
            person2.getRideGroupView().setBackgroundColor(Color.TRANSPARENT);

            Snackbar.make(view, "Swapping selected riders!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            viewRidesActivity_.adapter_.notifyDataSetChanged();
            viewRidesActivity_.selectedPersons_.clear();
            viewRidesActivity_.updateButtonVisibility();
            Sheet.sortNames();
            Sheet.pushDataToOnlineSheet();
        }
    }
}
