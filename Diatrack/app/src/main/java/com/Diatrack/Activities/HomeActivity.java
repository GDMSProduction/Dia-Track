package com.Diatrack.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Diatrack.Classes.Day;
import com.Diatrack.Classes.History;
import com.Diatrack.Classes.UserHistory;
import com.Diatrack.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("UserData").document(user.getUid());
    //Private Varaibles
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    TextView name;
    TextView email;
    int day6I;
    int day5I;
    int day4I;
    int day3I;
    int day2I;
    int day1I;
    int day0I;

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

        View navHeader = navigationView.getHeaderView(0);
        name = navHeader.findViewById(R.id.name);
        email = navHeader.findViewById(R.id.email);

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null) {
            name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }

       if( FirebaseAuth.getInstance().getCurrentUser().getEmail() != null ) {
           email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
       }
    }

    public void CreateMainMenu() {
        mDrawerLayout = findViewById(com.Diatrack.R.id.menuDrawerLayout);

        navigationView = findViewById(com.Diatrack.R.id.menuNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void CreateGraph()
    {
        final UserHistory[] day6 = new UserHistory[1];
        UserHistory day5;
        UserHistory day4;
        UserHistory day3;
        UserHistory day2;
        UserHistory day1;
        UserHistory day0;

        final Date now = new Date();
        GraphView graph = (GraphView) findViewById(com.Diatrack.R.id.graph);

        db.collection("UserDailyIntake")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                if (document.getId() == user.getUid() + (now.getDay() - 6))
                                {
                                   UserHistory day6 = document.toObject(UserHistory.class);
                                   day6I = day6.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay() - 5))
                                {
                                    UserHistory day5 = document.toObject(UserHistory.class);
                                    day5I = day5.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay() - 4))
                                {
                                    UserHistory day4 = document.toObject(UserHistory.class);
                                    day4I = day4.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay() - 3))
                                {
                                    UserHistory day3 = document.toObject(UserHistory.class);
                                    day5I = day3.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay() - 2))
                                {
                                    UserHistory day2 = document.toObject(UserHistory.class);
                                    day5I = day2.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay() - 1))
                                {
                                    UserHistory day1 = document.toObject(UserHistory.class);
                                    day1I = day1.getInsulin();
                                }
                                else if (document.getId() == user.getUid() + (now.getDay()))
                                {
                                    UserHistory day0 = document.toObject(UserHistory.class);
                                    day0I = day0.getInsulin();
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(now.getDate() - 6, day6I),
                new DataPoint(now.getDate() - 5, day5I),
                new DataPoint(now.getDate() - 4, day4I),
                new DataPoint(now.getDate() - 3, day3I),
                new DataPoint(now.getDate() - 2, day2I),
                new DataPoint(now.getDate() - 1, day1I),
                new DataPoint(now.getDate(), day0I),
        });
        graph.addSeries(series);
    }
    @Override
    public void onBackPressed() {

    }


}
