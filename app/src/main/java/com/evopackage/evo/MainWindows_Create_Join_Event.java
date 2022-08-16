package com.evopackage.evo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainWindows_Create_Join_Event extends AppCompatActivity implements create_event_popup.DialogListener, View.OnClickListener {
    //messanging
    Adapter_Messangers adaptor_m;
    RecyclerView reciclemsg;
    ArrayList<main_messenges> messenges_array;
    private EditText mess;
    FloatingActionButton send_btn;

    //firebase
    FirebaseUser user;
    FirebaseAuth Auth_;
    private DatabaseReference   refMessanging;
    //searchView
    RecyclerView recicleviw;
    DatabaseReference refdata;
    DatabaseReference userefdata = FirebaseDatabase.getInstance().getReference().child("users");
    ArrayList<Event> _events;
    SearchView search_bar;
    Adapter_Recicleview adaptor;
    LinearLayoutManager managerL;


    private ImageButton qr, settings, evtBtn, btn, messenges_btn, back_settings, join;
    private Button logout;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_messenges_);
        Auth_ = FirebaseAuth.getInstance();
        user = Auth_.getCurrentUser();
        send_btn = findViewById(R.id.sent_input);
        refMessanging =  FirebaseDatabase.getInstance().getReference();
        mess = findViewById(R.id.Mensage_in);

        setContentView(R.layout.setting_page);
        //messanging




        logout = findViewById(R.id.logout);
        back_settings = findViewById(R.id.SettingsBack);
        setContentView(R.layout.activity_main_windows_create_join_event);
        messenges_btn = findViewById(R.id.messages_btn);

        refdata = FirebaseDatabase.getInstance().getReference().child("events");
        recicleviw = findViewById(R.id.RecicleBar_Firebase);
        search_bar = findViewById(R.id.searchView_Main);
        managerL = new LinearLayoutManager(this);
        recicleviw.setLayoutManager(managerL);
        _events = new ArrayList<>();

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

                                Event evt = new Event(snap.getKey(),
                                        snap.child("name").getValue(String.class),
                                        snap.child("date").getValue(String.class),
                                        snap.child("address").getValue(String.class),
                                        snap.child("category").getValue(String.class),
                                        snap.child("creator").getValue(String.class),
                                        "String uri", "String description");
                                _events.add(evt);
                            }
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


    }
















    private void joinPopUp() {
        create_event_popup evtPopUp = new create_event_popup();
        evtPopUp.show(getSupportFragmentManager(), "Join Dialog");
    }

    private void movetodescription(Event ev) {
        Intent i = new Intent(this, EventDescription.class);
        i.putExtra("Event", ev);
        startActivity(i);
    }

    //Search
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


    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        if (v.getId() == btn.getId()) {

            Intent _intent = new Intent(this, Profile_Page.class);
            startActivity(_intent);

        }
        if (v.getId() == qr.getId()) {


            RequestCameraPermission();
        }
        if (v.getId() == settings.getId()) {
            setContentView(R.layout.setting_page);
            logout.setOnClickListener(MainWindows_Create_Join_Event.this);
        }
        if (v.getId() == logout.getId()) {
            //signout();
        }
        if (v.getId() == settings.getId()) {

            setContentView(R.layout.setting_page);
            logout.setOnClickListener(MainWindows_Create_Join_Event.this);
            back_settings.setOnClickListener(MainWindows_Create_Join_Event.this);

        }
        if (v.getId() == logout.getId()) {

            signout();

        }
        if (v.getId() == back_settings.getId()) {

            Intent _intent = new Intent(this, MainWindows_Create_Join_Event.class);
            startActivity(_intent);
            finish();
        }
        if (v.getId() == messenges_btn.getId()) {

            setContentView(R.layout.main_messenges_);
            send_btn.setOnClickListener(MainWindows_Create_Join_Event.this);


        }
        if (v.getId() == send_btn.getId()) {

            Test();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Test() {

        mess = findViewById(R.id.Mensage_in);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a ");
        Date currentTime = Calendar.getInstance().getTime();
        String Name = user.getDisplayName();
        String Date = df.format(System.currentTimeMillis());
        main_messenges mensage = new main_messenges(Name, Date,mess.getText().toString());

        refMessanging.child("events").child("-N9Zrr8wPPsWmL_eQGgK").child("Message").push().setValue(mensage);
      mess.setText("");
      mess.setHint(" ");
    }
    
    private void signout() {

        AuthUI.getInstance().signOut(MainWindows_Create_Join_Event.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainWindows_Create_Join_Event.this, Login.class));
                    finish();

//
                }
            }
        });
    }
    private void RequestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this).setTitle("Permission need it").setMessage("To be able to scan QR code \n you will need the permissions ")
                    .setPositiveButton("Allow", (dialog, which) -> {
                        Intent car = new Intent(MainWindows_Create_Join_Event.this, Qr_code_scanner.class);
                        startActivity(car);


                    }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();


        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


            Intent car = new Intent(this, Qr_code_scanner.class);
            startActivity(car);


        }




    }
}
