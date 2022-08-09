package com.evopackage.evo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.evopackage.evo.databinding.EventDescriptionBinding;

public class EventDescription extends AppCompatActivity {

    private EventDescriptionBinding binding;
    private Event ev;
    private TextView txtName;
    private TextView txtAddress;
    private TextView txtDate;
    private TextView txtCategory;
    private TextView txtDescription;
    private TextView txtCreator;
    private Button setUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_description);

        ev = (Event) getIntent().getSerializableExtra("Event");

        txtName = findViewById(R.id.txtEventName);
        txtAddress = findViewById(R.id.txtEventAddress);
        txtDate = findViewById(R.id.txtEventDate);
        txtCategory = findViewById(R.id.category4item);
        txtDescription = findViewById(R.id.descriptionev);
        txtCreator = findViewById(R.id.eventcreatorr);
        setUp = findViewById(R.id.button);
        setUp.setOnClickListener(v -> openAct());
        populateData();
    }

    private void openAct() {
        create_activity_popup actDialog = new create_activity_popup();
        actDialog.show(getSupportFragmentManager(), "ActivityDialog");
    }

    public void populateData()
    {
        txtName.setText(ev.GetName());
        txtAddress.setText(ev.GetLocation());
        txtDate.setText(ev.GetDate());
        txtCategory.setText(ev.GetCategory());
        txtDescription.setText(ev.GetDescription());
        txtCreator.setText(ev.GetCreator());
    }
}