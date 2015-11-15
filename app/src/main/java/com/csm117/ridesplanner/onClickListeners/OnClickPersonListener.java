package com.csm117.ridesplanner.onClickListeners;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.entities.Person;

/**
 * Created by Roger on 11/14/2015.
 */
public class OnClickPersonListener implements View.OnClickListener{
    ViewRidesActivity viewRidesActivity_;
    Person person_;

    public OnClickPersonListener(ViewRidesActivity vra, Person p){
        viewRidesActivity_ = vra;
        person_ = p;
    }

    public void onClick(View view){
        if (!viewRidesActivity_.selectedPersons_.contains(person_)) {
            view.setBackgroundColor(0x6F00FF80);
            viewRidesActivity_.selectedPersons_.add(person_);
            Log.d("listener", "adding: " + person_.toString());
        } else{
            view.setBackgroundColor(Color.TRANSPARENT);
            viewRidesActivity_.selectedPersons_.remove(person_);
            Log.d("listener", "removing: " + person_.toString());
        }
        return;
    }
}
