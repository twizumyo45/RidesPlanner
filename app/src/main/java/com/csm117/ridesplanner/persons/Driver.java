package com.csm117.ridesplanner.persons;

import android.graphics.Typeface;

/**
 * Created by Roger on 11/13/2015.
 */
public class Driver extends Person {
    public Driver(String name){
        super(name);
    }

    @Override
    public Typeface getTypeface() {
        return Typeface.DEFAULT_BOLD;
    }
}
