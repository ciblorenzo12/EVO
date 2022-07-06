package com.evopackage.evo;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainWindows_Create_Join_Event extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windows_create_join_event);

        Button evtButton = findViewById(R.id.button2);
        evtButton.setOnClickListener(v -> openDialog());
    }

    private void openDialog() {
        create_event_popup evtPopUp = new create_event_popup();
        evtPopUp.show(getSupportFragmentManager(),"EventDialog");
    }

}