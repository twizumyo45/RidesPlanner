package com.csm117.ridesplanner.ridesplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
        List<String> riders = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            riders.add(new String("rider" + i));
        }
        for (int i = 0; i < 5; i++) {
            rideGroups.add(new RideGroup("driver"+i, riders));
        }
        ArrayAdapter<RideGroup> adapter = new RideGroupAdapter(this, rideGroups);
        gridview.setAdapter(adapter);

        //gridview.setOnItemClickListener(new AdapterView.OnItemClickListener
        //// () {//
        //    public void onItemClick(AdapterView<?> parent, View v,//
        //                            int position, long id) {//
        //        Toast.makeText(getApplicationContext(),//
        //                ((TextView) v).getText(), Toast.LENGTH_SHORT).show(// );
        //    }//
        //});


    }

}
