package com.evopackage.evo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Event_History extends AppCompatActivity implements create_event_popup.DialogListener, View.OnClickListener{

    Event _history;

    private String _name, _date, _location, _category, _creator;
    private FirebaseUser _user;

    private ImageButton backArrow;

    private TextView eventTest;

    DatabaseReference eventref;
    DatabaseReference ref;
    StorageReference picRef;

    ArrayList<String> userEvents;
    ArrayList<Event> eventInfo;

    RecyclerView evtHistory;
    Adapter_Recicleview adaptor;
    LinearLayoutManager layoutManager;

    Event event;

    Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_history);

        evtHistory = findViewById(R.id.evtHistoryRecycler);

        layoutManager = new LinearLayoutManager(this);
        evtHistory.setLayoutManager(layoutManager);

        eventInfo = new ArrayList<>();
        adaptor = new Adapter_Recicleview(eventInfo, new Adapter_Recicleview.OnItemClickListener() {
            @Override
            public void OnItemClick(Event ev) {

            }
        });

        evtHistory.setAdapter(adaptor);
        eventref = FirebaseDatabase.getInstance().getReference().child("events");
        ref = FirebaseDatabase.getInstance().getReference();//.child("users").child(FirebaseAuth.getInstance().getUid()).child("My_Events");

        userEvents = new ArrayList<>();

        backArrow = findViewById(R.id.eventhistoryback);

        picRef = FirebaseStorage.getInstance().getReference();

        //eventTest.setText("Hello");



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userEvents.clear();
                DataSnapshot userRef = snapshot.child("users").child(FirebaseAuth.getInstance().getUid());
                DataSnapshot eventsRef = snapshot.child("events");
                if (userRef.exists()) {
                    String eventCode;
                    for (DataSnapshot snap : userRef.child("user-events").getChildren()) {



//                        StorageReference imageRef = picRef.child("user_profile_pics/"+eventsRef.child(snap.getKey()).child("creator")+"/profile.jpg");
//                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                picUri = uri;
//                            }
//                        });

                        //userEvents.add(eventCode);
                        //event = new Event(eventCode);

                        //event = new Event(snap.getKey());
                        eventInfo.add(event);
                    }

                    adaptor.notifyDataSetChanged();
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
    public void onClick(View view) {
        if(view.getId() == backArrow.getId()){
            Intent intent = new Intent(this, Profile_Page.class);
            startActivity(intent);
        }
    }
}
