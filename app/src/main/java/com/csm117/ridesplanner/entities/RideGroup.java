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
}
