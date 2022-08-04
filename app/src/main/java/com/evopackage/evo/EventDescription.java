package com.evopackage.evo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evopackage.evo.databinding.EventDescriptionBinding;

public class EventDescription extends AppCompatActivity {

    private EventDescriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_description);

        Event ev = (Event) getIntent().getSerializableExtra("Event");



    }

}