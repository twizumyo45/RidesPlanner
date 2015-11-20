package com.csm117.ridesplanner.entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.csm117.ridesplanner.communication.MakeRequestTask;
import com.csm117.ridesplanner.communication.OnTaskCompleted;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import java.util.ArrayList;
import java.util.Arrays;
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
        class MyCallback implements OnTaskCompleted {
            public void onTaskCompleted(Object output) {
                //do your stuff with the result stuff
                Log.d("credential", "CREDENTIALS WORKED!");
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
            }
        }

        List<Object> objectList = new ArrayList<Object>();
        objectList.add("1p8IvZm5UWtO6wY8LO8nmhYoUImyc1wgqilGr9ictZkc"); // sheet id
        MyCallback cb = new MyCallback();
        new MakeRequestTask("getData", objectList, cb).execute();
    }

    public static void pushDataToOnlineSheet(){
        class MyCallback implements OnTaskCompleted{
            public void onTaskCompleted(Object output){
                //do your stuff with the result stuff
                Log.d("credential", "CREDENTIALS WORKED!");
            }
        }

        List<Object> objectList = new ArrayList<Object>();
        objectList.add("1p8IvZm5UWtO6wY8LO8nmhYoUImyc1wgqilGr9ictZkc"); // sheet id
        MyCallback cb = new MyCallback();
        new MakeRequestTask("getData", objectList, cb).execute(); //get mCredential
    }

    /*
    public static void sync(){

        class MyCallback implements OnTaskCompleted{
            public void onTaskCompleted(Object output){
                //do your stuff with the result stuff
            }
        }

        List<Object> objectList = new ArrayList<Object>();
        MyCallback cb = new MyCallback();
        new MakeRequestTask(mCredential, "getData", objectList, cb).execute(); //get mCredential
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
*/
    public static void sortNames(){
        for (RideGroup rg: rideGroups_)
            Collections.sort(rg.riders);
    }
}
