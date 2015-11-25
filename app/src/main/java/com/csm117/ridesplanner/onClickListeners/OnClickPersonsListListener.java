package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.csm117.ridesplanner.ViewPersonsListActivity;
import com.csm117.ridesplanner.entities.Person;

/**
 * Created by Patrick on 11/22/2015.
 */
public class OnClickPersonsListListener implements View.OnClickListener{
    ViewPersonsListActivity ViewPersonsListActivity_;
    Person person_;

    public OnClickPersonsListListener(ViewPersonsListActivity vra, Person p){
        ViewPersonsListActivity_ = vra;
        person_ = p;
    }

    public void onClick(View view){
        if (!ViewPersonsListActivity_.selectedPersons_.contains(person_)) {
            view.setBackgroundColor(0x6F00FF80);
            ViewPersonsListActivity_.selectedPersons_.add(person_);
            Log.d("listener", "adding: " + person_.toString());
        } else{
            view.setBackgroundColor(Color.TRANSPARENT);
            ViewPersonsListActivity_.selectedPersons_.remove(person_);
            Log.d("listener", "removing: " + person_.toString());
        }
        ViewPersonsListActivity_.updateButtonVisibility();
        return;
    }
}