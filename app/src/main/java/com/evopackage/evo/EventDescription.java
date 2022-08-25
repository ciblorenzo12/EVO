package com.evopackage.evo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

public class EventDescription extends AppCompatActivity {

    private Event ev;
    private static String ClickedEvent;
    private Button setUp, Join_Evt;
    private ImageButton chatroom_m;
    private TextView txtName, txtAddress, txtDate, txtCategory, txtDescription, txtCreator, txtAttendees;
    private RecyclerView rv;
   // private EventActivitiesAdapter adapter;
    private ArrayList<Activity> list;
    private DatabaseReference firebaseUsers;
    private DatabaseReference eventRef;
    //private EventActivitiesAdapter adapter;

    private LinearLayoutManager managerL;
    private DatabaseReference refdata;


    ArrayList<Activity> _activity;
    EventActivitiesAdapter adaptor;
    private RecyclerView rv2;
    private ArrayList<Profile_Page> list2;

   // private EventActivitiesAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_description);

        ev = (Event) getIntent().getSerializableExtra("Event");
        chatroom_m = findViewById(R.id.chatroom);
        txtName = findViewById(R.id.txtEventName);
        txtAddress = findViewById(R.id.txtEventAddress);
        txtDate = findViewById(R.id.txtEventDate);
        txtCategory = findViewById(R.id.category4item);
        txtDescription = findViewById(R.id.descriptionev);
        txtCreator = findViewById(R.id.eventcreatorr);
        txtAttendees = findViewById(R.id.txtAttendees);
        ClickedEvent = ev.GetKey();
        Join_Evt = findViewById(R.id.Join_Event);
        _activity = new ArrayList<>();
        GenerateQr(EventDescription.GetClickedEvent());
        eventRef = FirebaseDatabase.getInstance().getReference().child("events");
        firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseDatabase.getInstance().getReference().child("events").child(ev.GetKey()).child("people").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()){
                    Join_Evt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Join_Evt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventRef.child(ev.GetKey()).child("people").child(firebaseUsers.getKey()).setValue("Guest");
                firebaseUsers.child("user-events").child(ev.GetKey()).setValue(ev.GetName());
                Join_Evt.setVisibility(View.GONE);
            }
        });

        chatroom_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDescription.this,main_messages.class));
            }
        });
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
        adaptor = new EventActivitiesAdapter(_activity, new EventActivitiesAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Activity ac) {
                movetoacdescription(ac);
            }
        });


        rv.setAdapter(adaptor);

        refdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    _activity.clear();

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        Activity act = new Activity(
                                snap.getKey(),
                                snap.child("time").getValue(String.class),
                                snap.child("location").getValue(String.class));

                        _activity.add(act);
                    }
                    adaptor.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void movetoacdescription(Activity ac) {
        ActivityDescription description = new ActivityDescription(ac, ev.GetKey());
        description.show(getSupportFragmentManager(), "ActivityDescription");
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
    private void GenerateQr(String qr) {
        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = writer.encode(qr, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.qrvt)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}