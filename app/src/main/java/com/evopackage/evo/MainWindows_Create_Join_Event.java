package com.evopackage.evo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainWindows_Create_Join_Event extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windows_create_join_event);

        ImageButton profButton = findViewById(R.id.imageButton2);
        profButton.setOnClickListener(v -> ProfilePage());

        Button evtButton = findViewById(R.id.button2);
        evtButton.setOnClickListener(v -> openDialog());
    }

    private void ProfilePage() {
        startActivity(new Intent(this, User_Profile_picture.class));
    }

    private void openDialog() {
        create_event_popup evtPopUp = new create_event_popup();
        evtPopUp.show(getSupportFragmentManager(),"EventDialog");
    }

}