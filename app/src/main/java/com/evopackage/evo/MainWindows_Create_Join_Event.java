package com.evopackage.evo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainWindows_Create_Join_Event extends AppCompatActivity implements create_event_popup.DialogListener, View.OnClickListener {


    private FirebaseAuth auth;
    private FirebaseDatabase search;
    private FirebaseUser user;
    private int Camera_Permission_Request = 1;
    private String[] perm_ = {Manifest.permission.CAMERA};//add permitions to this array
    private boolean permission_granted;
    private ImageButton btn;
    private ImageButton evtBtn;
    private ImageButton qr,settings;

    private RecyclerView rv;
    private SearchView s;
    private DatabaseReference ref;
    private ArrayList<Event> liste;
    private MyAdapter adapter;

    private String n;
    private String d;
    private String c;
    private String l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windows_create_join_event);
        qr = findViewById(R.id.qr_main_id);
        btn = findViewById(R.id.profile_picture_Main_id);
        settings= findViewById(R.id.settings_Main_Id);
        evtBtn = findViewById(R.id.calendar_id);
        evtBtn.setOnClickListener(v -> openDialog());
        qr.setOnClickListener(this);
        btn.setOnClickListener(this);
        s = findViewById(R.id.searchView);
        rv = findViewById(R.id.rv);
        ref = FirebaseDatabase.getInstance().getReference().child("events");
        liste = new ArrayList<>();
        adapter = new MyAdapter(liste, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                movetodescription(event);
            }
        });

        n  = "ghe";
        d  = "ghr";
        c = "gh";
        l  = "gyh";

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    for (DataSnapshot sn : snapshot.getChildren())
                    {

                        c = sn.child("_category").getValue().toString();
                        d = sn.child("_date").getValue().toString();
                        l = sn.child("_address").getValue().toString();
                        n = sn.child("_name").getValue().toString();


                        Event  ev = new Event( n, d, l, c, user) ;

                        liste.add(ev);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String sui) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String si) {
                find(si);
                return true;
            }
        });
        settings.setOnClickListener(this);
    }

    private void movetodescription(Event event) {
        Intent i = new Intent(this, EventDescription.class);
                i.putExtra("Event", event);
        startActivity(i);

    }

    private void find(String si) {
        ArrayList<Event> milist = new ArrayList<>();

        for (Event sn : liste)
        {
            if(sn.GetName().toLowerCase().contains(si.toLowerCase()))
            {
                milist.add(sn);
            }
        }
        MyAdapter a = new MyAdapter(milist, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {

            }
        });
        rv.setAdapter(a);


    }


    private void openDialog() {
        create_event_popup evtPopUp = new create_event_popup();
        evtPopUp.show(getSupportFragmentManager(), "EventDialog");
    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAdder) {

    }


    @Override
    public void onClick(View v) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (v.getId() == btn.getId()) {

            if(user!=null){
            startActivity(new Intent(this, Profile_Page.class));

            }
            else{
                startActivity(new Intent(this, Login.class));
            }

        }
        if (v.getId() == qr.getId()) {


            RequestCameraPermission();


        }
        if(v.getId()==R.id.settings_Main_Id){

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



                    }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();


        }   if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){


            Intent car = new Intent(this, Qr_code_scanner.class);
            startActivity(car);



        }


    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

    }
}
