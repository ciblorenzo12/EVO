package com.evopackage.evo;







import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainWindows_Create_Join_Event extends AppCompatActivity implements create_event_popup.DialogListener, View.OnClickListener {


    private FirebaseAuth auth;
    private FirebaseDatabase search;
    private FirebaseUser user;
    private int Camera_Permission_Request = 1;
    private String[] perm_ = {Manifest.permission.CAMERA};//add permitions to this array
    private boolean permission_granted;
    private ImageButton btn;
    private ImageButton evtBtn;
    private ImageButton qr;
    private Button evt;
    private int NUM_ROW = 1;
    private int NUM_COLS = 1;
    private LinearLayout linearEvt;
    //private TableLayout tEvt;
    //private CardView cEvt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_windows_create_join_event);
        qr = findViewById(R.id.qr_main_id);
        btn = findViewById(R.id.profile_picture_Main_id);
        evtBtn = findViewById(R.id.calendar_id);
        evtBtn.setOnClickListener(v -> openDialog());
        qr.setOnClickListener(this);
        btn.setOnClickListener(this);



        linearEvt = (LinearLayout) findViewById(R.id.Linear_Event);


    }

    private void openDialog() {
        create_event_popup evtPopUp = new create_event_popup();
        evtPopUp.show(getSupportFragmentManager(), "EventDialog");
    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAdder) {
        TextView evtName = new TextView(this);
        TextView evtDate = new TextView(this);
        TextView evtAdder = new TextView(this);
        TableLayout table = new TableLayout(this);
        TableLayout.LayoutParams pTable = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
       // pTable.gravity = Gravity.CENTER;
        TableRow tableRowName = new TableRow(this);
        TableRow tableRowDate = new TableRow(this);
        TableRow tableRowAddre = new TableRow(this);
            tableRowName.setLayoutParams(pTable);
        tableRowDate.setLayoutParams(pTable);
        tableRowAddre.setLayoutParams(pTable);
        table.addView(tableRowName);
        table.addView(tableRowDate);
        table.addView(tableRowAddre);


       // TableRow.LayoutParams rTable = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        evt = new Button(this);
        RelativeLayout relative = new RelativeLayout(this);
        evt.getBackground().setAlpha(50);
        evtName.setId(View.generateViewId());
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams rPara = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rPara.addRule(RelativeLayout.RIGHT_OF);



        evtName.setText(_evtName);
        evtDate.setText(_evtDate);
        evtAdder.setText(_evtAdder);

        evtName.setTextSize(35f);
        evtDate.setTextSize(35f);
        evtAdder.setTextSize(35f);
        evt.setLayoutParams(rPara);
        evtName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT ));
        evtDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT ));
        evtAdder.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT ));
        table.setLayoutParams(rPara);

        relative.setLayoutParams(para);


        evt.setId(View.generateViewId());
        relative.addView(table);
        relative.addView(evt);
        tableRowName.addView(evtName);
        tableRowDate.addView(evtDate);
        tableRowAddre.addView(evtAdder);

        linearEvt.addView(relative);


        //evt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        tEvt = (TableLayout) findViewById(R.id.tableForEvent);
//        for (int row = 0; row < NUM_ROW; row++) {
//            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
//            tEvt.addView(tableRow);
//            if(NUM_COLS > 2)
//            for (int col = 0; col < NUM_COLS; col++){
//
//                evt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
//                evt.setScaleX(100);
//                evt.setScaleY(100);
//                tableRow.addView(evt);
//            }
//        }



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
}
