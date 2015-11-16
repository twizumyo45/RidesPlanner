package com.csm117.ridesplanner.entities;

import android.util.Log;

import com.csm117.ridesplanner.entities.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class RideGroup {
    public Person driver;
    public List<Person> riders;

    public RideGroup(Person driver, List<Person> riders) {
        this.driver = driver;
        this.riders = riders;
    }

    //check if this ridegroup contains the person
    public boolean contains(Person person){
        for (Person r: riders){
            if (r==person) {
                return true;
            }
        }
        if (driver == person)
            return true;

        return false;
    }
    //returns whether or not the person was successfully removed
    public boolean remove(Person personToBeDeleted){
        for (Person r: riders){
            if (r==personToBeDeleted) {
                riders.remove(r);
                return true;
            }
        }
        return false;
    }

    public void add(Person personToBeAdded){
        riders.add(personToBeAdded);
    }

    //STATIC METHODS
    //input: list of persons
    //output: boolean; true if list of persons contains a driver
    public static boolean checkContainsDriver(ArrayList<Person> personsList){
        boolean containsDriver = false;
        for (Person p: personsList) {
            if (p.getClass() == Driver.class)
                containsDriver = true;
        }
        return containsDriver;
    }

    //returns the first driver in the list
    public static Person getDriver(ArrayList<Person> personsList){
        for (Person p: personsList) {
            if (p.getClass() == Driver.class)
                return p;
        }
        return null;
    }

    //returns the first rider in the list
    public static Person getRider(ArrayList<Person> personsList){
        for (Person p: personsList) {
            if (p.getClass() == Rider.class)
                return p;
        }
        return null;
    }

    //input: list of persons
    //precondition: list of persons must contain exactly one rider and
        //exactly one driver
    //output: whether or not moveRider was successfully moved
    public static boolean moveRider(ArrayList<Person> personsList, List<RideGroup> rideGroups){
        Person driver = getDriver(personsList);
        Person rider = getRider(personsList);

        if (driver == null || rider == null)
            return false;

        int stepNum = 0;
        for (RideGroup rg : rideGroups) {
            if (rg.remove(rider))
                stepNum += 1;
        }
        for (RideGroup rg : rideGroups) {
            if (rg.contains(driver)) {
                rg.add(rider);
                stepNum += 1;
            }
        }
        Log.d("MoveRider", "stepNum: " + stepNum);
        return stepNum == 2;
    }
}
