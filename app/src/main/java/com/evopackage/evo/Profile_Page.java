package com.evopackage.evo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Profile_Page extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth _authetication;
    private FirebaseDatabase _Database = FirebaseDatabase.getInstance();

    private TextView Display, FullName, DOB;

    private ImageView ProfilePic;

    private ImageButton ProfileBack;

    private Button EventHistory;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        FirebaseDatabase Ref = null;
        FirebaseUser user = _authetication.getCurrentUser();

        Display = findViewById(R.id.DisplayName);
        Display.setText((CharSequence) _Database.getReference("users/"+user.getUid()+"/_displayName"));
        FullName = findViewById(R.id.FullName);
        //FullName.setText();
        DOB = findViewById(R.id.DOB);

        ProfilePic = findViewById(R.id.profilepicture);

        ProfileBack = findViewById(R.id.profileBack);

        EventHistory = findViewById(R.id.EventHistory);
    }
    @Override
    public void onClick(View view) {

    }
}
