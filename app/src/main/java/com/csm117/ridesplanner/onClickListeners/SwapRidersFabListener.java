package com.csm117.ridesplanner.onClickListeners;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.RideGroup;

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
            //check that the ppl are in different groups
            boolean inDiffGroups = true;
            for (RideGroup rg: viewRidesActivity_.rideGroups_){
                if (rg.contains(viewRidesActivity_.selectedPersons_.get(0)) && rg.contains(viewRidesActivity_.selectedPersons_.get(1)))
                    inDiffGroups = false;
            }

            if (inDiffGroups) {
                for (RideGroup rg : viewRidesActivity_.rideGroups_) {
                    if (rg.remove(viewRidesActivity_.selectedPersons_.get(0))) {
                        rg.add(viewRidesActivity_.selectedPersons_.get(1));
                    } else if (rg.remove(viewRidesActivity_.selectedPersons_.get(1))) {
                        rg.add(viewRidesActivity_.selectedPersons_.get(0));
                    }
                }
            }
            else{
                for (RideGroup rg : viewRidesActivity_.rideGroups_) {
                    if (rg.contains(viewRidesActivity_.selectedPersons_.get(0)) && rg.contains(viewRidesActivity_.selectedPersons_.get(0))){
                        rg.remove(viewRidesActivity_.selectedPersons_.get(0));
                        rg.remove(viewRidesActivity_.selectedPersons_.get(1));
                        rg.add(viewRidesActivity_.selectedPersons_.get(0));
                        rg.add(viewRidesActivity_.selectedPersons_.get(1));
                    }
                }
            }
            viewRidesActivity_.adapter_.notifyDataSetChanged();
            Snackbar.make(view, "Swapping selected riders!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            viewRidesActivity_.selectedPersons_.clear();
            viewRidesActivity_.updateButtonVisibility();
        }
    }
}
