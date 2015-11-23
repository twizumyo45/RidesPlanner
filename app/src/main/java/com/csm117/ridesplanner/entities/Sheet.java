package com.csm117.ridesplanner.entities;

import android.util.Log;

import com.csm117.ridesplanner.ViewRidesActivity;
import com.csm117.ridesplanner.communication.MakeRequestTask;
import com.csm117.ridesplanner.communication.OnTaskCompleted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roger on 11/16/2015.
 */
public class Sheet {
    private static Sheet instance;
    private static List<RideGroup> sentRideGroups_ = new ArrayList<RideGroup>();
    private static List<RideGroup> unsentRideGroups_ = new ArrayList<RideGroup>();
    public final static String ridesSheetName = "ridesplanner-rides";
    private static String sheetID = "";

    private Sheet() {

    }

    public static List<RideGroup> getSentRideGroups() {
        return sentRideGroups_;
    }

    public static List<RideGroup> getUnsentRideGroups() {
        return unsentRideGroups_;
    }

    public static void updateSheetID(String id) {
        sheetID = id;
    }

    public static void getDataFromOnlineSheet() {
        class MyPushCallback implements OnTaskCompleted {
            public void onTaskCompleted(Object output) {
                //do your stuff with the result stuff
                Log.d("Google Scripts", "PULL WORKED!");
                sentRideGroups_.clear();
                unsentRideGroups_.clear();


                for (ArrayList<String> car : (ArrayList<ArrayList<String>>) output) {
                    List<Person> riders = new ArrayList<Person>();
                    Person driver = null;
                    if (car.get(0).equals("sent")) { //fill up the sent car
                        for (int i = 1; i < car.size(); i++) { //skip first entry, which is "sent"
                            if (i == 1) //first name should be the driver
                                driver = new Driver(car.get(i));
                            else if (!car.get(i).equals("")) //add the riders if the name is not empty
                                riders.add(new Rider(car.get(i)));
                        }
                        sentRideGroups_.add(new RideGroup(driver, riders));
                    }
                    else { //else is an unsent car
                        for (int i = 0; i < car.size(); i++) { //skip first entry, which is "sent"
                            if (i == 0) //first name should be the driver
                                driver = new Driver(car.get(i));
                            else if (!car.get(i).equals("")) //add the riders if the name is not empty
                                riders.add(new Rider(car.get(i)));
                        }
                        unsentRideGroups_.add(new RideGroup(driver, riders));
                    }

                }
            }
        }

        List<Object> parameters = new ArrayList<Object>();
        Log.d("Sheet", "sheetID is: " + sheetID);
        parameters.add(sheetID); // sheet id
        MyPushCallback cb = new MyPushCallback();
        new MakeRequestTask("getData", parameters, cb).execute();
    }

    public static void pushDataToOnlineSheet(){
        class MyPullCallback implements OnTaskCompleted{
            public void onTaskCompleted(Object output){
                //do your stuff
                Log.d("Google Scripts", "PUSH WORKED!");
            }
        }

        List<Object> parameters = new ArrayList<Object>();
        parameters.add(sheetID); // sheet id

        //translates our local data (arraylists of ridegroups/persons) into
        //arraylist of strings (format required by sheets)
        ArrayList<ArrayList<String>> newSheet = new ArrayList<ArrayList<String>>();

        //note: 2d array must be perfect rectangle; no jagged edges
        //therefore, must write in empty spaces if we run out of ppl
        //find max length car
        int maxRiderLen = 0;
        for (RideGroup rg: sentRideGroups_){
            if (rg.riders.size() > maxRiderLen)
                maxRiderLen = rg.riders.size();
        }
        for (RideGroup rg: unsentRideGroups_){
            if (rg.riders.size() > maxRiderLen)
                maxRiderLen = rg.riders.size();
        }

        for (RideGroup rg: sentRideGroups_){
            ArrayList<String> row = new ArrayList<String>();
            row.add("sent");
            row.add(rg.driver.toString());

            for (int i = 0; i < maxRiderLen; i++) {
                if (i < rg.riders.size())
                    row.add(rg.riders.get(i).toString());
                else
                    row.add("");
            }

            newSheet.add(row);
        }

        for (RideGroup rg: unsentRideGroups_){
            ArrayList<String> row = new ArrayList<String>();
            row.add(rg.driver.toString());

            for (int i = 0; i < maxRiderLen; i++) {
                if (i < rg.riders.size())
                    row.add(rg.riders.get(i).toString());
                else
                    row.add("");
            }

            newSheet.add(row);
        }

        parameters.add(newSheet);

        MyPullCallback cb = new MyPullCallback();
        new MakeRequestTask("refillData", parameters, cb).execute(); //get mCredential
    }

    public static void sortNames(){
        for (RideGroup rg: sentRideGroups_)
            Collections.sort(rg.riders);
        for (RideGroup rg: unsentRideGroups_)
            Collections.sort(rg.riders);
    }
}
