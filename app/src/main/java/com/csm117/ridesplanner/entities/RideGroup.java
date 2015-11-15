package com.csm117.ridesplanner.entities;

import com.csm117.ridesplanner.entities.Person;

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

    public boolean contains(Person person){
        for (Person r: riders){
            if (r==person) {
                return true;
            }
        }
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
}
