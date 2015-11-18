package com.csm117.ridesplanner.entities;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Roger on 11/13/2015.
 */
public abstract class Person implements Comparable<Person>{
    String name_;
    private TextView rideGroupView_;
    private TextView personListView_;

    public Person(String name){
        this.name_ = name;
    }

    public String toString(){
        return name_;
    }

    public TextView getRideGroupView(){
        return rideGroupView_;
    }
    public void setRideGroupView(TextView rgv){
        rideGroupView_ = rgv;
    }

    public TextView getPersonListView(){
        return personListView_;
    }
    public void setPersonListView_(TextView plv){
        personListView_ = plv;
    }

    public int compareTo(Person p) {
        return this.toString().compareTo(p.toString());

    }

    public abstract Typeface getTypeface();
}
