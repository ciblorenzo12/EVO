package com.evopackage.evo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.evopackage.evo.databinding.EventDescriptionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private TextView txtName, txtAddress, txtDate, txtCategory, txtDescription, txtCreator, txtAttendees;

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
        txtAttendees = findViewById(R.id.txtAttendees);

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
        FirebaseDatabase.getInstance().getReference().child("events").child(ev.GetKey()).child("assistance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtAttendees.setText(snapshot.getChildrenCount() + " Attendees");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}