package com.evopackage.evo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.evopackage.evo.databinding.EventDescriptionBinding;

public class EventDescription extends AppCompatActivity {

    private EventDescriptionBinding binding;
    private Event ev;
    private TextView txtName;
    private TextView txtAddress;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_description);

        ev = (Event) getIntent().getSerializableExtra("Event");

        txtName = findViewById(R.id.txtEventName);
        txtAddress = findViewById(R.id.txtEventAddress);
        txtDate = findViewById(R.id.txtEventDate);

        populateData();
    }

    public void populateData()
    {
        txtName.setText(ev.GetName());
        txtAddress.setText(ev.GetLocation());
        txtDate.setText(ev.GetLocation());
    }
}