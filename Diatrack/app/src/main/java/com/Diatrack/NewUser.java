package com.Diatrack;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static com.Diatrack.R.id.num_Age;
import static com.Diatrack.R.id.num_InsulinSens;
import static com.Diatrack.R.id.num_MaxBlood;
import static com.Diatrack.R.id.num_MinBlood;
import static com.Diatrack.R.id.num_Target;
import static com.Diatrack.R.id.num_Weight;
import static com.Diatrack.R.id.num_Type;
import static com.Diatrack.R.id.txt_usergender;
import static com.Diatrack.R.id.txt_Height;


public class NewUser extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> Userinfo = new HashMap<String, String>();
        Userinfo.put("gender", String.valueOf(txt_usergender));
        Userinfo.put("age", String.valueOf(num_Age));
        Userinfo.put("height", String.valueOf(txt_Height));
        Userinfo.put("weight", String.valueOf(num_Weight));
        Userinfo.put("maxblood", String.valueOf(num_MaxBlood));
        Userinfo.put("minblood", String.valueOf(num_MinBlood));
        Userinfo.put("targetblood", String.valueOf(num_Target));
        Userinfo.put("insulinsens", String.valueOf(num_InsulinSens));
        Userinfo.put("type", String.valueOf(num_Type));
        return Userinfo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
       // db.getReference();
        Button Save = findViewById(R.id.btn_SaveProfile);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.collection("UserData").document(user.getEmail())
                        .set(getHeaders());

                   // db.getReference("UserData").setValue(getHeaders());
                } catch (AuthFailureError authFailureError) {
                    authFailureError.printStackTrace();
                }
            }
        });
    }
}
