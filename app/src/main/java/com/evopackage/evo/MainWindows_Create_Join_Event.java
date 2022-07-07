package com.evopackage.evo;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainWindows_Create_Join_Event extends AppCompatActivity implements create_event_popup.DialogListener {


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
    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr) {
        Event event = new Event(_evtName,_evtDate,_evtAddr, null);
        FirebaseDatabase.getInstance().getReference("events").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
          .getUid()).setValue(event);
    }
}