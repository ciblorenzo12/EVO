package com.evopackage.evo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.event_description);
        ActivityDescription description = new ActivityDescription();
        description.show(getSupportFragmentManager(), "ActivityDescription");


    }
}
