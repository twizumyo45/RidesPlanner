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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csm117.ridesplanner.ridesplanner.persons.Driver;
import com.csm117.ridesplanner.ridesplanner.persons.Person;
import com.csm117.ridesplanner.ridesplanner.persons.Rider;

import java.util.ArrayList;
import java.util.List;

public class ViewPersonsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
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
        ListView listview = (ListView) findViewById(R.id.listView);

        List<Person> persons = new ArrayList<Person>();

        for (int k = 0; k < 5; k++) {
            persons.add(new Driver("driver"+k));
        }
        for (int k = 0; k < 20; k++) {
            persons.add(new Rider("rider"+k));
        }

        ArrayAdapter<Person> adapter = new PersonsListAdapter(this, persons);
        listview.setAdapter(adapter);

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
