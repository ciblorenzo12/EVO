package com.evopackage.evo;

import android.app.DownloadManager;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class Profile_Page extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase _database;

    private DatabaseReference _Reference;

    StorageReference pictureReference;

    private TextView Display, FullName, DOB;

    private ImageView ProfilePic;

    private ImageButton ProfileBack, ProfileSettings;

    private Button EventHistory;

    private static final String USER = "users";

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Intent intent = getIntent();
        //email =


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        pictureReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = pictureReference.child("user_profile_pics/"+user.getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ProfilePic);
            }
        });
        //String display = String.valueOf(_database.getReference("users/"+user.getUid()+"/_displayname"));
        //String Displayname = null;
        //if (user != null) {
          //  Displayname = user.getDisplayName();
        //}

        String userID = user.getUid();



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



        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Display.setText(snapshot.child(userID).child("_displayName").getValue(String.class));
                FullName.setText(snapshot.child(userID).child("_firstname").getValue(String.class) + " " + snapshot.child(userID).child("_lastname").getValue(String.class));
                DOB.setText(snapshot.child(userID).child("_dob").getValue(String.class));
                //Picasso.get().load(pictureReference.).into(ProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //_Reference.addValueEventListener(new ValueEventListener() {
            //@Override
            //public void onDataChange(@NonNull DataSnapshot snapshot) {
                //User_information userInfo = snapshot.child("_firstname").getValue(User_information.class);

                //FullName.setText(userInfo.GetFullName());
                //for(DataSnapshot ds : snapshot.getChildren()){
                //    if(ds.getValue().equals(userID)){
                //        FullName.setText(ds.child("_firstname").getValue(String.class));
                //    }
                //}
            //}

            //@Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});


        //Query mQueryRef = _database.getReference("users/"+userID);


        //mQueryRef.addValueEventListener(new ValueEventListener() {
          //  @Override
            //public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  User_information userInfo = snapshot.getValue(User_information.class);
                //String _fullName = userInfo.GetFullName();
            //}

           // @Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});

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
            startActivity(new Intent(this,Event_History.class));
        }
    }
}
