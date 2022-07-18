package com.evopackage.evo;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

public class Qr_code_scanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private MainWindows_Create_Join_Event mw;
    private  String[]perm = { Manifest.permission.CAMERA};
    private FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference firebaseEvent = FirebaseDatabase.getInstance().getReference().child("events");
    private DatabaseReference firebaseUser = FirebaseDatabase.getInstance().getReference().child("users");
     private TextView tw ;

   private static Result _result;
private CodeScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);


tw = findViewById(R.id.text_uni3);

        scannerView = findViewById(R.id.Scanner_Qrcode);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    tw.setText( result.getText().trim(), TextView.BufferType.NORMAL);
                  _result = result;
                   firebaseEvent.child(_result.toString()).child("Users").child(me.getUid()).setValue("Guest");
                  firebaseUser.child(me.getUid()).child("Events").child(_result.toString()).setValue("Guest");
                    }
                });
            }
        });

                scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }
    @Override
    protected void onStart() {




       ActivityCompat.requestPermissions(Qr_code_scanner.this, perm, 1);

        super.onStart();


    }
    @Override
    protected void onResume() {
        mCodeScanner.startPreview();

        super.onResume();


    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();

        super.onPause();
    }
    public static String GetScan(){





        return _result.getText() ;
    }

}
