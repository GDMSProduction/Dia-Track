package com.Diatrack.Classes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;

import com.Diatrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Day extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    Date now = new Date();
    CalendarView calendar;
    ListView Daily;
    List<Date> time = new ArrayList<>();
    List<Double> glycose = new ArrayList<>();
    List<String> DailyList = new ArrayList<String>();
    // LocalDate currentDate = LocalDate.now();
    // DayOfWeek dow = currentDate.getDayOfWeek();
    // int day = currentDate.getDayOfMonth();
    // int doy = currentDate.getDayOfYear();
    // Month month = currentDate.getMonth();
    // int year = currentDate.getYear();
    double averageGlycose;
    double highGlycose;
    double lowGlycose;
    double protein;
    double fats;
    double calories;
    double carbs;
    double insulin;
    Calendar cal = Calendar.getInstance();
    int day;
    int month;
    int year;
    DatePicker pickerDate;
    String monthString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Daily = findViewById(R.id.ListView);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        switch (month + 1) {
            case 1:
                monthString = "JANUARY";
                break;
            case 2:
                monthString = "FEBRUARY";
                break;
            case 3:
                monthString = "MARCH";
                break;
            case 4:
                monthString = "APRIL";
                break;
            case 5:
                monthString = "MAY";
                break;
            case 6:
                monthString = "JUNE";
                break;
            case 7:
                monthString = "JULY";
                break;
            case 8:
                monthString = "AUGUST";
                break;
            case 9:
                monthString = "SEPTEMBER";
                break;
            case 10:
                monthString = "OCTOBER";
                break;
            case 11:
                monthString = "NOVEMBER";
                break;
            case 12:
                monthString = "DECEMBER";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        // setSupportActionBar(toolbar);

        db.collection("UserDailyIntake")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                String test = document.getId();
                                String tes1 = user.getUid() + day + monthString + year;
                                if (document.getId().equals(user.getUid() + day + monthString + year)) {
                                    glycose = (List<Double>) document.get("glycose");
                                    time = (List<Date>) document.get("time");
                                    calories = (double) document.get("calories");
                                    protein = (double) document.get("protein");
                                    fats = (double) document.get("fats");
                                    carbs = (double) document.get("carbs");
                                    insulin = (double) document.get("insulin");
                                    for (int i = 0; i < glycose.size(); i++) {
                                        averageGlycose = averageGlycose + glycose.get(i);
                                        if (highGlycose < glycose.get(i)) {
                                            highGlycose = glycose.get(i);
                                            lowGlycose = glycose.get(i);
                                        }

                                        if (lowGlycose > glycose.get(i)) {
                                            lowGlycose = glycose.get(i);
                                        }
                                    }
                                    averageGlycose = averageGlycose / glycose.size();
                                    DailyList.add("Calores: " + Double.toString(calories));
                                    DailyList.add("Protein: " + Double.toString(protein));
                                    DailyList.add("Fats: " + Double.toString(fats));
                                    DailyList.add("Carbohydrates: " + Double.toString(carbs));
                                    DailyList.add("Insulin intake: " + Double.toString(insulin));
                                    DailyList.add("Average Glycose Level: " + Double.toString(averageGlycose));
                                    DailyList.add("Highest Glycose Level: " + Double.toString(highGlycose));
                                    DailyList.add("Lowest Glycose Level: " + Double.toString(lowGlycose));
                                    PopulateList();
                                }

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void PopulateList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DailyList);

        Daily.setAdapter(arrayAdapter);
    }

}
