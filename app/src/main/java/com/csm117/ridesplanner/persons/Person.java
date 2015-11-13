package com.csm117.ridesplanner.persons;

import android.graphics.Typeface;

/**
 * Created by Roger on 11/13/2015.
 */
public abstract class Person {
    String name;

    public Person(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public abstract Typeface getTypeface();
}
