package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.csm117.ridesplanner.ViewPersonsListActivity;
import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;
import com.csm117.ridesplanner.entities.RideGroup;
import com.csm117.ridesplanner.entities.Sheet;

import java.util.ArrayList;

/**
 * Created by Patrick on 11/22/2015.
 */
public class AddCarFabListener implements View.OnClickListener{
    ViewPersonsListActivity ViewPersonsListActivity_;

    public AddCarFabListener(ViewPersonsListActivity vra){
        ViewPersonsListActivity_ = vra;
    }

    @Override
    public void onClick(View view) {
        if (RideGroup.checkSingleDriver(ViewPersonsListActivity_.selectedPersons_)){
            Snackbar.make(view, "Sending car!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            ArrayList<Person> personsList = new ArrayList<Person>();
            for(Person p : ViewPersonsListActivity_.selectedPersons_)
                    personsList.add(p);
            Person driver = RideGroup.getDriver(personsList);
            personsList.remove(driver);
            Sheet.getRideGroups().add(new RideGroup(driver, personsList));

            ViewPersonsListActivity_.adapter_.notifyDataSetChanged();
            ViewPersonsListActivity_.selectedPersons_.clear();
            ViewPersonsListActivity_.updateButtonVisibility();
            Sheet.sortNames();
            Sheet.pushDataToOnlineSheet();
        }
    }
}
