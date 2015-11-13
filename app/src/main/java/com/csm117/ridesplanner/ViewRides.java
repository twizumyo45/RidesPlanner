package com.csm117.ridesplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.csm117.ridesplanner.ridesplanner.R;
import com.csm117.ridesplanner.persons.Driver;
import com.csm117.ridesplanner.persons.Person;
import com.csm117.ridesplanner.persons.Rider;

import java.util.ArrayList;
import java.util.List;

public class ViewRides extends AppCompatActivity {

    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rides);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gridView);

        List<RideGroup> rideGroups = new ArrayList<RideGroup>();
        List<List<Person>> riders = new ArrayList<List<Person>>();
        for (int k = 0; k < 5; k++) {
            riders.add(new ArrayList<Person>());
            for (int i = 0; i < 4; i++) {
                riders.get(k).add(new Rider("rider" + (k * 4 + i)));
            }
        }
        for (int i = 0; i < 5; i++) {
            Person driver = new Driver("driver" + i);
            rideGroups.add(new RideGroup(driver, riders.get(i)));
        }
        ArrayAdapter<RideGroup> adapter = new RideGroupAdapter(this, rideGroups);
        gridview.setAdapter(adapter);

    }

}
