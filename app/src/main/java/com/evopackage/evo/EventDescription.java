package com.evopackage.evo;

import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventDescription extends AppCompatActivity {

    private Event ev;
   private static String ClickedEvent;
    private Button setUp;
    private TextView txtName, txtAddress, txtDate, txtCategory, txtDescription, txtCreator, txtAttendees;
    private RecyclerView rv;
    private EventActivitiesAdapter adapter;
    private ArrayList<Activity> list;
    private DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    //private EventActivitiesAdapter adapter;
    private List<Activity> list;
    private LinearLayoutManager managerL;
    private DatabaseReference refdata;
    private Button setUp;

    private RecyclerView rv2;
    private ArrayList<Profile_Page> list2;

   // private EventActivitiesAdapter.OnItemClickListener listener;

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
        ClickedEvent = ev.GetKey();

            setUp = findViewById(R.id.button);
            if(!firebaseUsers.getKey().equals(ev.GetCreator())) {
                setUp.setVisibility(View.GONE);
        }
        setUp.setOnClickListener(v -> openAct());
        populateData();

        managerL = new LinearLayoutManager(this);
        refdata = FirebaseDatabase.getInstance().getReference().child("events").child(ev.GetKey()).child("activities");
        rv = findViewById(R.id.rvact);
        rv.setLayoutManager(managerL);

        list = new ArrayList<Activity>();

        adapter = new EventActivitiesAdapter(list);

        rv.setAdapter(adapter);

        refdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list.clear();

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        Activity act = new Activity(
                                snap.getKey(),
                                snap.child("time").getValue(String.class),
                                snap.child("location").getValue(String.class));

                        list.add(act);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void openAct() {
        create_activity_popup actDialog = new create_activity_popup();
        actDialog.show(getSupportFragmentManager(), "ActivityDialog");
        actDialog.setEventKey(ev.GetKey());
    }

    public void populateData()
    {
        txtName.setText(ev.GetName());
        txtAddress.setText(ev.GetLocation());
        txtDate.setText(ev.GetDate());
        txtCategory.setText(ev.GetCategory());
        txtDescription.setText(ev.GetDescription());
        txtCreator.setText(ev.GetCreator());

        FirebaseDatabase.getInstance().getReference().child("events").child(ev.GetKey()).child("people").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtAttendees.setText(" Attendees (" + snapshot.getChildrenCount() + " have already joined the Event)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static String  GetClickedEvent(){

        return ClickedEvent;
    }
}