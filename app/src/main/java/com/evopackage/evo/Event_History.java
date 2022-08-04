package com.evopackage.evo;

import android.app.DownloadManager;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Event_History extends AppCompatActivity implements create_event_popup.DialogListener{

    Event _history;

    private String _name, _date, _location, _category, _creator;
    private FirebaseUser _user;


    private TextView eventTest;

    DatabaseReference eventref;
    DatabaseReference ref;

    ArrayList<String> userEvents;
    ArrayList<com.evopackage.evo.Event> eventInfo;

    RecyclerView evtHistory;
    Adapter_Recicleview adaptor;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_history);

        evtHistory = findViewById(R.id.evtHistoryRecycler);

        layoutManager = new LinearLayoutManager(this);
        evtHistory.setLayoutManager(layoutManager);

        eventInfo = new ArrayList<>();
        adaptor = new Adapter_Recicleview(eventInfo);

        //eventref = FirebaseDatabase.getInstance().getReference().child("events");
        ref = FirebaseDatabase.getInstance().getReference();//.child("users").child(FirebaseAuth.getInstance().getUid()).child("My_Events");

        userEvents = new ArrayList<>();


        //eventTest.setText("Hello");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userEvents.clear();
                DataSnapshot userRef = snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("My_Events");
                DataSnapshot eventsRef = snapshot.child("events");
                if (userRef.exists()) {
                    String eventCode;
                    for (DataSnapshot snap : userRef.getChildren()) {

                        eventCode = snap.getKey();

                        //userEvents.add(eventCode);
                        //Event event =
                    }

                }
                else{
                    eventTest.setText("No Events");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAdder) {

    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

    }
}
