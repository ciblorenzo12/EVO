package com.evopackage.evo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.evopackage.evo.databinding.EventDescriptionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDescription extends AppCompatActivity {

    private EventDescriptionBinding binding;
    private String eventKey;
    private TextView txtName;
    private TextView txtAddress;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_description);

        Event ev = (Event) getIntent().getSerializableExtra("Event");

        txtName = findViewById(R.id.txtEventName);
        txtAddress = findViewById(R.id.txtEventAddress);
        txtDate = findViewById(R.id.txtEventDate);
    }

    // Must set event key upon creation
    public void setEventKey(String key)
    {
        eventKey = key;
    }

    public void populateData()
    {
        FirebaseDatabase.getInstance().getReference("events").child(eventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtName.setText(snapshot.child("_name").getValue(String.class));
                txtAddress.setText(snapshot.child("_address").getValue(String.class));
                txtDate.setText(snapshot.child("_date").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}