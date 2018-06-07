package com.Diatrack.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Diatrack.Classes.UserProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.Diatrack.R.id.txt_Height;
import static com.Diatrack.R.id.txt_InsulinSen;
import static com.Diatrack.R.id.txt_MinBloodSugar;
import static com.Diatrack.R.id.txt_Username;
import static com.Diatrack.R.id.ProfilePicture;
import static com.Diatrack.R.id.txt_Weight;

import com.Diatrack.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.URL;

public class Profile extends AppCompatActivity {
    LoginActivity loginActivity = new LoginActivity();
    TextView usersname;
    String userImage;
    ImageView userImageView;
    TextView InsulinSens;
    TextView maxBlood;
    TextView minBlood;
    TextView Weight;
    TextView Height;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
if (user != null && user.getEmail() != null) {
    checkforProfile();
}
       // usersname.setText(loginActivity.UsersName);
       // userImage = loginActivity.UsersPhoto;
            try {
                InputStream is = (InputStream) new URL(userImage).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                userImageView.setImageDrawable(d);
            } catch (Exception e) {
            }
        Button newUser = (Button) findViewById(R.id.btn_newUser);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, NewUser.class));
            }
        });
        }

    public void checkforProfile()
    {
        usersname = findViewById(txt_Username);
        userImageView =findViewById(ProfilePicture);
        Height = findViewById(txt_Height);
        InsulinSens = findViewById(txt_InsulinSen);
        maxBlood = findViewById(txt_MinBloodSugar);
        minBlood = findViewById(txt_MinBloodSugar);
        Weight = findViewById(txt_Weight);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("UserData").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                UserProfileData userProfileData = documentSnapshot.toObject(UserProfileData.class);
//                Height.setText((userProfileData.getHeight()));
//                InsulinSens.setText(Float.toString(userProfileData.getInsulinSense()));
//                maxBlood.setText(Integer.toString(userProfileData.getMaxBlood()));
//                minBlood.setText(Integer.toString(userProfileData.getMinBlood()));
//                Weight.setText(Float.toString( userProfileData.getWeight()));


            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                      //  uGender.setText((CharSequence) document.get("age"));
                      //  Weight.setText((Integer) document.get("weight"));
                      //  Height.setText((Integer) document.get("height"));
                      //  uGender.setText((CharSequence) document.get("gender"));
                     //   InsulinSens.setText((Integer) document.get("insulinsens"));
                     //   maxBlood.setText((Integer) document.get("maxblood"));
                     //   minBlood.setText((Integer) document.get("minblood"));
                      //  uTargetBlood.setText((Integer) document.get("targetblood"));
                       // uType.setText((Integer) document.get("type"));
                       // document.get("age");
                       // document.get("weight");
                       // document.get("height");
                       // document.get("gender");
                       // document.get("insulinsens");
                       // document.get("maxblood");
                       // document.get("minblood");
                       // document.get("targetblood");
                       // document.get("type");
                    }
                }

            }
        });

            }
    }



