package com.Diatrack.Classes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
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
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    Date now = new Date();
    CalendarView calendar;
    ListView Daily;
    List<Date> time = new ArrayList<>();
    List<Double> glycose = new ArrayList<>();
    List<String> DailyList = new ArrayList<String>();
    double averageGlycose;
    double highGlycose;
    double lowGlycose;
    long protein;
    long fats;
    long calories;
    long carbs;
    long insulin;
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Daily = findViewById(R.id.DailyList);
        calendar = findViewById(R.id.calendarView);
       // setSupportActionBar(toolbar);

       db.collection("UserDailyIntake")
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d("TAG", document.getId() + " => " + document.getData());
                               if (document.getId().equals(user.getUid() + now.getDate()))
                               {
                                 glycose = (List<Double>) document.get("glycose");
                                 time = (List<Date>) document.get("time");
                                 calories = Long.parseLong(document.get("calories").toString());
                                   protein = Long.parseLong(document.get("protein").toString());
                                   fats = Long.parseLong(document.get("fats").toString());
                                   carbs = Long.parseLong(document.get("carbs").toString());
                                   insulin = Long.parseLong(document.get("insluin").toString());
                                 for (int i=0; i<glycose.size(); i++) {
                                     averageGlycose = averageGlycose + glycose.get(i);
                                     if (highGlycose >= glycose.get(i))
                                     {
                                         highGlycose = glycose.get(i);
                                     }
                                     if (lowGlycose <= glycose.get(i))
                                     {
                                         lowGlycose = glycose.get(i);
                                     }
                                 }
                                   averageGlycose = averageGlycose / glycose.size();
                                   DailyList.add(Long.toString(calories));
                                   DailyList.add(Long.toString(protein));
                                   DailyList.add(Long.toString(fats));
                                   DailyList.add(Long.toString(carbs));
                                   DailyList.add(Long.toString(insulin));
                                   DailyList.add(Double.toString(averageGlycose));
                                   DailyList.add(Double.toString(highGlycose));
                                   DailyList.add(Double.toString(lowGlycose));
                                   PopulateList();
                               }

                           }
                       } else {
                           Log.d("TAG", "Error getting documents: ", task.getException());
                       }
                   }
               });
}
public void PopulateList()
{
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , DailyList );

    Daily.setAdapter(arrayAdapter);
}
}
