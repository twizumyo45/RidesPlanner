package com.csm117.ridesplanner.entities;

import android.graphics.Typeface;

/**
 * Created by Roger on 11/13/2015.
 */
public class Rider extends Person{
    public Rider(String name){
        super(name);
    }
    @Override
    public Typeface getTypeface() {
        return Typeface.DEFAULT;
    }
}
