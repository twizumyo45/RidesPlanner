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
    private static List<RideGroup> rideGroups_ = new ArrayList<RideGroup>();
    private static List<Person> unsentPersons_ = new ArrayList<Person>();

    private Sheet() {

    }

    public static List<RideGroup> getRideGroups() {
        return rideGroups_;
    }

    public static List<Person> getUnsentPersons() {
        return unsentPersons_;
    }

    public static void getDataFromOnlineSheet() {
        class MyPushCallback implements OnTaskCompleted {
            public void onTaskCompleted(Object output) {
                //do your stuff with the result stuff
                Log.d("Google Scripts", "PUSH WORKED!");
                rideGroups_.clear();
                unsentPersons_.clear();


                for (ArrayList<String> car : (ArrayList<ArrayList<String>>) output) {
                    List<Person> riders = new ArrayList<Person>();
                    Person driver = null;
                    for (int i = 0; i < car.size(); i++) {
                        if (i == 0)
                            driver = new Driver(car.get(i));
                        else
                            riders.add(new Rider(car.get(i)));
                    }
                    rideGroups_.add(new RideGroup(driver, riders));
                }

                //TODO: make an unsent persons format
                for (int k = 0; k < 5; k++) {
                    unsentPersons_.add(new Driver("driver" + k));
                }
                for (int k = 0; k < 20; k++) {
                    unsentPersons_.add(new Rider("rider" + k));
                }
                sortNames();
                if (ViewRidesActivity.adapter_ != null)
                    ViewRidesActivity.adapter_.notifyDataSetChanged();
                //TODO: update view persons list adapter
            }
        }

        List<Object> objectList = new ArrayList<Object>();
        objectList.add("1p8IvZm5UWtO6wY8LO8nmhYoUImyc1wgqilGr9ictZkc"); // sheet id
        MyPushCallback cb = new MyPushCallback();
        new MakeRequestTask("getData", objectList, cb).execute();
    }

    public static void pushDataToOnlineSheet(){
        class MyPullCallback implements OnTaskCompleted{
            public void onTaskCompleted(Object output){
                //do your stuff with the result stuff
                Log.d("Google Scripts", "PUSH WORKED!");
            }
        }

        List<Object> objectList = new ArrayList<Object>();
        objectList.add("1p8IvZm5UWtO6wY8LO8nmhYoUImyc1wgqilGr9ictZkc"); // sheet id
        MyPullCallback cb = new MyPullCallback();
        new MakeRequestTask("getData", objectList, cb).execute(); //get mCredential
    }

    public static void sortNames(){
        for (RideGroup rg: rideGroups_)
            Collections.sort(rg.riders);
    }
}
