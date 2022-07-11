package com.evopackage.evo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class User_Profile_picture extends AppCompatActivity implements View.OnClickListener {
    private TextView _userID;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_setting);

        User_information info;
        //button.findViewById(R.id.textview1000);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_back3) {
            startActivity(new Intent(this, MainWindows_Create_Join_Event.class));
        }
        //save changes
        else if (view.getId() == R.id.btn_Evt_Att) {

        }
        //change profile pic
        else if (view.getId() == R.id.imageButton) {

        }
    }
}