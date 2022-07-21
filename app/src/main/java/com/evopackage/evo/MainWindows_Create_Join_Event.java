package com.evopackage.evo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Dictionary;

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
    private Button evt;
    private LinearLayout linearEvt;
   // private Dictionary<Integer,String> evtList;


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
        settings.setOnClickListener(this);

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
    }

    @Override
    public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

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

   // @Override
  //  public void applyTexts(String _evtName, String _evtDate, String _evtAddr, String _evtTheme) {

   // }
}
