package com.csm117.ridesplanner.onClickListeners;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.RideGroup;

/**
 * Created by Roger on 11/15/2015.
 */
public class DeleteRidersFabListener implements View.OnClickListener {

    ViewRidesActivity viewRidesActivity_;

    public DeleteRidersFabListener(ViewRidesActivity vra){
        viewRidesActivity_ = vra;
    }

    @Override
    public void onClick(View view) {
        if (viewRidesActivity_.selectedPersons_.size() == 1) {
            Snackbar.make(view, "Deleting selected rider!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            for (RideGroup rg : viewRidesActivity_.rideGroups_) {
                rg.remove(viewRidesActivity_.selectedPersons_.get(0));
            }
            viewRidesActivity_.adapter_.notifyDataSetChanged();
            viewRidesActivity_.selectedPersons_.clear();
            viewRidesActivity_.updateButtonVisibility();
        }
    }
}
