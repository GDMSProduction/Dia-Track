package com.example.marshall.diatrack;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.marshall.diatrack.dummy.Day;
import com.example.marshall.diatrack.dummy.History;
import com.example.marshall.diatrack.dummy.SettingsActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //Private Varaibles
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ConfigureAndInstall();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here.
        mDrawerLayout.closeDrawers();

        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_Day_Layout) {
            startActivity(new Intent(this,Day.class));
        } else if (id == R.id.nav_History_Layout) {
            startActivity(new Intent(this,History.class));
        } else if (id == R.id.nav_Settings_Layout) {
            startActivity(new Intent(this,SettingsActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ConfigureAndInstall()
    {
        CreateGraph();
        CreateMainMenu();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Button meal = (Button) findViewById(R.id.bt_Meal);
        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MealOptionsActivity.class));
            }
        });

        Button glycose = (Button) findViewById(R.id.bt_Glycose);

        glycose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GlycoseActivity.class));
            }
        });
    }

    public void CreateMainMenu() {
        mDrawerLayout = findViewById(R.id.menuDrawerLayout);

        NavigationView navigationView = findViewById(R.id.menuNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void CreateGraph()
    {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)

        });
        graph.addSeries(series);
    }
    @Override
    public void onBackPressed() {

    }
}
