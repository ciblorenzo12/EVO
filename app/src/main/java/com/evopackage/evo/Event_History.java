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


public class Event_History extends AppCompatActivity implements View.OnClickListener{

    Event _history;

    private String _name, _date, _location, _category, _creator;
    private FirebaseUser _user;

    private List<String> eventList = new ArrayList<String>();

    private TextView eventTest;

    @Override
    public void onClick(View view) {

        eventTest = findViewById(R.id.eventTest);

        setContentView(R.layout.event_history);

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // For loop is to count up to the number of events the user joined or attended

                //Iterator eventIter = (Iterator) snapshot.child("users").child(_user.getUid()).child("_events").getChildren();
                //Iterator iter = snapshot.child("users").child(_user.getUid()).child("Events").getChildren();
                for (DataSnapshot eventKey: snapshot.child("users").child(_user.getUid()).child("Events").getChildren())
                {
                    //DatabaseReference event = FirebaseDatabase.getInstance().getReference("events").child(eventKey.getKey());
                    DataSnapshot event = snapshot.child("events").child(eventKey.getKey());
                    Log.i("~!!!!!!!!!!!!!!!", event.child("_name").getValue().toString());
                    //eventTest.setText(event.child("_name").getValue(String.class));
                    eventTest.setText("hi");
                }


//                Iterator eventIter = (Iterator) snapshot.getChildren();
//
//                eventTest.setText(eventIter.toString());
//                while(eventIter.hasNext()){
//                    Log.i("TAG", "onDataChange: " + eventIter.next());
//                }
                //for(int i =0 ; i < _attendedEvents; i++)
                //eventList[i] = snapshot.child(_user.getUid()).child("Events").toString();
                //Store all the info from fire base into the string variable to be made into an event for history display

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            FirebaseDatabase.getInstance().getReference("events").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    //_name = datasnapshot.child(_eventID).child("_name").toString();
                    //_date = datasnapshot.child(_eventID).child("_date").toString();
                    //_location = datasnapshot.child(_eventID).child("_address").toString();
                    //_category = datasnapshot.child(_eventID).child("_category").toString();
                    //_creator = datasnapshot.child(_eventID).child("_creator").toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
}
