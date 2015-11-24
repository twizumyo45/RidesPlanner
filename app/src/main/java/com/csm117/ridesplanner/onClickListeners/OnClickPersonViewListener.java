package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.csm117.ridesplanner.ViewPersonsListActivity;
import com.csm117.ridesplanner.entities.Person;

/**
 * Created by Roger on 11/14/2015.
 */
public class OnClickPersonViewListener implements View.OnClickListener{
    ViewPersonsListActivity viewPersonsListActivity_;
    Person person_;

    public OnClickPersonViewListener(ViewPersonsListActivity vra, Person p){
        viewPersonsListActivity_ = vra;
        person_ = p;
    }

    public void onClick(View view){
        if (!viewPersonsListActivity_.selectedPersons_.contains(person_)) {
            view.setBackgroundColor(0x6F00FF80);
            viewPersonsListActivity_.selectedPersons_.add(person_);
            Log.d("listener", "adding: " + person_.toString());
        } else{
            view.setBackgroundColor(Color.TRANSPARENT);
            viewPersonsListActivity_.selectedPersons_.remove(person_);
            Log.d("listener", "removing: " + person_.toString());
        }
        //viewPersonsListActivity_.updateButtonVisibility();
        return;
    }
}
