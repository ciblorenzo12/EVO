package com.evopackage.evo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainWindows_Create_Join_Event extends AppCompatActivity implements create_event_popup.DialogListener, View.OnClickListener {




    //searchView
    RecyclerView recicleviw;
    DatabaseReference refdata;
    DatabaseReference userefdata = FirebaseDatabase.getInstance().getReference().child("users");
    ArrayList<Event> _events;
    SearchView search_bar;
    Adapter_Recicleview adaptor;
    LinearLayoutManager managerL;


    private ImageButton qr, settings,evtBtn, btn,messenges_btn,back_settings,join;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        logout = findViewById(R.id.logout);
        back_settings =  findViewById(R.id.SettingsBack);
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
        if(v.getId()==back_settings.getId() ){

            Intent _intent = new Intent(this,MainWindows_Create_Join_Event.class);
            startActivity(_intent);
             finish();
        }
        if(v.getId()==messenges_btn.getId()){

            Intent _intent = new Intent(this,main_messenges.class);
            startActivity(_intent);


        }
    }

    private void signout() {

        AuthUI.getInstance().signOut(MainWindows_Create_Join_Event.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainWindows_Create_Join_Event.this, Login.class));
                    finish();

//        if (v.getId() == R.id.settings_Main_Id) {
//            Intent car = new Intent(this, Event_Page.class);
//            startActivity(car);
//        } caused crash... was located in signout before??
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
                    }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).create().show();
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


                    }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();


        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


            Intent car = new Intent(this, Qr_code_scanner.class);
            startActivity(car);


        }


    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAdder) {

    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

    }

    // @Override
  //  public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

   // }
}
