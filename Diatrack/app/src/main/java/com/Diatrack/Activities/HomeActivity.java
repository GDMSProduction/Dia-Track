package com.Diatrack.Activities;

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

import com.Diatrack.Classes.Day;
import com.Diatrack.Classes.History;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
        setContentView(com.Diatrack.R.layout.activity_home);
        ConfigureAndInstall();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here.
        mDrawerLayout.closeDrawers();

        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == com.Diatrack.R.id.nav_Day_Layout) {
            startActivity(new Intent(this,Day.class));
        } else if (id == com.Diatrack.R.id.nav_History_Layout) {
            startActivity(new Intent(this,History.class));
        } else if (id == com.Diatrack.R.id.nav_Settings_Layout) {
            startActivity(new Intent(this,Profile.class));
        } else if (id == com.Diatrack.R.id.nav_share) {

        } else if (id == com.Diatrack.R.id.nav_send) {

        }
        else if (id == com.Diatrack.R.id.nav_logout) {

            //Firebase Logout
            FirebaseAuth.getInstance().signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("1036090741062-jnpj5tnml0u84vchfn4ud247gn6sqo06.apps.googleusercontent.com")
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            // Google sign out
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        }
                    });
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

        android.support.v7.widget.Toolbar toolbar = findViewById(com.Diatrack.R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(com.Diatrack.R.drawable.ic_menu);

        Button meal = (Button) findViewById(com.Diatrack.R.id.bt_Meal);
        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MealOptionsActivity.class));
            }
        });

        Button glycose = (Button) findViewById(com.Diatrack.R.id.bt_Glycose);

        glycose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GlycoseActivity.class));
            }
        });
    }

    public void CreateMainMenu() {
        mDrawerLayout = findViewById(com.Diatrack.R.id.menuDrawerLayout);

        NavigationView navigationView = findViewById(com.Diatrack.R.id.menuNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void CreateGraph()
    {

        GraphView graph = (GraphView) findViewById(com.Diatrack.R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(6, 150),
                new DataPoint(8, 125),
                new DataPoint(10, 320),
                new DataPoint(12, 230),
                new DataPoint(14.30, 176)


        });
        graph.addSeries(series);
    }
    @Override
    public void onBackPressed() {

    }


}
