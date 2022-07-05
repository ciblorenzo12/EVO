package com.evopackage.evo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class create_event_popup extends AppCompatActivity {
    private EditText txtName;
    private EditText txtDate;
    private EditText txtAddress;
    private Button btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dialog);
        txtName = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtAddress = findViewById(R.id.txtLocation);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(v -> {
            Event event = new Event(txtName.toString(), txtDate.toString(), txtAddress.toString(), "null category");
            FirebaseDatabase.getInstance().getReference("events").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                    .getUid()).setValue(event);
        });

    }


}
