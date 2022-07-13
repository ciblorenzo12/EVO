package com.evopackage.evo;

import android.app.usage.NetworkStats;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile_Page extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase _database = FirebaseDatabase.getInstance();

    private TextView Display, FullName, DOB;

    private ImageView ProfilePic;

    private ImageButton ProfileBack, ProfileSettings;

    private Button EventHistory;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String display = String.valueOf(_database.getReference("users/"+user.getUid()+"/_displayname"));
        //String Displayname = null;
        //if (user != null) {
          //  Displayname = user.getDisplayName();
        //}

        Display = findViewById(R.id.DisplayName);
        //Display.setText(Displayname);

        FullName = findViewById(R.id.FullName);

        DOB = findViewById(R.id.DOB);

        ProfilePic = findViewById(R.id.profilepicture);

        ProfileBack = findViewById(R.id.profileBack);

        ProfileSettings = findViewById(R.id.EditProfile);

        EventHistory = findViewById(R.id.EventHistory);

        ProfileBack.setOnClickListener(this);
        ProfileSettings.setOnClickListener(this);
        EventHistory.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.profileBack){
            startActivity(new Intent(this, MainWindows_Create_Join_Event.class));
        }
        if(view.getId() == R.id.EditProfile){
            startActivity(new Intent(this,User_Profile_picture.class));
        }
        if(view.getId() == R.id.EventHistory){
            
        }
    }
}
