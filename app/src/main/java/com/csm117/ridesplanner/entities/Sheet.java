package com.csm117.ridesplanner.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roger on 11/16/2015.
 */
public class Sheet {
    private static List<RideGroup> rideGroups_ = new ArrayList<RideGroup>();
    private static List<Person> unsentPersons_ = new ArrayList<Person>();

    private Sheet(){

    }

    public static List<RideGroup> getRideGroups(){
        return rideGroups_;
    }
    public static List<Person> getUnsentPersons(){
        return unsentPersons_;
    }

    public static void sync(){
        //TODO: sync with sheets
        //right now just fills with dummy data
        rideGroups_.clear();
        unsentPersons_.clear();

        List<List<Person>> riders = new ArrayList<List<Person>>();
        for (int k = 0; k < 5; k++) {
            riders.add(new ArrayList<Person>());
            for (int i = 0; i < 4; i++) {
                riders.get(k).add(new Rider("rider" + (k * 4 + i)));
            }
        }
        for (int i = 0; i < 5; i++) {
            Person driver = new Driver("driver" + i);
            rideGroups_.add(new RideGroup(driver, riders.get(i)));
        }

        for (int k = 0; k < 5; k++) {
            unsentPersons_.add(new Driver("driver"+k));
        }
        for (int k = 0; k < 20; k++) {
            unsentPersons_.add(new Rider("rider"+k));
        }
        sortNames();
    }

    public static void sortNames(){
        for (RideGroup rg: rideGroups_)
            Collections.sort(rg.riders);
    }
}
