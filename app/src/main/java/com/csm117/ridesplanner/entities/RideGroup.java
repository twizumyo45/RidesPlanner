package com.csm117.ridesplanner.entities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        int foundRider = -1;
        for (int i = 0; i < riders.size(); i++){
            if (riders.get(i).compareTo(personToBeDeleted)==0) {
                foundRider = i;
            }
        }
        if(foundRider != -1){
            riders.remove(foundRider);
            Log.d("Remove", "removing: " + personToBeDeleted.toString());
            return true;
        }

        if(driver.compareTo(personToBeDeleted)==0) {
            driver = new Driver("");
            Log.d("Remove", "removing: " + personToBeDeleted.toString());
            return true;
        }
        return false;
    }

    public void add(Person personToBeAdded){
        riders.add(personToBeAdded);
    }

    //STATIC METHODS
    //input: list of persons
    //output: boolean; true if list of persons contains a driver
    public static boolean checkContainsExactlyOneDriver(ArrayList<Person> personsList){
        int containsDriver = 0;
        for (Person p: personsList) {
            if (p.getClass() == Driver.class)
                containsDriver++;
        }
        return containsDriver==1;
    }

    //input: list of persons
    //output: boolean; true if list of persons contains exactly one driver
    public static boolean checkSingleDriver(ArrayList<Person> personsList){
        int countDrivers = 0;
        for (Person p: personsList) {
            if (p.getClass() == Driver.class)
                countDrivers++;
        }
        return countDrivers == 1;
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

    //returns the ride group the given driver is in
    //if driver DNE, then return null
    public static RideGroup getRideGroupByDriver(Person driver, List<RideGroup> rideGroups){
        for(RideGroup rg: rideGroups){
            if (rg.driver == driver)
                return rg;
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
        driver.getRideGroupView().setBackgroundColor(Color.TRANSPARENT);

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

    public static RideGroup createRideGroupFromSelected(List<Person> personList) {
        //find each person from personList in groupList
        //if they are a driver, set as driver
        //if they are a person, add that person to arraylist

        String newDriverName="";
        List<Person> newRiders = new ArrayList<Person>();

        for (Person p: personList){
            if (p.getClass() == Driver.class)
                newDriverName = p.toString();
            else
                newRiders.add(p);
        }

        Driver newDriver = new Driver(newDriverName);
        RideGroup rideGroup = new RideGroup(newDriver, newRiders);
        return rideGroup;
    }
}
