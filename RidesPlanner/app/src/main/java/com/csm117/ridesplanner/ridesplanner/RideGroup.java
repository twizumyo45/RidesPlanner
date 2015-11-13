package com.csm117.ridesplanner.ridesplanner;

import java.util.List;

/**
 * Created by julianyang on 11/12/15.
 */
public class RideGroup {
    String driver;
    List<String> riders;

    RideGroup(String driver, List<String> riders) {
        this.driver = driver;
        this.riders = riders;
    }
}
