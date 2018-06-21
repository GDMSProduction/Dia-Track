package com.Diatrack.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Diatrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.O)
public class glycosetime extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    TextView glycose;
    TextView Time;
    double glycoselevel;
    long timeenter;
    Date now = new Date();
    List<String> time = new ArrayList<>();
    List<Double> glycosel = new ArrayList<>();
    int length;
    String[] hour;
    String[] min;
    String[] ampm;
    String[] day;
    String[] month;
    String[] year;
    String currentTime;

    protected void Spinners()
    {
        final Spinner Hour = findViewById(R.id.spinnerHour);
        final Spinner Min = findViewById(R.id.spinnerMin);
        final Spinner AMPM = findViewById(R.id.spinnerAMPM);

//create a list of items for the spinner.
        hour = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
        min = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
        ampm = new String[]{"AM","PM"};
        day = new String[]{"1","2","3","4","5","6","7","8","9","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        month = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterHour = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hour);
        ArrayAdapter<String> adapterMin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, min);
        ArrayAdapter<String> adapterAMPM = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ampm);
//set the spinners adapter to the previously created one.
        Hour.setAdapter(adapterHour);
        Min.setAdapter(adapterMin);
        AMPM.setAdapter(adapterAMPM);

        Button save = (Button) findViewById(R.id.btn_Done);
        glycose = findViewById(R.id.glycoselevel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef = db.collection("UserDailyIntake").document(user.getUid() + now.getDay()+ now.getMonth() + now.getYear());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (glycose.getText().toString() != null && !"".equals(glycose.getText().toString())){
                            glycoselevel = Double.parseDouble(glycose.getText().toString());
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    document.getData();
                                    if (document.get("glycose") != null) {
                                        glycosel = (List<Double>) document.get("glycose");
                                        glycosel.add(glycoselevel);
                                    } else {
                                        glycosel.add(glycoselevel);
                                    }
                                    if (document.get("time") != null) {
                                        time = (List<String>) document.get("time");
                                        time.add(currentTime);
                                    } else {
                                        time.add(currentTime);
                                    }

                                }
                                final Map<String, Object> dailyintake = new HashMap<>();
                                dailyintake.put("glycose", glycosel);
                                dailyintake.put("time", time);
                                db.collection("UserDailyIntake").document(user.getUid() + now.getDay()+ now.getMonth() + now.getYear()).set(dailyintake).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(glycosetime.this, HomeActivity.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                });

                            }}
                        else{ Toast.makeText(getApplicationContext(),"Glycose is Empty",Toast.LENGTH_SHORT).show();}
                        currentTime = Hour.getSelectedItem().toString() + ":" + Min.getSelectedItem().toString() + ":00 " + AMPM.getSelectedItem().toString();


                    };
                });
            }});
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycosetime);
        Spinners();
    }
}
