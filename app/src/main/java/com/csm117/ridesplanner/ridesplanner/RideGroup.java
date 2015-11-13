package com.csm117.ridesplanner.ridesplanner;

import com.csm117.ridesplanner.ridesplanner.persons.Driver;
import com.csm117.ridesplanner.ridesplanner.persons.Person;
import com.csm117.ridesplanner.ridesplanner.persons.Rider;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class RideGroup {
    Person driver;
    List<Person> riders;

    RideGroup(Person driver, List<Person> riders) {
        this.driver = driver;
        this.riders = riders;
    }
}
