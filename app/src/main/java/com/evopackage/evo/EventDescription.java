package com.evopackage.evo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evopackage.evo.databinding.EventDescriptionBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

public class EventDescription extends AppCompatActivity {
//QR Generator

  private static String Ev_clicked;




    private EventDescriptionBinding binding;
    private Event ev;
    private TextView txtName, txtAddress, txtDate, txtCategory, txtDescription, txtCreator, txtAttendees;
    private RecyclerView rv;
    //private EventActivitiesAdapter adapter;
    private List<Activity> list;
    private LinearLayoutManager managerL;
    private DatabaseReference refdata;
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

        populateData();

//
//
//        refdata = FirebaseDatabase.getInstance().getReference().child("events");
//        rv = findViewById(R.id.RecicleBar_Firebase);
//
//        rv.setLayoutManager(managerL);
//
//        adapter = new EventActivitiesAdapter(list, listener) {
//
//            public void OnItemClick(Activity ev) {
//                movetodescription(ev);
//            }
//        };
//
//        rv.setAdapter(adapter);
//
//        refdata.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
//
//                    list.clear();
//
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//
//                        String Creator = snap.child("creator").getValue(String.class);
//
//                        Activity evt = new Activity(snap.getKey(),
//                                snap.child("name").getValue(String.class),
//                                snap.child("date").getValue(String.class),
//                                snap.child("address").getValue(String.class),
//                                snap.child("category").getValue(String.class),
//                                snap.child("creator").getValue(String.class),
//                                "String uri",
//                                snap.child("description").getValue(String.class));
//                        list.add(evt);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
    }

//    private void movetodescription(Activity ev) {
//        Intent i = new Intent(this, EventDescription.class);
//        i.putExtra("Activity", ev);
//        startActivity(i);
//    }

    public void populateData()
    {

        txtName.setText(ev.GetName());
        txtAddress.setText(ev.GetLocation());
        txtDate.setText(ev.GetDate());
        txtCategory.setText(ev.GetCategory());
        txtDescription.setText(ev.GetDescription());
        txtCreator.setText(ev.GetCreator());
        GenerateQr(ev.GetKey());
        FirebaseDatabase.getInstance().getReference().child("events").child(ev.GetKey()).child("people").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtAttendees.setText(snapshot.getChildrenCount() + " Attendees");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            ((ImageView) findViewById(R.id.EventQRGen)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
