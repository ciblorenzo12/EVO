package com.evopackage.evo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

    private ImageButton btn;
    private ImageButton evtBtn;
    private ImageButton qr,settings;

    //searchView
    RecyclerView recicleviw;
     DatabaseReference refdata;
     ArrayList<Event> events;
   SearchView search_bar;
     Adapter_Recicleview adaptor;
    LinearLayoutManager managerL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windows_create_join_event);


        refdata = FirebaseDatabase.getInstance().getReference().child("events");
        recicleviw = findViewById(R.id.RecicleBar_Firebase);
        search_bar = findViewById(R.id.searchView_Main);
        managerL = new LinearLayoutManager(this);
        recicleviw.setLayoutManager(managerL);
        events = new ArrayList<>();
        adaptor = new Adapter_Recicleview(events);
        recicleviw.setAdapter(adaptor);
        refdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               events.clear();
                 if(snapshot.exists()){
                     for (DataSnapshot snap:snapshot.getChildren()){

                         Event evt = new Event(snap.child("name").getValue().toString(), snap.child("date").getValue().toString(), "String location", "String category", "String creator","String uri");
                         events.add(evt);

                     }




                 }
                adaptor.notifyDataSetChanged();



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
        settings= findViewById(R.id.settings_Main_Id);
        evtBtn = findViewById(R.id.calendar_id);
        evtBtn.setOnClickListener(v -> openDialog());
        qr.setOnClickListener(this);
        btn.setOnClickListener(this);
        settings.setOnClickListener(this);


    }

    private void Search(String txt) {
        ArrayList<Event>events_search= new ArrayList<>();
        for (Event eventObj:events){

            if(eventObj.GetName().toLowerCase().contains(txt.toLowerCase())) {
                events_search.add(eventObj);

            Adapter_Recicleview recicleview = new Adapter_Recicleview(events_search);
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
    public void applyTexts(String _evtName, String _evtDate, String _evtAdder) {

    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

    }

    // @Override
  //  public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

   // }
}
