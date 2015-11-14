package com.csm117.ridesplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csm117.ridesplanner.persons.Driver;
import com.csm117.ridesplanner.persons.Rider;
import com.csm117.ridesplanner.ridesplanner.R;
import com.csm117.ridesplanner.persons.Person;

import java.util.ArrayList;
import java.util.List;

public class ViewPersonsList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_persons_list_navigation_activity);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_rides) {
            // Handle the camera action
            Intent nextScreen = new Intent(getApplicationContext(),  ViewRides.class);
            startActivity(nextScreen);
        } else if (id == R.id.nav_view_persons_list) {
            Intent nextScreen = new Intent(getApplicationContext(),  ViewPersonsList.class);
            startActivity(nextScreen);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
