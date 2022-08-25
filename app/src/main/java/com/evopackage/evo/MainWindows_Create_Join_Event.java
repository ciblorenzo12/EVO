package com.evopackage.evo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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

public class MainWindows_Create_Join_Event extends AppCompatActivity implements /*create_event_popup.DialogListener,*/ View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseDatabase search;
    private FirebaseUser user;
    private int Camera_Permission_Request = 1;
    private String[] perm_ = {Manifest.permission.CAMERA};//add permitions to this array
    private boolean permission_granted, isAttending;
    private ImageButton btn;
    private ImageButton evtBtn;
    private ImageButton qr, settings, join,back_settings;
    private ImageButton messenges_btn ;
    private CardView cv;

    int NightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private Switch swi;

    //searchView
    RecyclerView recicleviw;
    DatabaseReference refdata;
    DatabaseReference userefdata = FirebaseDatabase.getInstance().getReference().child("users");
    ArrayList<Event> _events;
    SearchView search_bar;
    Adapter_Recicleview adaptor;
    LinearLayoutManager managerL;
    Sharedprefs s;
    private Object Context;
    private Button logout;
    private ArrayList<String> peopleUIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){

            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        s = new Sharedprefs(this);

        if (s.LoadState() == true)
        {
            setTheme(R.style.darktheme);

        }
        else
        {
            setTheme(R.style.Apptheme);

        }

        super.onCreate(savedInstanceState);







        setContentView(R.layout.activity_main_windows_create_join_event);
        messenges_btn = findViewById(R.id.messages_btn);

        refdata = FirebaseDatabase.getInstance().getReference().child("events");
        recicleviw = findViewById(R.id.RecicleBar_Firebase);
        search_bar = findViewById(R.id.searchView_Main);
        managerL = new LinearLayoutManager(this);
        recicleviw.setLayoutManager(managerL);
        settings= findViewById(R.id.settings_Main_Id);

        _events = new ArrayList<>();
        peopleUIDs = new ArrayList<>();

        isAttending = false;


        adaptor = new Adapter_Recicleview(_events, new Adapter_Recicleview.OnItemClickListener() {
            @Override
            public void OnItemClick(Event ev) {
                movetodescription(ev);
            }
        });
        recicleviw.setAdapter(adaptor);
        userefdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap_user) {

                refdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            _events.clear();

                            for (DataSnapshot snap : snapshot.getChildren()) {
                                peopleUIDs.clear();
                                String Creator = snap.child("creator").getValue(String.class);
                                for(DataSnapshot guestSnap : snap.child("people").getChildren()){
                                    peopleUIDs.add(guestSnap.getKey());
                                }
                                Event evt = new Event(snap.getKey(),
                                        snap.child("name").getValue(String.class),
                                        snap.child("date").getValue(String.class),
                                        snap.child("address").getValue(String.class),
                                        snap.child("category").getValue(String.class),
                                        snap.child("creator").getValue(String.class),
                                        "String uri", "String description",
                                        snap.child("private").getValue(Boolean.class),
                                        snap.child("password").getValue(String.class));
                                _events.add(evt);
                            }
                            _events = Event.sortEventsByDate(_events, true);
                            adaptor.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String Txt) {
                Search(Txt);
                return true;
            }
        });

        qr = findViewById(R.id.qr_main_id);
        btn = findViewById(R.id.profile_picture_Main_id);
        settings = findViewById(R.id.settings_Main_Id);
        evtBtn = findViewById(R.id.calendar_id);
        join = findViewById(R.id.plus_Main_id);
        evtBtn.setOnClickListener(v -> openDialog());
        join.setOnClickListener(view -> joinPopUp());
        qr.setOnClickListener(this);
        btn.setOnClickListener(this);
        settings.setOnClickListener(this);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }

        });
    }
            public void setDayNight(int theme) {
//        SharedPreferences sp = getSharedPreferences("SP", this.MODE_PRIVATE);
//        int theme = sp.getInt("Theme", 1);

                if (theme == 0) {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            restaret();
                } else {
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            restaret();
                }
            }

            private void restaret() {
                finish();
                Intent i = new Intent(getApplicationContext(), MainWindows_Create_Join_Event.class);
                startActivity(i);

            }


            private void joinPopUp() {
                Join_Event join = new Join_Event();
                join.show(getSupportFragmentManager(), "Join Dialog");
            }

    public void showPopup(View v)
    {
        PopupMenu p = new PopupMenu(this, v);
        p.setOnMenuItemClickListener(this::onMenuItemClick);
        p.inflate(R.menu.menu1);
        p.show();
    }

            private void movetodescription(Event ev) {
                Intent i = new Intent(this, EventDescription.class);
                i.putExtra("Event", ev);
//        if(ev.isEventPrivate() == true)// && isAttending == false)
//        {
//            joinPopUp();
//        }
                startActivity(i);
            }

            private void Search(String txt) {
                ArrayList<Event> events_search = new ArrayList<>();
                for (Event eventObj : _events) {

                    if (eventObj.GetName().toLowerCase().contains(txt.toLowerCase())) {
                        events_search.add(eventObj);

                        Adapter_Recicleview recicleview = new Adapter_Recicleview(events_search, new Adapter_Recicleview.OnItemClickListener() {
                            @Override
                            public void OnItemClick(Event ev) {

                            }
                        });
                        recicleviw.setAdapter(recicleview);
                    }
                }
            }

            private void openDialog() {
                create_event_popup evtPopUp = new create_event_popup();
                evtPopUp.show(getSupportFragmentManager(), "EventDialog");
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == btn.getId()) {
                    Intent car = new Intent(this, Profile_Page.class);
                    startActivity(car);
                }
                if (v.getId() == qr.getId()) {
                    RequestCameraPermission();
                }
                if (v.getId() == R.id.settings_Main_Id) {
                    Toast.makeText(this, "jjjjjjjjjj", Toast.LENGTH_SHORT).show();
                    Intent car = new Intent(this, Event_Page.class);
                    startActivity(car);
                }
            }


            private void RequestCameraPermission() {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    new AlertDialog.Builder(this).setTitle("Permission need it").setMessage("To be able to scan QR code \n you will need the permissions ")
                            .setPositiveButton("Allow", (dialog, which) -> {
                                Intent car = new Intent(MainWindows_Create_Join_Event.this, Qr_code_scanner.class);
                                startActivity(car);
                            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
                }
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


                    Intent car = new Intent(this, Qr_code_scanner.class);
                    startActivity(car);


                }
            }


            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.night11:
                        // getDelegate().applyDayNight();
                        setDayNight(0);
                        //  getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                        restartApp();
                        return true;


                    case R.id.day:
                        //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        setDayNight(1);
                        return true;

                }

                return false;
            }

            private void restartApp() {
                finish();
                Intent i = new Intent(getApplicationContext(), MainWindows_Create_Join_Event.class);
                startActivity(i);

            }

        }


